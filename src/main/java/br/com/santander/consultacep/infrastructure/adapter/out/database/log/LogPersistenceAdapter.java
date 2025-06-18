package br.com.santander.consultacep.infrastructure.adapter.out.database.log;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.model.Log;
import br.com.santander.consultacep.domain.port.out.logRepositoryPort;
import br.com.santander.consultacep.infrastructure.adapter.out.database.endereco.EnderecoEntity;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LogPersistenceAdapter implements logRepositoryPort {

    private final LogJpaRepository repository;

	@Override
	public void salvar(Endereco endereco) {
		  var edereco = EnderecoEntity.builder().bairro(endereco.getBairro()).cep(endereco.getCep()).localidade(endereco.getLocalidade()).logradouro(endereco.getLogradouro()).uf(endereco.getUf()).build();
		  var logEntity = LogEntity.builder().dadosRetornoApiViaCep(edereco).horarioRequicicao(LocalDateTime.now()).build();
	      repository.save(logEntity);
	}

}