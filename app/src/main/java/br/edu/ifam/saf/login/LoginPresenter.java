package br.edu.ifam.saf.login;


import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.data.LocalRepository;
import br.edu.ifam.saf.util.ApiCallback;
import br.edu.ifam.saf.util.ApiManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter implements LoginContract.Presenter {


    private LoginContract.View view;
    private LocalRepository repository;

    public LoginPresenter(LoginContract.View view, LocalRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void login(LoginData loginData) {
        ApiManager.getService().login(loginData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<UsuarioDTO>() {
                    @Override
                    public void onSuccess(UsuarioDTO usuario) {
                        view.esconderLoading();
                        repository.salvarInfoUsuario(usuario);
                        view.mostrarMensagem(String.format("Bem vindo %s", usuario.getNome()));
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.esconderLoading();
                        view.mostrarInfoMensagem(mensagem.getMensagens().get(0));
                    }

                    @Override
                    public boolean canExecute() {
                        return view != null;
                    }
                })
        ;


    }

    @Override
    public void destroy() {
        view = null;
    }

}
