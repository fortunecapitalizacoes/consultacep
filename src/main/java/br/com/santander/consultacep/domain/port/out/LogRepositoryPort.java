package br.com.santander.consultacep.domain.port.out;

import br.com.santander.consultacep.domain.model.Endereco;

public interface LogRepositoryPort {
    void salvar(Endereco endereco);

    /**
     * Registra falhas ocorridas ao tentar buscar um CEP na API externa.
     *
     * @param cep o CEP que causou falha
     * @param motivo descrição ou stack trace da falha
     */
    void salvarFalha(String cep, String motivo);
}
