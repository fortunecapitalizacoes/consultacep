package br.com.santander.consultacep.infrastructure.adapter.out.database.log;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.santander.consultacep.infrastructure.adapter.out.database.endereco.EnderecoEntity;

public interface LogJpaRepository extends MongoRepository<LogEntity, String> {

}
