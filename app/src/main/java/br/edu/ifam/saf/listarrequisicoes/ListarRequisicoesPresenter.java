package br.edu.ifam.saf.listarrequisicoes;


import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.ItensAluguelResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.data.StatusItemAluguelData;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.enums.StatusItemAluguel;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListarRequisicoesPresenter implements ListarRequisicoesContract.Presenter {


    private SAFService service;
    private ListarRequisicoesContract.View view;
    private StatusItemAluguel ultimoStatusRequest = StatusItemAluguel.RESERVA_PENDENTE;

    public ListarRequisicoesPresenter(ListarRequisicoesContract.View view, SAFService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void carregarRequisicoes(StatusItemAluguel status) {
        view.mostrarLoading();
        ultimoStatusRequest = status;
        service.listarRequisicoes(status).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<ItensAluguelResponse>() {
                    @Override
                    public void onSuccess(ItensAluguelResponse response) {
                        view.esconderLoading();
                        view.mostrarItens(response.getItensAluguel());
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
    public void refresh() {
        carregarRequisicoes(ultimoStatusRequest);
    }

    @Override
    public void atualizarStatus(ItemAluguelDTO item, StatusItemAluguel status) {
        view.mostrarLoading();
        service.atualizaItemAluguelStatus(item.getId(), new StatusItemAluguelData(status))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        view.esconderLoading();
                        view.mostrarMensagem("Item atualizado");
                        carregarRequisicoes(ultimoStatusRequest);
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.esconderLoading();
                        view.mostrarMensagem(mensagem.join());
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
