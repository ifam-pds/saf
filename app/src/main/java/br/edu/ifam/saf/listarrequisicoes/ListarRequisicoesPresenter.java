package br.edu.ifam.saf.listarrequisicoes;


import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.AlugueisResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.enums.StatusAluguel;
import br.edu.ifam.saf.util.ApiManager;
import retrofit2.adapter.rxjava.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
                .subscribe(new Action1<Result<AlugueisResponse>>() {
                    @Override
                    public void call(Result<AlugueisResponse> result) {
                        if (view != null) {
                            view.esconderLoading();
                            if (result.isError()) {
                                // IOException
                                result.error().printStackTrace();
                                view.mostrarMensagem("Erro de conexão");
                            } else if (result.response().isSuccessful()) {
                                view.mostrarRequisicoes(result.response().body().getAlugueis());
                            } else {
                                MensagemErroResponse mensagem = ApiManager.parseErro(result.response());
                                view.mostrarMensagem(mensagem.getMensagens().get(0));
                            }
                        }
                    }
                })


        ;


    }

    @Override
    public void destroy() {
        view = null;

    }
}
