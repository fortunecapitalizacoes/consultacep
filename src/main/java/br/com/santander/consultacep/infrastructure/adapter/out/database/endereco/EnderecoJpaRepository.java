package br.com.santander.consultacep.infrastructure.adapter.out.database.endereco;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repositório MongoDB responsável pelas operações de persistência
 * e consulta da entidade {@link EnderecoEntity}.
 *
 * <p>Estende {@link MongoRepository} para fornecer funcionalidades
 * CRUD automáticas sobre documentos do MongoDB.</p>
 *
 * <p>Faz parte do adaptador de saída da arquitetura hexagonal, sendo
 * a implementação concreta da persistência de dados.</p>
 */
public interface EnderecoJpaRepository extends MongoRepository<EnderecoEntity, String> {
}
