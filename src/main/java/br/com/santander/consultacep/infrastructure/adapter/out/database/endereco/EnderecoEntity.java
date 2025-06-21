package br.com.santander.consultacep.infrastructure.adapter.out.database.endereco;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

/**
 * Entidade MongoDB que representa o documento de endereço na coleção correspondente.
 *
 * <p>Mapeia os campos do documento Mongo para os atributos da classe,
 * com o campo {@code cep} como identificador único {@link Id}.</p>
 *
 * <p>Utiliza anotações do Lombok para geração automática de getters, setters,
 * construtores, builder, etc.</p>
 */
@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoEntity {

    /** Código de Endereçamento Postal - identificador único do documento. */
    @Id
    private String cep;

    /** Nome da rua, avenida, etc. */
    private String logradouro;

    /** Bairro do endereço. */
    private String bairro;

    /** Cidade ou localidade. */
    private String localidade;

    /** Unidade federativa (estado), representada pela sigla. */
    private String uf;

}
