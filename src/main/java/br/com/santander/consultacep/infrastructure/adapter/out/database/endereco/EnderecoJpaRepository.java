package br.com.santander.consultacep.infrastructure.adapter.out.database.endereco;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EnderecoJpaRepository extends MongoRepository<EnderecoEntity, String> {

}
