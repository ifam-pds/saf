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

    private void loadData() {
        view.showLoadingIndicator();
        api.listarItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<ItensResponse>>() {
                    @Override
                    public void call(Result<ItensResponse> response) {
                        if (!response.isError()) {
                            if (view != null) {
                                view.showItens(response.response().body().getItems());
                                view.hideLoadingIndicator();
                            }
                        }

                    }
                });

    }

    @Override
    public void start() {
        loadData();
    }

    @Override
    public void destroy() {
        view = null;

    }

    @Override
    public void onItemClick(ItemDTO item) {
        view.showItem(item);
    }

    @Override
    public void refresh() {
        loadData();
    }
}
