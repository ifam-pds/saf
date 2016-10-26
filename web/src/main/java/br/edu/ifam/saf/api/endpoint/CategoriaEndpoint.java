package br.edu.ifam.saf.api.endpoint;

import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.CategoriasResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.CategoriaDTO;
import br.edu.ifam.saf.api.dto.CategoriaTransformer;
import br.edu.ifam.saf.api.interceptor.RequerLogin;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.api.util.Respostas;
import br.edu.ifam.saf.dao.CategoriaDAO;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.exception.ValidacaoError;
import br.edu.ifam.saf.modelo.Categoria;

@Stateless
@Path("/categorias")
public class CategoriaEndpoint {

    @Inject
    CategoriaTransformer categoriaTransformer;

    @Inject
    CategoriaDAO dao;

    @Inject
    private Logger log;


    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response categorias() {
        return Response.ok().entity(new CategoriasResponse(
                categoriaTransformer.toDTOList(dao.listarTodos()))
        ).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON_UTF8)
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @RequerLogin(Perfil.ADMINISTRADOR)
    @Path("/")
    public Response cadastrar(CategoriaDTO categoriaDTO) {
        try {
            Categoria categoriaACadastrar = categoriaTransformer.toEntity(categoriaDTO);

            dao.atualizar(categoriaACadastrar);

            log.info(">>> " + categoriaACadastrar);

            return Respostas.criado();
        } catch (ValidacaoError ex) {
            return Respostas.badRequest(ex.getMensagemErroResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Respostas.badRequest(new MensagemErroResponse(ex.getMessage()));
        }
    }
}
