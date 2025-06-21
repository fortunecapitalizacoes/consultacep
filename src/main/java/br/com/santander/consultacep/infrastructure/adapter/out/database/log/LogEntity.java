package br.com.santander.consultacep.infrastructure.adapter.out.database.log;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.santander.consultacep.infrastructure.adapter.out.database.endereco.EnderecoEntity;
import lombok.*;

/**
 * Entidade MongoDB que representa um registro de log das operações
 * relacionadas à consulta e persistência de CEPs.
 *
 * <p>Mapeia um documento na coleção "log" do MongoDB, contendo informações
 * sobre falhas ou sucessos das requisições realizadas.</p>
 *
 * <p>Inclui detalhes do CEP consultado, erro ocorrido (se houver),
 * dados retornados da API externa e o horário da requisição.</p>
 *
 * <p>Utiliza anotações Lombok para gerar automaticamente os métodos
 * getters, setters, construtores e builder.</p>
 */
@Document(collection = "log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEntity {

    /** Identificador único do registro de log. */
    @Id
    private String id;

    /** CEP consultado na operação. */
    private String cep;

    /** Mensagem de erro ou descrição da falha ocorrida durante a requisição. */
    private String erro;

    /** Dados do endereço retornados pela API ViaCEP, se a consulta foi bem-sucedida. */
    private EnderecoEntity dadosRetornoApiViaCep;

    /** Data e hora em que a requisição foi realizada. */
    private LocalDateTime horarioRequicicao;
}
