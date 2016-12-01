package br.edu.ifam.saf.itensadmin;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.api.data.StatusItemData;
import br.edu.ifam.saf.enums.StatusItem;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListarItensAdminPresenter implements ItensAdminContract.Presenter{

    ItensAdminContract.View view;
    private SAFService service;
    private StatusItem ultimoStatusRequest = StatusItem.ATIVO;

    public ListarItensAdminPresenter(ItensAdminContract.View view, SAFService service) {
        this.view = view;
        this.service = service;
    }

    public void carregarItens(StatusItem status) {
        view.mostrarLoading();
        ultimoStatusRequest = status;
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
        view.mostrarTelaNovoItem();
    }

    @Override
    public void carregarListaDeItens(StatusItem status) {
        carregarItens(StatusItem.ATIVO);
    }

    @Override
    public void itemClicado(ItemDTO item) {
        view.mostrarTelaEditarItem(item.getId());
    }

    @Override
    public void atualizarStatus(ItemDTO item, StatusItem status) {
        view.mostrarLoading();
        service.atualizaItemStatus(item.getId(), new StatusItemData(status))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new ApiCallback<Void>() {
            @Override
            public void onSuccess(Void response) {
                view.esconderLoading();
                view.mostrarInfoMensagem("Item atualizado");
                carregarItens(ultimoStatusRequest);
            }

            @Override
            public void onError(MensagemErroResponse mensagem) {
                view.esconderLoading();
                view.mostrarInfoMensagem(mensagem.join());
            }

            @Override
            public boolean canExecute() {
                return view != null;
            }
        });
    }

    @Override
    public void destroy() {
        view = null;
    }
}
