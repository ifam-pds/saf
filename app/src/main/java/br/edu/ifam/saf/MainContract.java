package br.edu.ifam.saf;


import br.edu.ifam.saf.api.dto.UsuarioDTO;

public interface MainContract {

    interface View {
        void setInfoUsuario(UsuarioDTO usuario);

        void limpaInfoUsuario();

        void esconderOpcaoAdminCategorias();

        void mostrarOpcaoAdminCategorias();

        void esconderOpcaoAdminItens();

        void mostrarOpcaoAdminItens();

        void esconderOpcaoAdminRequisicoes();

        void mostrarOpcaoAdminRequisicoes();

        void esconderOpcaoAdminUsuarios();

        void mostrarOpcaoAdminUsuarios();

        void esconderOpcaoRelatorios();

        void mostrarOpcaoRelatorios();

        void iniciaTelaLogin();
    }

    interface Presenter {
        void init();

        void onLogActionClicked();

        void recarregarPermissoes();

    }
}
