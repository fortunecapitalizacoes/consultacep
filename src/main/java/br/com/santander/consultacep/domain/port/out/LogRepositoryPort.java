package br.com.santander.consultacep.domain.port.out;

import br.com.santander.consultacep.domain.model.Endereco;
public interface LogRepositoryPort {
	 void salvar(Endereco endereco);
}
