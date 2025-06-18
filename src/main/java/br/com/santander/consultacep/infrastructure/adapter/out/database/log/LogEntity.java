package br.com.santander.consultacep.infrastructure.adapter.out.database.log;


import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.santander.consultacep.infrastructure.adapter.out.database.endereco.EnderecoEntity;
import lombok.*;

	@Document
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class LogEntity {
	
	@Id
	private String Id;
    private EnderecoEntity dadosRetornoApiViaCep;
    private LocalDateTime horarioRequicicao;
    
}