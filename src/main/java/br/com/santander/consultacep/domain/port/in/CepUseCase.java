package br.com.santander.consultacep.domain.port.in;

import java.util.concurrent.CompletableFuture;
import br.com.santander.consultacep.domain.model.Endereco;

/**
 * Porta de entrada da aplicação responsável pela orquestração
 * do caso de uso de busca e persistência de endereços a partir de um CEP.
 *
 * <p>Faz parte da arquitetura hexagonal, sendo invocada por adaptadores primários
 * como controllers REST, mensageria ou qualquer outro mecanismo de entrada.</p>
 */
public interface CepUseCase {

    /**
     * Busca o endereço correspondente ao CEP informado em uma fonte externa
     * e o persiste em um repositório local.
     *
     * @param cep o código de endereçamento postal a ser consultado.
     * @return um {@link CompletableFuture} contendo o {@link Endereco} correspondente ao CEP.
     */
    CompletableFuture<Endereco> buscarESalvar(String cep);
}
