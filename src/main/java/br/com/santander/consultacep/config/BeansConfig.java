package br.com.santander.consultacep.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.santander.consultacep.application.service.CepService;
import br.com.santander.consultacep.domain.port.in.CepUseCase;
import br.com.santander.consultacep.domain.port.out.CepExternoPort;
import br.com.santander.consultacep.domain.port.out.EnderecoRepositoryPort;
import br.com.santander.consultacep.domain.port.out.LogRepositoryPort;

@Configuration
public class BeansConfig {

	@Bean
	public CepUseCase cepUseCase(
	    @Qualifier("enderecoPersistenceAdapter") EnderecoRepositoryPort repo,
	    CepExternoPort api,
	    LogRepositoryPort log
	) {
	    return new CepService(repo, log, api);
	}
}
