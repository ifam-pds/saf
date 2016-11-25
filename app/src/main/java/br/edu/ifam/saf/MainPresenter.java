package br.edu.ifam.saf;

import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.data.LocalRepository;
import br.edu.ifam.saf.enums.Perfil;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;
    private final LocalRepository repository;

    public MainPresenter(MainContract.View view, LocalRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void init() {
        recarregarPermissoes();
    }

    @Override
    public void recarregarPermissoes() {
        if (view == null) return;

        if (repository.getInfoUsuario() != null) {
            view.setInfoUsuario(repository.getInfoUsuario());
        } else {
            view.limpaInfoUsuario();
        }
        esconderTodasAsOpcoes();
        UsuarioDTO infoUsuario = repository.getInfoUsuario();

        Perfil perfil = infoUsuario == null ? Perfil.CLIENTE : infoUsuario.getPerfil();

        if (perfil.getNivel() == Perfil.CLIENTE.getNivel()) {
            esconderTodasAsOpcoes();
        } else
            if (perfil.getNivel() == Perfil.FUNCIONARIO.getNivel()) {
                view.mostrarOpcaoAdminRequisicoes();
            } else
                if (perfil.getNivel() == Perfil.ADMINISTRADOR.getNivel()) {
                    view.mostrarOpcaoAdminUsuarios();
                    view.mostrarOpcaoAdminItens();
                    view.mostrarOpcaoAdminCategorias();
                    view.mostrarOpcaoAdminRequisicoes();
                }

    }

    private void esconderTodasAsOpcoes() {
        view.esconderOpcaoAdminItens();
        view.esconderOpcaoAdminRequisicoes();
        view.esconderOpcaoAdminUsuarios();
        view.esconderOpcaoAdminCategorias();
    }

    @Override
    public void onLogActionClicked() {
        if (repository.getInfoUsuario() == null) {
            view.iniciaTelaLogin();
        } else {
            repository.salvarInfoUsuario(null);
            view.limpaInfoUsuario();
            recarregarPermissoes();
        }


    }
}
