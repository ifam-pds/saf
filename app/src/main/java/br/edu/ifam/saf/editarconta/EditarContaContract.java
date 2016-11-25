package br.edu.ifam.saf.editarconta;

import java.util.List;

import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.enums.Perfil;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

interface EditarContaContract {

    interface View extends LoadingView{
        void mostrarMensagemContaAlterada();
        void mostrarUsuario(UsuarioDTO usuarioDTO);
        void mostrarPerfis(List<Perfil> perfis);
    }

    interface Presenter extends BasePresenter{
        void carregarPerfis();
        void carregarUsuario();
        void alterar(UsuarioDTO usuarioDTO);
    }

}
