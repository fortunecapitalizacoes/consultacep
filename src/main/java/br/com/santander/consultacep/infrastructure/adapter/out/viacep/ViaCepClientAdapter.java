package br.com.santander.consultacep.infrastructure.adapter.out.viacep;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.out.CepExternoPort;

@Component
public class ViaCepClientAdapter implements CepExternoPort {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Endereco buscarPorCep(String cep) {
        ViaCepResponse response = restTemplate.getForObject(
                "http://localhost:8080/cep/" + cep, ViaCepResponse.class);
        
     

        if (response == null || response.getCep() == null) {
            throw new RuntimeException("CEP inválido ou não encontrado.");
        }

        return new Endereco(
                response.getCep(),
                response.getLogradouro(),
                response.getBairro(),
                response.getLocalidade(),
                response.getUf()
        );
    }
}