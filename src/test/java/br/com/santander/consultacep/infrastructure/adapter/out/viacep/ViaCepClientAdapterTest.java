package br.com.santander.consultacep.infrastructure.adapter.out.viacep;

import br.com.santander.consultacep.domain.model.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViaCepClientAdapterTest {

    @Mock
    private RestTemplate restTemplate;

    private ViaCepClientAdapter adapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        adapter = new ViaCepClientAdapter();

        // Usando Reflection para injetar mock RestTemplate no adapter (pois é final e criado no construtor)
        try {
            java.lang.reflect.Field field = ViaCepClientAdapter.class.getDeclaredField("restTemplate");
            field.setAccessible(true);
            field.set(adapter, restTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void buscarPorCep_deveRetornarEndereco_quandoResponseValido() {
        String cep = "12345678";
        ViaCepResponse mockResponse = new ViaCepResponse();
        mockResponse.setCep(cep);
        mockResponse.setLogradouro("Rua X");
        mockResponse.setBairro("Bairro Y");
        mockResponse.setLocalidade("Cidade Z");
        mockResponse.setUf("SP");

        when(restTemplate.getForObject(anyString(), eq(ViaCepResponse.class))).thenReturn(mockResponse);

        Endereco endereco = adapter.buscarPorCep(cep);

        assertNotNull(endereco);
        assertEquals(cep, endereco.getCep());
        assertEquals("Rua X", endereco.getLogradouro());
        assertEquals("Bairro Y", endereco.getBairro());
        assertEquals("Cidade Z", endereco.getLocalidade());
        assertEquals("SP", endereco.getUf());
    }

    @Test
    void buscarPorCep_deveLancarException_quandoResponseNull() {
        String cep = "00000000";
        when(restTemplate.getForObject(anyString(), eq(ViaCepResponse.class))).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adapter.buscarPorCep(cep));
        assertEquals("CEP inválido ou não encontrado.", exception.getMessage());
    }

    @Test
    void buscarPorCep_deveLancarException_quandoCepNullNoResponse() {
        String cep = "00000000";
        ViaCepResponse response = new ViaCepResponse();
        response.setCep(null);

        when(restTemplate.getForObject(anyString(), eq(ViaCepResponse.class))).thenReturn(response);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adapter.buscarPorCep(cep));
        assertEquals("CEP inválido ou não encontrado.", exception.getMessage());
    }

    @Test
    void fallbackBuscarPorCep_deveLancarRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> adapter.fallbackBuscarPorCep("12345678", new RuntimeException("fail")));

        assertEquals("Serviço ViaCep indisponível. Tente novamente mais tarde.", exception.getMessage());
    }
}
