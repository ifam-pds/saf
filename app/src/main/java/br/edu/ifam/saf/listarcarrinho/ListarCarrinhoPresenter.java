package br.edu.ifam.saf.listarcarrinho;

import android.util.Log;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.util.ApiManager;
import retrofit2.adapter.rxjava.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ListarCarrinhoPresenter implements ListarCarrinhoContract.Presenter {

    ListarCarrinhoContract.View view;
    private SAFService service;

    public ListarCarrinhoPresenter(ListarCarrinhoContract.View view, SAFService service){

        this.view = view;
        this.service = service;

    }


    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void carregarCarrinho() {

        service.listarItems().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Result<ItensResponse>>() {
            @Override
            public void call(Result<ItensResponse> result) {
                if (view != null) {
                    view.esconderLoading();
                    if (result.isError()) {
                        Log.e(ListarCarrinhoPresenter.class.getSimpleName(), "Erro de conexão", result.error());
                        view.mostrarMensagem("Erro de conexão");
                    } else if (result.response().isSuccessful()) {
                        view.mostrarCarrinho(result.response().body().getItems());
                    } else {
                        MensagemErroResponse erroResponse = ApiManager.parseErro(result.response());
                        view.mostrarMensagem(erroResponse.getMensagens().get(0));
                    }
                }
            }
        });

    }
}
