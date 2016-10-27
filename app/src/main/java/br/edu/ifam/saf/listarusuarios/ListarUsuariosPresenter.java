package br.edu.ifam.saf.listarusuarios;

import android.util.Log;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.data.UsuariosResponse;
import br.edu.ifam.saf.util.ApiManager;
import retrofit2.adapter.rxjava.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ListarUsuariosPresenter implements ListarUsuariosContract.Presenter {

    ListarUsuariosContract.View view;
    private SAFService service;

    public ListarUsuariosPresenter(ListarUsuariosContract.View view, SAFService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void carregarUsuarios() {
        service.listarUsuarios().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Result<UsuariosResponse>>() {
            @Override
            public void call(Result<UsuariosResponse> result) {
                if (view != null) {
                    view.esconderLoading();
                    if (result.isError()) {
                        Log.e(ListarUsuariosPresenter.class.getSimpleName(), "Erro de conexão", result.error());
                        view.mostrarMensagem("Erro de conexão");
                    } else if (result.response().isSuccessful()) {
                        view.mostrarUsuarios(result.response().body().getUsuarios());
                    } else {
                        MensagemErroResponse erroResponse = ApiManager.parseErro(result.response());
                        view.mostrarMensagem(erroResponse.getMensagens().get(0));
                    }
                }
            }
        });
    }

}
