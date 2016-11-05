package br.edu.ifam.saf.criarconta;

import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

interface CriarContaContract {
    interface View extends LoadingView {
        void mostrarMensagemContaCriada();
    }

    interface Presenter extends BasePresenter {
        void registrar(UsuarioDTO usuarioDTO);
    }
}
