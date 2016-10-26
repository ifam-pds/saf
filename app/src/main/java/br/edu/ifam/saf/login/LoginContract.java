package br.edu.ifam.saf.login;


import br.edu.ifam.saf.api.data.LoginData;

interface LoginContract {
    interface View {
        void mostrarMensagem(String message);
    }

    interface Presenter {
        void login(LoginData loginData);
        void destroy();
    }
}
