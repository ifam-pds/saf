package br.edu.ifam.saf.api.endpoint;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import br.edu.ifam.saf.api.data.ItensAluguelResponse;
import br.edu.ifam.saf.api.util.MediaType;

import br.edu.ifam.saf.api.dto.ItemAluguelTransformer;
import br.edu.ifam.saf.dao.ItemAluguelDAO;

@Stateless
@Path("/aluguel")
public class AluguelEndpoint {

    @Inject
    private ItemAluguelTransformer itemAluguelTransformer;

    @Inject
    private ItemAluguelDAO itemAluguelDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response itensEmAluguel(){
        return Response.ok().entity(new ItensAluguelResponse(
                itemAluguelTransformer.toDTOList(itemAluguelDAO.listarTodos()))
        ).build();
    }

}
