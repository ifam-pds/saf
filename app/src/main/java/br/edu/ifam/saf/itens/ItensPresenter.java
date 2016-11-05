package br.edu.ifam.saf.itens;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.dto.ItemDTO;
import retrofit2.adapter.rxjava.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class ItensPresenter implements ItensContract.Presenter {
    private ItensContract.View view;
    private SAFService api;

    public ItensPresenter(ItensContract.View view, SAFService api) {
        this.view = view;
        this.api = api;
    }

    private void carregarDados() {
        view.mostrarLoading();
        api.listarItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<ItensResponse>>() {
                    @Override
                    public void call(Result<ItensResponse> response) {
                        if (view != null) {
                            view.esconderLoading();
                            if (!response.isError()) {
                                view.mostrarItens(response.response().body().getItems());
                            }

                        }

                    }
                });

    }

    @Override
    public void carregarListaDeItens() {
        carregarDados();
    }

    @Override
    public void destroy() {
        view = null;

    }

    @Override
    public void onItemClick(ItemDTO item) {
        view.mostrarItem(item);
    }

    @Override
    public void atualizar() {
        carregarDados();
    }
}
