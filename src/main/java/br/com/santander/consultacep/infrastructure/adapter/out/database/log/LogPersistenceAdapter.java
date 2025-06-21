package br.com.santander.consultacep.infrastructure.adapter.out.database.log;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.out.LogRepositoryPort;
import br.com.santander.consultacep.infrastructure.adapter.out.database.endereco.EnderecoEntity;
import lombok.AllArgsConstructor;

/**
 * Adapter de persistência responsável pelo armazenamento de logs.
 * relacionados a consultas de CEP.
 * 
 * Implementa a porta de repositório de logs para salvar logs.
 * de sucesso e falha no banco de dados.
 */
@Component
@AllArgsConstructor
public class LogPersistenceAdapter implements LogRepositoryPort {

    private final LogJpaRepository repository;

    /**
     * Salva um log de sucesso contendo os dados do endereço consultado.
     * 
     * @param endereco endereço consultado para registro no log.
     */
    @Override
    public void salvar(Endereco endereco) {
        var edereco = EnderecoEntity.builder()
                .bairro(endereco.getBairro())
                .cep(endereco.getCep())
                .localidade(endereco.getLocalidade())
                .logradouro(endereco.getLogradouro())
                .uf(endereco.getUf())
                .build();

        var logEntity = LogEntity.builder()
                .cep(endereco.getCep())
                .dadosRetornoApiViaCep(edereco)
                .horarioRequicicao(LocalDateTime.now())
                .build();

        repository.save(logEntity);
    }

    /**
     * Salva um log de falha contendo o CEP consultado e o motivo da falha.
     * 
     * @param cep CEP que originou a falha.
     * @param motivo descrição do motivo da falha.
     */
    @Override
    public void salvarFalha(String cep, String motivo) {
        var logEntity = LogEntity.builder()
                .cep(cep)
                .erro(motivo)
                .horarioRequicicao(LocalDateTime.now())
                .build();

        repository.save(logEntity);
    }
}
