package br.com.santander.consultacep.infrastructure.adapter.out.database.endereco;

import br.com.santander.consultacep.domain.model.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class EnderecoPersistenceAdapterTest {

    @Mock
    private EnderecoJpaRepository repository;

    private EnderecoPersistenceAdapter adapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        adapter = new EnderecoPersistenceAdapter(repository);
    }

    @Test
    void salvar_deveConverterEDepoisSalvarEntidade() {
        // Arrange
        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setLogradouro("Rua A");
        endereco.setBairro("Bairro B");
        endereco.setLocalidade("Cidade C");
        endereco.setUf("SP");

        // Act
        adapter.salvar(endereco);

        // Assert
        ArgumentCaptor<EnderecoEntity> captor = ArgumentCaptor.forClass(EnderecoEntity.class);
        verify(repository, times(1)).save(captor.capture());

        EnderecoEntity entitySalva = captor.getValue();
        assert entitySalva.getCep().equals(endereco.getCep());
        assert entitySalva.getLogradouro().equals(endereco.getLogradouro());
        assert entitySalva.getBairro().equals(endereco.getBairro());
        assert entitySalva.getLocalidade().equals(endereco.getLocalidade());
        assert entitySalva.getUf().equals(endereco.getUf());
    }
}
