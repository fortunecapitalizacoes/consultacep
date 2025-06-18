package br.com.santander.consultacep.infrastructure.adapter.out.viacep;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.santander.consultacep.domain.model.Endereco;
import br.com.santander.consultacep.domain.port.out.CepExternoPort;
import io.github.resilience4j.retry.annotation.Retry;

/**
 * Adapter para consulta externa de CEP usando o serviço ViaCep.
 * 
 * Utiliza RestTemplate para chamar o endpoint HTTP do serviço ViaCep.
 * Aplica retry com fallback em caso de falhas.
 */
@Component
public class ViaCepClientAdapter implements CepExternoPort {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Consulta o endereço pelo CEP utilizando o serviço ViaCep.
     * Aplica retry em caso de falhas e fallback se não for possível.
     * 
     * @param cep CEP a ser consultado
     * @return objeto Endereco com os dados retornados pelo ViaCep
     * @throws RuntimeException se o CEP for inválido ou não encontrado
     */
    @Override
    @Retry(name = "cepApiRetry", fallbackMethod = "fallbackBuscarPorCep")
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

    /**
     * Método fallback chamado quando todas as tentativas do retry falharem.
     * Deve ser implementado para lidar com a falha.
     * 
     * @param cep CEP que causou a falha
     * @param throwable exceção lançada
     * @return pode lançar exceção ou retornar valor default
     */
    public Endereco fallbackBuscarPorCep(String cep, Throwable throwable) {
        throw new RuntimeException("Serviço ViaCep indisponível. Tente novamente mais tarde.");
    }
}
