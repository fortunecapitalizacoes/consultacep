package br.com.santander.consultacep.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class Endereco {
  	private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
}
