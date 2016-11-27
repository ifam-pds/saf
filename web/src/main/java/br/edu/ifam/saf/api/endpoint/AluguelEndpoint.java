package br.edu.ifam.saf.api.endpoint;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.api.dto.AluguelTransformer;
import br.edu.ifam.saf.api.interceptor.RequerLogin;
import br.edu.ifam.saf.api.interceptor.UsuarioAutenticado;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.api.util.Respostas;
import br.edu.ifam.saf.dao.AluguelDAO;
import br.edu.ifam.saf.dao.UsuarioDAO;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.enums.StatusItemAluguel;
import br.edu.ifam.saf.exception.ValidacaoError;
import br.edu.ifam.saf.modelo.Aluguel;
import br.edu.ifam.saf.modelo.ItemAluguel;
import br.edu.ifam.saf.modelo.Usuario;

@Stateless
@Path("/alugueis")
public class AluguelEndpoint {

    @Inject
    private AluguelTransformer aluguelTransformer;

    @Inject
    private AluguelDAO aluguelDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    @UsuarioAutenticado
    private Usuario usuarioLogado;

    @POST
    @RequerLogin({Perfil.ADMINISTRADOR, Perfil.FUNCIONARIO, Perfil.CLIENTE})
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response cadastrarAluguel(AluguelDTO aluguelDTO) {
        if (aluguelDTO.getItens() == null || aluguelDTO.getItens().isEmpty()) {
            throw new ValidacaoError("Não há itens no carrinho!");
        }

        final Aluguel aluguel = aluguelTransformer.toEntity(aluguelDTO);
        for (ItemAluguel itemAluguel : aluguel.getItens()) {
            itemAluguel.setStatus(StatusItemAluguel.RESERVA_PENDENTE);
        }
        aluguel.setId(null);
        aluguel.setCliente(usuarioDAO.consultar(usuarioLogado.getId()));
        aluguelDAO.inserir(aluguel);
        return Respostas.criado();
    }

}
