package br.com.santander.consultacep.infrastructure.adapter.out.database.log;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.out.LogRepositoryPort;
import br.com.santander.consultacep.infrastructure.adapter.out.database.endereco.EnderecoEntity;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LogPersistenceAdapter implements LogRepositoryPort {

    private final LogJpaRepository repository;

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
