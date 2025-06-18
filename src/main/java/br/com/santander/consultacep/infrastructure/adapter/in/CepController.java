package br.com.santander.consultacep.infrastructure.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.in.CepUseCase;

@RestController
@RequestMapping("/api/cep")
public class CepController {

    private final CepUseCase cepUseCase;

    public CepController(CepUseCase cepUseCase) {
        this.cepUseCase = cepUseCase;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<Endereco> buscarCep(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(cepUseCase.buscarESalvar(cep));
    }
}