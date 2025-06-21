package br.com.santander.consultacep.domain.port.out;

import br.com.santander.consultacep.domain.model.Endereco;

/**
 * Porta de saída responsável pelo registro de logs relacionados
 * à busca e persistência de endereços.
 *
 * <p>Faz parte da arquitetura hexagonal e deve ser implementada por adaptadores
 * que armazenam logs em arquivos, bancos de dados, ferramentas de observabilidade, etc.</p>
 */
public interface LogRepositoryPort {

    /**
     * Registra um log de sucesso contendo os dados de um {@link Endereco}
     * que foi consultado e/ou persistido com êxito.
     *
     * @param endereco o endereço a ser registrado no log.
     */
    void salvar(Endereco endereco);

    /**
     * Registra falhas ocorridas ao tentar buscar um CEP na API externa.
     *
     * @param cep o código de endereçamento postal que causou a falha.
     * @param motivo descrição da falha, mensagem de erro ou stack trace.
     */
    void salvarFalha(String cep, String motivo);
}
