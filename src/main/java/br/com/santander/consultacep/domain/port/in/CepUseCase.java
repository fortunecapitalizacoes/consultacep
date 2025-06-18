	package br.com.santander.consultacep.domain.port.in;
	
	import br.com.santander.consultacep.domain.model.Endereco;
	
	public interface CepUseCase {
		 Endereco buscarESalvar(String cep);
	}
