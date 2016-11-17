package br.edu.ifam.saf.api.endpoint;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.AlugueisResponse;
import br.edu.ifam.saf.api.data.StatusData;
import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.api.dto.AluguelTransformer;
import br.edu.ifam.saf.api.dto.ItemTransformer;
import br.edu.ifam.saf.api.interceptor.RequerLogin;
import br.edu.ifam.saf.api.interceptor.UsuarioAutenticado;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.api.util.Respostas;
import br.edu.ifam.saf.dao.AluguelDAO;
import br.edu.ifam.saf.dao.ItemAluguelDAO;
import br.edu.ifam.saf.dao.UsuarioDAO;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.enums.StatusAluguel;
import br.edu.ifam.saf.exception.ValidacaoError;
import br.edu.ifam.saf.modelo.Aluguel;
import br.edu.ifam.saf.modelo.Usuario;

@Stateless
@Path("/alugueis")
public class AluguelEndpoint {

    @Inject
    private AluguelTransformer aluguelTransformer;

    @Inject
    private ItemTransformer itemTransformer;

    @Inject
    private AluguelDAO aluguelDAO;

    @Inject
    private ItemAluguelDAO itemAluguelDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    @UsuarioAutenticado
    private Usuario usuarioLogado;

    @GET
    @RequerLogin({Perfil.FUNCIONARIO, Perfil.ADMINISTRADOR})
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response listarAlugueis(@QueryParam("status") StatusAluguel statusAluguel) {
        List<AluguelDTO> lista;
        if (statusAluguel != null) {
            lista = aluguelTransformer.toDTOList(aluguelDAO.filtrarPorStatus(statusAluguel));
        } else {
            lista = aluguelTransformer.toDTOList(aluguelDAO.listarTodos());
        }
        return Response.ok().entity(
                AlugueisResponse.from(lista)
        ).build();

    }

    @PUT
    @RequerLogin({Perfil.FUNCIONARIO, Perfil.ADMINISTRADOR})
    @Consumes(MediaType.APPLICATION_JSON_UTF8)
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/{id}")
    public Response atualizarStatus(@PathParam("id") Integer id, StatusData statusData) {
        final Aluguel aluguel = aluguelDAO.consultar(id);
        aluguel.setStatus(statusData.getStatus());

        return Response.accepted(aluguelDAO.atualizar(aluguel)).build();
    }

    @POST
    @RequerLogin({Perfil.ADMINISTRADOR, Perfil.FUNCIONARIO, Perfil.CLIENTE})
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response cadastrarAluguel(AluguelDTO aluguelDTO) {
        if (aluguelDTO.getItens() == null || aluguelDTO.getItens().isEmpty()) {
            throw new ValidacaoError("Não há itens no carrinho! ERRO!");
        }

        final Aluguel aluguel = aluguelTransformer.toEntity(aluguelDTO);
        aluguel.setDataHoraInicio(new Date());
        aluguel.setDataHoraDevolucao(new Date());
        aluguel.setStatus(StatusAluguel.RESERVA_PENDENTE);
        aluguel.setId(null);
        aluguel.setCliente(usuarioDAO.consultar(usuarioLogado.getId()));
        aluguelDAO.inserir(aluguel);
        return Respostas.criado();
    }

}
