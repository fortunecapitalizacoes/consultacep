package br.com.santander.consultacep.infrastructure.adapter.out.database.log;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.santander.consultacep.infrastructure.adapter.out.database.endereco.EnderecoEntity;
import lombok.*;

@Document(collection = "log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogEntity {

    @Id
    private String id;

    private String cep; 

    private String erro; 
    
    private EnderecoEntity dadosRetornoApiViaCep;

    private LocalDateTime horarioRequicicao;
}
