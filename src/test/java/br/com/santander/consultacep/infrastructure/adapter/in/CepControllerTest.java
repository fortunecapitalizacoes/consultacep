package br.com.santander.consultacep.infrastructure.adapter.in;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.in.CepUseCase;
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
    void buscarCep_deveRetornarEndereco_quandoCepValido() {
        String cep = "12345678";
        Endereco enderecoMock = new Endereco();
        // Popule enderecoMock se desejar

        when(cepUseCase.buscarESalvar(cep)).thenReturn(enderecoMock);

        ResponseEntity<Endereco> response = cepController.buscarCep(cep);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(enderecoMock, response.getBody());
        verify(cepUseCase).buscarESalvar(cep);
    }

    @Test
    void fallbackBuscarCep_deveRetornar503_quandoServicoIndisponivel() {
        String cep = "12345678";
        Throwable ex = new RuntimeException("Erro simulado");

        ResponseEntity<String> response = cepController.fallbackBuscarCep(cep, ex);

        assertEquals(503, response.getStatusCodeValue());
        assertTrue(response.getBody().contains(cep));
        assertTrue(response.getBody().contains("Serviço indisponível"));
    }
}
