package br.edu.ifam.saf.api.interceptor;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Priority;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import br.edu.ifam.saf.api.util.Respostas;
import br.edu.ifam.saf.dao.UsuarioDAO;
import br.edu.ifam.saf.modelo.Usuario;

/**
 * adaptado de http://stackoverflow.com/questions/26777083/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey
 */

@Provider
@RequerLogin
@Priority(Priorities.AUTHENTICATION)

public class AutenticacaoFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_PROPERTY = "Bearer";


    @Inject
    @UsuarioAutenticado
    private Event<Usuario> usuarioAutenticadoEvento;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authString = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isEmpty(authString) || !authString.startsWith(AUTHORIZATION_PROPERTY)) {
            requestContext.abortWith(Respostas.acessoNegado());
            return;
        }
        String token = authString.substring(AUTHORIZATION_PROPERTY.length()).trim();

        Usuario usuario = usuarioDAO.consultarPorToken(token);
        if (usuario == null) {
            requestContext.abortWith(Respostas.acessoNegado());
            return;
        }
        usuarioAutenticadoEvento.fire(usuario);

    }

}
