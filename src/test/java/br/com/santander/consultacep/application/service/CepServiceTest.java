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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class CepServiceTest {

	  @Mock
	    private EnderecoRepositoryPort enderecoRepository;

	    @Mock
	    private LogRepositoryPort logRepositoryPort;

	    @Mock
	    private CepExternoPort cepPort;

	    @InjectMocks
	    private CepService cepService;

	    @BeforeEach
	    void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void deveBuscarESalvarEnderecoComSucesso() throws Exception {
	        // Arrange
	        String cep = "12345678";
	        Endereco endereco = new Endereco();
	        endereco.setCep(cep);

	        when(cepPort.buscarPorCep(cep)).thenReturn(endereco);

	        // Act
	        CompletableFuture<Endereco> future = cepService.buscarESalvar(cep);

	        // Assert
	        Endereco resultado = future.get();
	        assertNotNull(resultado);
	        assertEquals(cep, resultado.getCep());
	        verify(enderecoRepository).salvar(endereco);
	        verify(logRepositoryPort).salvar(endereco);
	    }

	    @Test
	    void deveExecutarFallbackQuandoCepPortLancaExcecao() {
	        // Arrange
	        String cep = "87654321";
	        RuntimeException excecao = new RuntimeException("Serviço indisponível");

	        when(cepPort.buscarPorCep(cep)).thenThrow(excecao);

	        // Act
	        CompletableFuture<Endereco> future = cepService.fallbackBuscarESalvar(cep, excecao);

	        // Assert
	        ExecutionException executionException = assertThrows(ExecutionException.class, future::get);
	        assertTrue(executionException.getCause() instanceof ExternalServiceException);
	        verify(logRepositoryPort).salvarFalha(eq(cep), contains("Serviço indisponível"));
	    }
}
