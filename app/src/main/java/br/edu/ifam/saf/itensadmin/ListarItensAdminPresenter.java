package br.edu.ifam.saf.itensadmin;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListarItensAdminPresenter implements ItensAdminContract.Presenter{

    ItensAdminContract.View view;
    private SAFService service;

    public ListarItensAdminPresenter(ItensAdminContract.View view, SAFService service) {
        this.view = view;
        this.service = service;
    }

    public void carregarItens() {
        view.mostrarLoading();
        service.listarItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<ItensResponse>() {
                    @Override
                    public void onSuccess(ItensResponse response) {
                        view.esconderLoading();
                        view.mostrarItens(response.getItems());
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
                });
    }

    @Override
    public void onFabAction() {
        view.mostrarTelaCriarConta();
    }

    @Override
    public void carregarListaDeItens() {
        carregarItens();
    }

    @Override
    public void itemClicado(ItemDTO item) {
        view.mostrarTelaEditarItem(item.getId());
    }

    @Override
    public void destroy() {
        view = null;
    }
}
