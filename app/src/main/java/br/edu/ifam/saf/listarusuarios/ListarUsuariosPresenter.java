package br.edu.ifam.saf.listarusuarios;

import java.util.Arrays;
import java.util.List;

import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiManager;
import br.edu.ifam.saf.view.FieldView;

public class ListarUsuariosPresenter implements ListarUsuariosContract.Presenter{

    ListarUsuariosContract.View view;

    public ListarUsuariosPresenter(ListarUsuariosContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void carregarUsuarios() {
        UsuarioDTO usuario = new UsuarioDTO.Builder().nome("Fulano de tal").cpf("111.111.111-11").build();
        UsuarioDTO usuario1 = new UsuarioDTO.Builder().nome("Siclano de tal").cpf("222.222.222-22").build();
        UsuarioDTO usuario2 = new UsuarioDTO.Builder().nome("Beltrano de tal").cpf("333.333.333-33").build();

        if (view != null) {
            view.esconderLoading();
            view.mostrarUsuarios(Arrays.asList(usuario,usuario1,usuario2));
        }
    }

}
