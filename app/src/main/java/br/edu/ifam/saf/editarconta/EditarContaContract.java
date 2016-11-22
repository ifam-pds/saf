package br.edu.ifam.saf.editarconta;

import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

/**
 * Created by marciogabriel on 21/11/16.
 */

interface EditarContaContract {

    interface View extends LoadingView{
        void mostrarMensagemContaAlterada();
        void mostrarUsuario(UsuarioDTO usuarioDTO);
    }

    interface Presenter extends BasePresenter{
        void carregarPerfil();
        void carregarUsuario();
        void alterar(UsuarioDTO usuarioDTO);
    }

}
