package br.com.santander.consultacep.infrastructure.adapter.out.database.log;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.infrastructure.adapter.out.database.endereco.EnderecoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogPersistenceAdapterTest {

    @Mock
    private LogJpaRepository repository;

    private LogPersistenceAdapter adapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        adapter = new LogPersistenceAdapter(repository);
    }

    @Test
    void salvar_deveCriarLogEntityComEnderecoEChamarRepositorySave() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setLogradouro("Rua X");
        endereco.setBairro("Bairro Y");
        endereco.setLocalidade("Cidade Z");
        endereco.setUf("SP");

        adapter.salvar(endereco);

        ArgumentCaptor<LogEntity> captor = ArgumentCaptor.forClass(LogEntity.class);
        verify(repository, times(1)).save(captor.capture());

        LogEntity logSalvo = captor.getValue();
        assertEquals("12345678", logSalvo.getCep());
        assertNotNull(logSalvo.getDadosRetornoApiViaCep());
        EnderecoEntity enderecoEntity = logSalvo.getDadosRetornoApiViaCep();
        assertEquals(endereco.getCep(), enderecoEntity.getCep());
        assertEquals(endereco.getLogradouro(), enderecoEntity.getLogradouro());
        assertEquals(endereco.getBairro(), enderecoEntity.getBairro());
        assertEquals(endereco.getLocalidade(), enderecoEntity.getLocalidade());
        assertEquals(endereco.getUf(), enderecoEntity.getUf());
        assertNotNull(logSalvo.getHorarioRequicicao());
    }

    @Test
    void salvarFalha_deveCriarLogEntityComErroEChamarRepositorySave() {
        String cep = "87654321";
        String motivo = "Erro de conexão";

        adapter.salvarFalha(cep, motivo);

        ArgumentCaptor<LogEntity> captor = ArgumentCaptor.forClass(LogEntity.class);
        verify(repository, times(1)).save(captor.capture());

        LogEntity logSalvo = captor.getValue();
        assertEquals(cep, logSalvo.getCep());
        assertEquals(motivo, logSalvo.getErro());
        assertNotNull(logSalvo.getHorarioRequicicao());
    }
}
