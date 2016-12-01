package br.edu.ifam.saf.itens;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.ItensResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.data.StatusItemData;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.enums.StatusItem;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
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
        api.listarItems(StatusItem.ATIVO)
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
