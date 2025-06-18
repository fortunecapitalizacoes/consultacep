package br.com.santander.consultacep.application.service;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.in.CepUseCase;
import br.com.santander.consultacep.domain.port.out.CepExternoPort;
import br.com.santander.consultacep.domain.port.out.EnderecoRepositoryPort;
import br.com.santander.consultacep.domain.port.out.LogRepositoryPort;
import br.com.santander.consultacep.infrastructure.exception.ExternalServiceException;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CepService implements CepUseCase {

    private final EnderecoRepositoryPort enderecoRepository;
    private final LogRepositoryPort logRepositoryPort;
    private final CepExternoPort cepPort;

    @Override
    @Retry(name = "cepApi", fallbackMethod = "fallbackBuscarESalvar")
    @CircuitBreaker(name = "cepApi", fallbackMethod = "fallbackBuscarESalvar")
    public Endereco buscarESalvar(String cep) {
        log.info("Iniciando consulta para o CEP {}", cep);

        Endereco endereco = cepPort.buscarPorCep(cep);

        enderecoRepository.salvar(endereco);
        logRepositoryPort.salvar(endereco);

        log.info("Endereço salvo com sucesso para o CEP {}", cep);
        return endereco;
    }

    public Endereco fallbackBuscarESalvar(String cep, Throwable throwable) {
        log.error("Erro ao consultar o CEP {}: {}", cep, throwable.getMessage());
        logRepositoryPort.salvarFalha(cep, throwable.getMessage());
        throw new ExternalServiceException("Serviço de consulta de CEP indisponível no momento. Tente novamente mais tarde.");
    }
}
