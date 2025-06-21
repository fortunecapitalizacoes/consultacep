package br.com.santander.consultacep.domain.port.out;

import br.com.santander.consultacep.domain.model.Endereco;

/**
 * Porta de saída responsável por realizar a comunicação com sistemas externos
 * para obtenção de informações de endereço a partir de um CEP.
 *
 * <p>Esta interface faz parte da arquitetura hexagonal e deve ser implementada
 * por adaptadores que consomem APIs externas, como o ViaCEP, por exemplo.</p>
 */
public interface CepExternoPort {

    /**
     * Realiza a busca de um endereço em uma fonte externa com base no CEP informado.
     *
     * @param cep o código de endereçamento postal a ser consultado.
     * @return o {@link Endereco} correspondente ao CEP informado.
     */
    Endereco buscarPorCep(String cep);
}
