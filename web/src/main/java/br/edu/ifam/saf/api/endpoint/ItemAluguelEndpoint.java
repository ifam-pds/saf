package br.edu.ifam.saf.api.endpoint;


import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.ItensAluguelResponse;
import br.edu.ifam.saf.api.data.StatusItemAluguelData;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.api.dto.ItemAluguelTransformer;
import br.edu.ifam.saf.api.interceptor.RequerLogin;
import br.edu.ifam.saf.api.interceptor.UsuarioAutenticado;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.api.util.Respostas;
import br.edu.ifam.saf.dao.ItemAluguelDAO;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.enums.StatusItemAluguel;
import br.edu.ifam.saf.modelo.ItemAluguel;
import br.edu.ifam.saf.modelo.Usuario;

@Stateless
@Path("/item_aluguel")
public class ItemAluguelEndpoint {

    @Inject
    private ItemAluguelDAO itemAluguelDAO;

    @Inject
    private ItemAluguelTransformer itemAluguelTransformer;
    @Inject
    @UsuarioAutenticado
    private Usuario usuarioLogado;

    @GET
    @RequerLogin({Perfil.FUNCIONARIO, Perfil.ADMINISTRADOR})
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response listar(@QueryParam("status") StatusItemAluguel status) {
        List<ItemAluguelDTO> lista;

        if (status != null) {
            lista = itemAluguelTransformer.toDTOList(itemAluguelDAO.filtrarPorStatus(status));
        } else {
            lista = itemAluguelTransformer.toDTOList(itemAluguelDAO.listarTodos());
        }
        return Response.ok().entity(ItensAluguelResponse.from(lista)).build();

    }

    @PUT
    @RequerLogin({Perfil.FUNCIONARIO, Perfil.ADMINISTRADOR})
    @Consumes(MediaType.APPLICATION_JSON_UTF8)
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/{item_aluguel_id}")
    public Response atualizarStatus(@PathParam("item_aluguel_id") Integer itemAluguelId, StatusItemAluguelData statusData) {
        ItemAluguel item = itemAluguelDAO.consultar(itemAluguelId);
        if (item == null) {
            return Respostas.badRequest("Item informado não existe");
        }

        item.setStatus(statusData.getStatus());
        return Response.accepted().build();
    }


}
