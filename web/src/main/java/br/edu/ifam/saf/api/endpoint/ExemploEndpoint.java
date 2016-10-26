package br.edu.ifam.saf.api.endpoint;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.dto.ItemTransformer;
import br.edu.ifam.saf.api.dto.UsuarioTransformer;
import br.edu.ifam.saf.api.interceptor.RequerLogin;
import br.edu.ifam.saf.api.interceptor.UsuarioAutenticado;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.modelo.Item;
import br.edu.ifam.saf.modelo.Usuario;

@Path("/exemplo")
@Stateless
public class ExemploEndpoint {

    @Inject
    ItemTransformer itemTransformer;

    @Inject
    UsuarioTransformer usuarioTransformer;

    @Inject
    @UsuarioAutenticado
    Usuario usuario;

    @GET
    @Path("/logado")
    @RequerLogin
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    public Response exemploLogado() {
        return Response.ok().entity(new ItensResponse(itemTransformer.toDTO(new Item("Exemplo Logado", 20.0))))
                .build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    public Response semLogin() {
        return Response.ok().entity(new ItensResponse(itemTransformer.toDTO(new Item("Exemplo Não Logado", 20.0))))
                .build();
    }

    @GET
    @Path("/perfil")
    @RequerLogin(Perfil.ADMINISTRADOR)
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    public Response exemploLogadoPerfil() {
        return Response.ok().entity(new ItensResponse(itemTransformer.toDTO(new Item("Exemplo Logado", 20.0))))
                .build();
    }

    @GET
    @RequerLogin
    @Path("/injecao")
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    public Response exemploUsuarioLogado() {
        return Response.ok().entity(usuarioTransformer.toDTO(usuario)).build();
    }
}