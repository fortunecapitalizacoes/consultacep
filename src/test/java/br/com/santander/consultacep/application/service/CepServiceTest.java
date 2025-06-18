package br.com.santander.consultacep.application.service;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.out.CepExternoPort;
import br.com.santander.consultacep.domain.port.out.EnderecoRepositoryPort;
import br.com.santander.consultacep.domain.port.out.LogRepositoryPort;
import br.com.santander.consultacep.infrastructure.exception.ExternalServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CepServiceTest {

    @Mock
    private EnderecoRepositoryPort enderecoRepository;

    @Mock
    private LogRepositoryPort logRepositoryPort;

    @Mock
    private CepExternoPort cepExternoPort;

    @InjectMocks
    private CepService cepService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarESalvar_deveRetornarEndereco_quandoConsultaForSucesso() {
        String cep = "12345678";
        Endereco enderecoMock = new Endereco();
        // Suponha que Endereco tenha setters ou construtor para popular os dados, se necessário

        when(cepExternoPort.buscarPorCep(cep)).thenReturn(enderecoMock);

        Endereco resultado = cepService.buscarESalvar(cep);

        assertNotNull(resultado);
        assertEquals(enderecoMock, resultado);
        verify(enderecoRepository).salvar(enderecoMock);
        verify(logRepositoryPort).salvar(enderecoMock);
    }

    @Test
    void fallbackBuscarESalvar_deveLancarExcecaoECriarLogFalha_quandoServicoExternoFalhar() {
        String cep = "12345678";
        Throwable throwable = new RuntimeException("Falha no serviço externo");

        ExternalServiceException exception = assertThrows(ExternalServiceException.class,
                () -> cepService.fallbackBuscarESalvar(cep, throwable));

        assertEquals("Serviço de consulta de CEP indisponível no momento. Tente novamente mais tarde.", exception.getMessage());
        verify(logRepositoryPort).salvarFalha(cep, throwable.getMessage());
    }
}
