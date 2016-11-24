package br.edu.ifam.saf.api.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

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
import javax.ws.rs.core.Response;

import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.api.data.UsuariosResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.api.dto.UsuarioTransformer;
import br.edu.ifam.saf.api.interceptor.RequerLogin;
import br.edu.ifam.saf.api.interceptor.UsuarioAutenticado;
import br.edu.ifam.saf.api.util.MediaType;
import br.edu.ifam.saf.api.util.Respostas;
import br.edu.ifam.saf.api.util.Validation;
import br.edu.ifam.saf.dao.UsuarioDAO;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.modelo.Usuario;
import br.edu.ifam.saf.util.SegurancaUtil;

@Path("/usuarios")
@Stateless
public class UsuarioEndpoint {

    @Inject
    private  UsuarioDAO usuarioDAO;

    @Inject
    private UsuarioTransformer usuarioTransformer;

    @Inject
    private Logger log;

    @Inject
    @UsuarioAutenticado
    private Usuario usuarioLogado;

    @POST
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Consumes(MediaType.APPLICATION_JSON_UTF8)
    @Path("/login")
    public Response login(LoginData loginData) {
        Validation.validaLogin(loginData);

        Usuario usuario = usuarioDAO.consultarPorEmail(loginData.getEmail());


        if (usuario == null || !SegurancaUtil.verificaSenha(loginData.getSenha(), usuario.getSenha())) {
            return Respostas.EMAIL_OU_SENHA_INCORRETOS;
        }
        if (StringUtils.isBlank(usuario.getToken())) {
            usuario.setToken(SegurancaUtil.gerarToken());
        }

        return Respostas.ok(usuarioTransformer.toDTO(usuario));


    }

    @POST
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @Consumes(MediaType.APPLICATION_JSON_UTF8)
    @Path("/")
    public Response cadastrar(UsuarioDTO usuarioDTO) {
        Validation.validaRegistroUsuario(usuarioDTO);

        Usuario usuarioExistente = usuarioDAO.consultarPorEmail(usuarioDTO.getEmail());


        if (usuarioExistente != null) {
            return Respostas.USUARIO_JA_EXISTE;
        }

        Usuario usuarioACadastrar = usuarioTransformer.toEntity(usuarioDTO);
        usuarioACadastrar.setId(null);
        usuarioACadastrar.setToken(null);
        usuarioACadastrar.setPerfil(Perfil.CLIENTE);
        usuarioACadastrar.setSenha(SegurancaUtil.hashSenha(usuarioACadastrar.getSenha()));

        usuarioDAO.atualizar(usuarioACadastrar);

        log.info(">>>" + usuarioACadastrar);

        return Respostas.criado();


    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @RequerLogin(Perfil.ADMINISTRADOR)
    @Path("/")
    public Response listar() {

        List<Usuario> usuarios = usuarioDAO.listarTodos();

        return Respostas.ok(new UsuariosResponse(usuarioTransformer.toDTOList(usuarios)));


    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON_UTF8)
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @RequerLogin({Perfil.ADMINISTRADOR, Perfil.CLIENTE, Perfil.FUNCIONARIO})
    @Path("/{id}")
    public Response atualizarUsuario(@PathParam("id") Integer usuario_id, UsuarioDTO usuarioDTO){
        final Usuario usuario = usuarioDAO.consultar(usuario_id);
        final Usuario usuario_atualizar = usuarioTransformer.toEntity(usuarioDTO);

        if(usuario.getId().equals(usuarioLogado.getId()) ||
                usuarioLogado.getPerfil() == Perfil.ADMINISTRADOR){
            usuarioDAO.atualizar(usuario_atualizar);
            return Respostas.ok();
        }else{
            return Respostas.acessoNegado();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_UTF8)
    @RequerLogin(Perfil.ADMINISTRADOR)
    @Path("/{usuario_id}")
    public Response consultarUsuario(@PathParam("usuario_id") Integer usuario_id){
        return Response.ok().entity(usuarioTransformer.toDTO(usuarioDAO.consultar(usuario_id))).build();
    }
}