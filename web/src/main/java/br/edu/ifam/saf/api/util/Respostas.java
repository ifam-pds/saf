package br.edu.ifam.saf.api.util;

import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.MensagemErroResponse;

public final class Respostas {


    public static final Response EMAIL_OU_SENHA_INCORRETOS = badRequest(new MensagemErroResponse("Email ou senha incorretos"));
    public static final Response USUARIO_JA_EXISTE = badRequest(new MensagemErroResponse("Usuário já existe"));
    public static final Response ERRO_INTERNO = Response.serverError().entity(new MensagemErroResponse("Erro interno")).build();

    public static Response badRequest(MensagemErroResponse messageData) {
        return Response.status(Response.Status.BAD_REQUEST).entity(messageData).build();
    }

    public static Response criado() {
        return Response.status(Response.Status.CREATED).build();
    }

    public static Response ok() {
        return Response.ok().build();
    }

    public static Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }


    public static Response acessoNegado() {
        return Response.status(Response.Status.FORBIDDEN).entity(new MensagemErroResponse("Accesso negado")).build();

    }
}
