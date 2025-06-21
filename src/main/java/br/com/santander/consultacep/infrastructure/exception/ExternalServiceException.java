package br.com.santander.consultacep.infrastructure.exception;

/**
 * Exceção que representa falhas ao se comunicar com serviços externos.
 *
 * <p>É lançada quando ocorre algum erro durante a chamada a APIs de terceiros,
 * como o ViaCEP, indicando que o serviço externo não pôde ser acessado ou retornou
 * uma resposta inesperada.</p>
 *
 * <p>Essa exceção é do tipo {@link RuntimeException}, portanto não exige tratamento obrigatório.</p>
 */
public class ExternalServiceException extends RuntimeException {

    /**
     * Cria uma nova instância da exceção com a mensagem fornecida.
     *
     * @param message a mensagem descritiva do erro.
     */
    public ExternalServiceException(String message) {
        super(message);
    }
}
