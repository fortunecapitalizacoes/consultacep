package br.com.santander.consultacep.infrastructure.adapter.out.database.endereco;

import org.springframework.stereotype.Component;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.out.EnderecoRepositoryPort;
import lombok.AllArgsConstructor;

/**
 * Adapter de persistência que implementa a porta de repositório de Endereço,
 * usando JPA para salvar a entidade no banco de dados.
 */
@Component
@AllArgsConstructor
public class EnderecoPersistenceAdapter implements EnderecoRepositoryPort {

    private final EnderecoJpaRepository repository;

    /**
     * Salva um objeto de domínio Endereco convertendo para entidade JPA e persistindo no banco.
     * 
     * @param endereco objeto de domínio a ser salvo
     */
    @Override
    public void salvar(Endereco endereco) {
        EnderecoEntity entity = new EnderecoEntity(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getLocalidade(),
                endereco.getUf()
        );

        repository.save(entity);
    }
}
