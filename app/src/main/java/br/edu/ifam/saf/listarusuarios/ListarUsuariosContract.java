package br.edu.ifam.saf.listarusuarios;

import java.util.List;

import br.edu.ifam.saf.api.dto.UsuarioDTO;

public interface ListarUsuariosContract {

    interface View{
        void mostrarUsuarios(List<UsuarioDTO> usuarios);

        void mostrarLoading();

        void esconderLoading();

        void mostrarMensagem(String mensagem);
    }

    interface Presenter{

        void destroy();

        void carregarUsuarios();

    }

}
