package br.com.santander.consultacep.infrastructure.adapter.out.database.endereco;

import org.springframework.stereotype.Component;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.out.EnderecoRepositoryPort;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EnderecoPersistenceAdapter implements EnderecoRepositoryPort {

    private final EnderecoJpaRepository repository;

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