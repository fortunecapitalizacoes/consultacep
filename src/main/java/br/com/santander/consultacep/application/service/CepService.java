package br.com.santander.consultacep.application.service;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.in.CepUseCase;
import br.com.santander.consultacep.domain.port.out.CepExternoPort;
import br.com.santander.consultacep.domain.port.out.EnderecoRepositoryPort;
import br.com.santander.consultacep.domain.port.out.LogRepositoryPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CepService implements CepUseCase {

    private final EnderecoRepositoryPort enderecoRepository;
    private final LogRepositoryPort logRepositoryPort;
    private final CepExternoPort cepPort;

    @Override
    public Endereco buscarESalvar(String cep) {
        Endereco endereco = cepPort.buscarPorCep(cep);
        enderecoRepository.salvar(endereco);
        logRepositoryPort.salvar(endereco);
        return endereco;
    }
}