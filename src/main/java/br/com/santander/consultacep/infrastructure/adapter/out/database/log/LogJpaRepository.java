package br.com.santander.consultacep.infrastructure.adapter.out.database.log;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repositório MongoDB responsável pelas operações de persistência
 * e consulta da entidade {@link LogEntity}, que representa registros de log
 * relacionados às operações com CEPs.
 *
 * <p>Estende {@link MongoRepository} para fornecer suporte completo a operações
 * CRUD em documentos armazenados no MongoDB.</p>
 *
 * <p>Faz parte do adaptador de saída da arquitetura hexagonal, implementando
 * o armazenamento de logs de sucesso ou falha nas requisições.</p>
 */
public interface LogJpaRepository extends MongoRepository<LogEntity, String> {
}
