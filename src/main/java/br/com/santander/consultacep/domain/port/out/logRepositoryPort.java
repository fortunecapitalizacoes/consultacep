package br.com.santander.consultacep.domain.port.out;

import br.com.santander.consultacep.domain.model.Endereco;
public interface logRepositoryPort {
	 void salvar(Endereco endereco);
}
