package br.edu.ifam.saf.api.endpoint;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.AlugueisResponse;
import br.edu.ifam.saf.api.dto.AluguelTransformer;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.dao.AluguelDAO;

@Stateless
@Path("/alugueis")
public class AluguelEndpoint {

    @Inject
    private AluguelTransformer aluguelTransformer;

    @Inject
    private AluguelDAO aluguelDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response algueis() {
        return Response.ok().entity(
                AlugueisResponse.from(aluguelTransformer.toDTOList(aluguelDAO.listarTodos()))
        ).build();
    }

}
