package br.com.santander.consultacep.domain.model;

import lombok.AllArgsConstructor;

/**
 * Modelo de domínio que representa um registro de log relacionado
 * a operações envolvendo endereços (CEP).
 *
 * <p>Herda os atributos da classe {@link Endereco}, podendo ser
 * estendida para incluir informações específicas de log, caso necessário.</p>
 *
 * <p>Atualmente possui apenas o construtor completo gerado pela anotação {@link AllArgsConstructor}.</p>
 */
@AllArgsConstructor
public class Log extends Endereco {
    // Pode ser extendida futuramente com atributos específicos de log
}
