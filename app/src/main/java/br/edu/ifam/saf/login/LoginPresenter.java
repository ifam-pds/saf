package br.edu.ifam.saf.login;


import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiManager;
import retrofit2.adapter.rxjava.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {


    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void login(LoginData loginData) {
        ApiManager.getService().login(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<UsuarioDTO>>() {
                    @Override
                    public void call(Result<UsuarioDTO> result) {
                        if (result.response().isSuccessful()) {
                            view.mostrarMensagem(String.format("Bem vindo %s", result.response().body().getNome()));
                        } else {
                            MensagemErroResponse mensagem = ApiManager.parseErro(result.response());
                            view.mostrarMensagem(mensagem.getMensagens().get(0));
                        }
                    }
                })
        ;


    }

    @Override
    public void destroy() {
        view = null;
    }

}
