package br.com.santander.consultacep.infrastructure.adapter.in;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.in.CepUseCase;
import br.com.santander.consultacep.infrastructure.exception.ExternalServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CepControllerTest {

    @Mock
    private CepUseCase cepUseCase;

    private CepController cepController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cepController = new CepController(cepUseCase);
    }

 
    @Test
    void buscarCep_deveRetornar503_quandoExternalServiceException() {
        String cep = "12345678";
        when(cepUseCase.buscarESalvar(cep)).thenThrow(new ExternalServiceException("Serviço indisponível"));

        ResponseEntity<?> response = cepController.buscarCep(cep);

        assertEquals(503, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Serviço indisponível"));
    }

    @Test
    void buscarCep_deveRetornar500_quandoErroDesconhecido() {
        String cep = "12345678";
        when(cepUseCase.buscarESalvar(cep)).thenThrow(new RuntimeException("Falha interna"));

        ResponseEntity<?> response = cepController.buscarCep(cep);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Erro interno"));
    }
}
