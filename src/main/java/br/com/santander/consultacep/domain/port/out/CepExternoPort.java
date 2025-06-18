package br.com.santander.consultacep.domain.port.out;

import br.com.santander.consultacep.domain.model.Endereco;

public interface CepExternoPort {
	Endereco buscarPorCep(String cep);
}
