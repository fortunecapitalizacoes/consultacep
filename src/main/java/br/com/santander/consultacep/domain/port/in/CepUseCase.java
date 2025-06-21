	package br.com.santander.consultacep.domain.port.in;
	
	import java.util.concurrent.CompletableFuture;

import br.com.santander.consultacep.domain.model.Endereco;
	
	public interface CepUseCase {
		CompletableFuture<Endereco> buscarESalvar(String cep);
	}
