package br.edu.ifam.saf.login;


import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

interface LoginContract {
    interface View extends LoadingView {
        void mostrarMensagem(String message);
    }

    interface Presenter extends BasePresenter {
        void login(LoginData loginData);

    }
}
