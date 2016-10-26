package br.edu.ifam.saf.api.endpoint;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.BairrosResponse;
import br.edu.ifam.saf.api.dto.BairroTransformer;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.dao.EnderecoService;

@Stateless
@Path("/bairros")
public class BairroEndpoint {

    @Inject
    private BairroTransformer bairroTransformer;

    @Inject
    private EnderecoService bairroDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response bairros() {

        return Response.ok().entity(
                new BairrosResponse(
                        bairroTransformer.toDTOList(bairroDAO.listarTodos())
                )

        ).build();
    }
}