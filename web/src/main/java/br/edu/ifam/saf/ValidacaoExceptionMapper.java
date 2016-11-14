package br.edu.ifam.saf;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.edu.ifam.saf.api.util.Respostas;
import br.edu.ifam.saf.exception.ValidacaoError;

@Provider
public class ValidacaoExceptionMapper implements ExceptionMapper<ValidacaoError> {
    @Override
    public Response toResponse(ValidacaoError validacaoError) {
        return Respostas.badRequest(validacaoError.getMensagemErroResponse());
    }
}
