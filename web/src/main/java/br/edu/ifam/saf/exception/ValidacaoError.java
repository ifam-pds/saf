package br.edu.ifam.saf.exception;

import br.edu.ifam.saf.api.data.MensagemErroResponse;

/**
 * Exception com mensagem de erro user friendly
 */
public class ValidacaoError extends RuntimeException {

    private final MensagemErroResponse mensagemErroResponse;

    public ValidacaoError(MensagemErroResponse mensagemErroResponse) {
        this.mensagemErroResponse = mensagemErroResponse;
    }

    public ValidacaoError(String message) {
        this.mensagemErroResponse = new MensagemErroResponse(message);
    }

    public MensagemErroResponse getMensagemErroResponse() {
        return mensagemErroResponse;
    }
}
