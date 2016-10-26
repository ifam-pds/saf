package br.edu.ifam.saf.criarconta;

import br.edu.ifam.saf.api.dto.UsuarioDTO;

interface CriarContaContract {
    interface View{
        void mostrarMensagemDeErro(String mensagem);
        void mostrarMensagemContaCriada();
    }

    interface Presenter {
        void registrar(UsuarioDTO usuarioDTO);

        void destroy();
    }
}
