package br.edu.ifam.saf.api.interceptor;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.modelo.Usuario;

@Provider
@RequerLogin
@Priority(Priorities.AUTHORIZATION)
public class AutorizacaoFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    @UsuarioAutenticado
    private Usuario usuario;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Method metodo = resourceInfo.getResourceMethod();
        List<Perfil> perfisDoMetodo = extrairPerfis(metodo);

        checarPermissoes(perfisDoMetodo);
    }

    private List<Perfil> extrairPerfis(AnnotatedElement elemento) {
        if (elemento == null) {
            return Collections.emptyList();
        } else {
            RequerLogin secured = elemento.getAnnotation(RequerLogin.class);
            if (secured == null) {
                return Collections.emptyList();
            } else {
                Perfil[] perfisPermitidos = secured.value();
                return Arrays.asList(perfisPermitidos);
            }
        }
    }

    private void checarPermissoes(List<Perfil> perfisPermitidos) throws ForbiddenException {
        if (!perfisPermitidos.isEmpty() && !perfisPermitidos.contains(usuario.getPerfil())) {
            throw new ForbiddenException(Response.status(403).entity(new MensagemErroResponse("Acesso negado")).build());
        }
    }
}