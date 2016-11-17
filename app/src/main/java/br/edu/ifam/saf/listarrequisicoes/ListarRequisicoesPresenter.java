package br.edu.ifam.saf.listarrequisicoes;


import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.AlugueisResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.enums.StatusAluguel;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListarRequisicoesPresenter implements ListarRequisicoesContract.Presenter {


    private SAFService service;
    private ListarRequisicoesContract.View view;

    public ListarRequisicoesPresenter(ListarRequisicoesContract.View view, SAFService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void carregarReservas(StatusAluguel statusAluguel) {
        service.alugueis(statusAluguel).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<AlugueisResponse>() {
                    @Override
                    public void onSuccess(AlugueisResponse response) {
                        view.esconderLoading();
                        view.mostrarRequisicoes(response.getAlugueis());
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.esconderLoading();
                        view.mostrarMensagemDeErro(mensagem.getMensagens().get(0));

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
