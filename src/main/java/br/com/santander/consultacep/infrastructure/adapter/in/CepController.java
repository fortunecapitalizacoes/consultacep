package br.com.santander.consultacep.infrastructure.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.in.CepUseCase;
import br.com.santander.consultacep.infrastructure.exception.ExternalServiceException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller REST para consulta de endereços via CEP.
 */
@Slf4j
@RestController
@RequestMapping("/api/cep")
@RequiredArgsConstructor
public class CepController {

    private final CepUseCase cepUseCase;
    
    
    /**
     * Endpoint para buscar endereço por CEP.
     *
     * @param cep CEP informado na URL
     * @return Endereço encontrado com status 200 OK
     */
    @GetMapping("/{cep}")
    public ResponseEntity<?> buscarCep(@PathVariable("cep") String cep) {
        try {
            Endereco endereco = cepUseCase.buscarESalvar(cep).get();
            return ResponseEntity.ok(endereco);
        } catch (ExternalServiceException e) {
            log.warn("Serviço externo indisponível para o CEP {}: {}", cep, e.getMessage());
            return ResponseEntity.status(503).body("Serviço indisponível para o CEP: " + cep);
        } catch (Exception e) {
            log.error("Erro inesperado ao consultar o CEP {}: {}", cep, e.getMessage(), e);
            return ResponseEntity.status(500).body("Erro interno ao processar o CEP.");
        }
    }
}
