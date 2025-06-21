package br.com.santander.consultacep.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo de domínio que representa um endereço obtido a partir de um CEP.
 *
 * <p>Contém informações básicas como logradouro, bairro, cidade e unidade federativa (UF).
 * Essa classe é utilizada em diversas camadas da aplicação, incluindo serviços de negócio
 * e persistência.</p>
 *
 * <p>Utiliza anotações do Lombok para geração automática de construtores, getters, setters,
 * e métodos como {@code toString}, {@code equals} e {@code hashCode}.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {

    /** Código de Endereçamento Postal. */
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
