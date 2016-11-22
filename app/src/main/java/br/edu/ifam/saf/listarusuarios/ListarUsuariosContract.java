package br.edu.ifam.saf.listarusuarios;

import java.util.List;

import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

public interface ListarUsuariosContract {

    interface View extends LoadingView {
        void mostrarUsuarios(List<UsuarioDTO> usuarios);

        void mostrarMensagem(String mensagem);

        void mostrarTelaEditarUsuario(Integer usuiaroId);
    }

    interface Presenter extends BasePresenter {
        void carregarUsuarios();

        void usuarioClicado(UsuarioDTO usuarioDTO);
    }

}
