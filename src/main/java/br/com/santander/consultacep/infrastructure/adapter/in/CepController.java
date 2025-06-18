package br.com.santander.consultacep.infrastructure.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.in.CepUseCase;
import io.github.resilience4j.retry.annotation.Retry;

/**
 * Controller REST para consultar CEPs.
 * 
 * Disponibiliza endpoint para buscar endereço por CEP.
 */
@RestController
@RequestMapping("/api/cep")
public class CepController {

    private final CepUseCase cepUseCase;

    /**
     * Construtor do controller, injetando a porta de caso de uso para CEP.
     * 
     * @param cepUseCase serviço de negócio para consulta e salvamento de CEP
     */
    public CepController(CepUseCase cepUseCase) {
        this.cepUseCase = cepUseCase;
    }

    /**
     * Endpoint GET para buscar endereço por CEP.
     * Aplica retry em caso de falha, com fallback (não implementado aqui).
     * 
     * @param cep CEP a ser consultado (path variable)
     * @return ResponseEntity com o endereço encontrado e status 200 OK
     */
    @GetMapping("/{cep}")
    @Retry(name = "cepServiceRetry", fallbackMethod = "fallbackBuscarCep")
    public ResponseEntity<Endereco> buscarCep(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(cepUseCase.buscarESalvar(cep));
    }
    
    /**
     * Método fallback chamado quando todas tentativas do retry falharem.
     * 
     * @param cep CEP da consulta
     * @param throwable exceção disparada
     * @return ResponseEntity com erro 503 e mensagem apropriada
     */
    public ResponseEntity<String> fallbackBuscarCep(String cep, Throwable throwable) {
        return ResponseEntity.status(503).body("Serviço indisponível para o CEP: " + cep);
    }
}
