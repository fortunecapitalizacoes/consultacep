package br.com.santander.consultacep.application.service;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.in.CepUseCase;
import br.com.santander.consultacep.domain.port.out.CepExternoPort;
import br.com.santander.consultacep.domain.port.out.EnderecoRepositoryPort;
import br.com.santander.consultacep.domain.port.out.LogRepositoryPort;
import br.com.santander.consultacep.infrastructure.exception.ExternalServiceException;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * Serviço responsável pela busca e persistência de Endereços
 * a partir da consulta de CEP, com tolerância a falhas.
 */
@Slf4j
@AllArgsConstructor
public class CepService implements CepUseCase {

    private final EnderecoRepositoryPort enderecoRepository;
    private final LogRepositoryPort logRepositoryPort;
    private final CepExternoPort cepPort;

    /**
     * Consulta e salva endereço, com mecanismos de retry, circuit breaker e timeout.
     *
     * @param cep CEP a ser consultado
     * @return CompletableFuture<Endereco> de forma resiliente
     */
    @Override
    @Retry(name = "cepApi", fallbackMethod = "fallbackBuscarESalvar")
    @CircuitBreaker(name = "cepApi", fallbackMethod = "fallbackBuscarESalvar")
    @TimeLimiter(name = "cepApi", fallbackMethod = "fallbackBuscarESalvar")
    public CompletableFuture<Endereco> buscarESalvar(String cep) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Iniciando consulta para o CEP {}", cep);

            Endereco endereco = cepPort.buscarPorCep(cep);

            enderecoRepository.salvar(endereco);
            logRepositoryPort.salvar(endereco);

            log.info("Endereço salvo com sucesso para o CEP {}", cep);
            return endereco;
        });
    }

    /**
     * Fallback para falhas técnicas (conexão, timeout, etc).
     *
     * @param cep CEP da tentativa
     * @param throwable Exceção original
     * @return CompletableFuture com exceção encapsulada
     */
    public CompletableFuture<Endereco> fallbackBuscarESalvar(String cep, Throwable throwable) {
        log.error("Erro ao consultar o CEP {}. Falha técnica: {}", cep, throwable.getMessage(), throwable);
        logRepositoryPort.salvarFalha(cep, throwable.getMessage());

        return CompletableFuture.failedFuture(
                new ExternalServiceException("Serviço de consulta de CEP indisponível no momento. Tente novamente mais tarde.")
        );
    }
}
