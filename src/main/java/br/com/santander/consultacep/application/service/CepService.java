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

/**
 * Serviço responsável pela busca e persistência de Endereços
 * a partir da consulta de CEP.
 * 
 * Aplica retry e circuit breaker para tolerância a falhas em chamadas externas.
 */
@Slf4j
@AllArgsConstructor
public class CepService implements CepUseCase {

    private final EnderecoRepositoryPort enderecoRepository;
    private final LogRepositoryPort logRepositoryPort;
    private final CepExternoPort cepPort;

    /**
     * Consulta um endereço pelo CEP e salva o resultado no repositório.
     * 
     * Caso a chamada ao serviço externo falhe, será aplicado retry e circuit breaker,
     * com fallback para método alternativo.
     * 
     * @param cep CEP a ser consultado
     * @return Endereço consultado e salvo
     * @throws ExternalServiceException se o serviço externo estiver indisponível
     */
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

    /**
     * Método fallback chamado quando a consulta ao serviço externo falhar,
     * após tentativas de retry e acionamento do circuit breaker.
     * 
     * Registra a falha no log e lança exceção personalizada.
     * 
     * @param cep CEP que originou a falha
     * @param throwable exceção original lançada
     * @return nunca retorna (lança exceção)
     * @throws ExternalServiceException exceção indicando indisponibilidade do serviço externo
     */
    public Endereco fallbackBuscarESalvar(String cep, Throwable throwable) {
        log.error("Erro ao consultar o CEP {}: {}", cep, throwable.getMessage());
        logRepositoryPort.salvarFalha(cep, throwable.getMessage());
        throw new ExternalServiceException("Serviço de consulta de CEP indisponível no momento. Tente novamente mais tarde.");
    }
}
