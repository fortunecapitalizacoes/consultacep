package br.com.santander.consultacep.infrastructure.adapter.out.database.endereco;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class EnderecoEntity {
	
	@Id
	private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    
}