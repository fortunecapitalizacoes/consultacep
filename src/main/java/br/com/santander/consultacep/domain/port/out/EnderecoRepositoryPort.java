package br.com.santander.consultacep.domain.port.out;

import br.com.santander.consultacep.domain.model.Endereco;

/**
 * Porta de saída responsável pela persistência de dados relacionados ao {@link Endereco}.
 *
 * <p>Faz parte da arquitetura hexagonal e deve ser implementada por adaptadores
 * que interagem com mecanismos de armazenamento, como bancos de dados relacionais
 * ou NoSQL.</p>
 */
public interface EnderecoRepositoryPort {

    /**
     * Persiste um objeto {@link Endereco} no repositório.
     *
     * @param endereco o endereço a ser salvo.
     */
    void salvar(Endereco endereco);
}
