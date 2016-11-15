package br.edu.ifam.saf.requisitarreserva;


import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.data.LocalRepository;
import br.edu.ifam.saf.util.ApiManager;
import retrofit2.adapter.rxjava.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ReservaPresenter implements ReservaContract.Presenter {

    private ReservaContract.View view;
    private SAFService service;
    private LocalRepository repository;

    private ItemDTO item = null;

    public ReservaPresenter(ReservaContract.View view, SAFService service, LocalRepository repository) {
        this.view = view;
        this.service = service;
        this.repository = repository;
    }

    @Override
    public void carregarItem(long itemId) {
        service.consultarItem(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<ItemDTO>>() {
                    @Override
                    public void call(Result<ItemDTO> result) {
                        if (view != null) {
                            view.esconderLoading();
                            if (result.isError()) {
                                result.error().printStackTrace();
                                view.mostrarMensagemDeErro("Erro de conexão");
                            } else if (result.response().isSuccessful()) {
                                item = result.response().body();
                                view.mostrarDetalhesItem(item);
                                onQuantidadeChanged(1);
                            } else {
                                MensagemErroResponse mensagem = ApiManager.parseErro(result.response());
                                view.mostrarMensagemDeErro(mensagem.getMensagens().get(0));
                            }
                        }

                    }
                });

    }

    @Override
    public void onQuantidadeChanged(int quantidade) {
        if (item != null) {
            view.atualizarTotal(item.getPrecoPorHora() * quantidade);
        }

    }

    @Override
    public void adicionarItem(ItemAluguelDTO itemAluguel) {
        itemAluguel.setItem(item);
        repository.adicionarAluguelItem(itemAluguel);
        if (view != null) {
            view.fechar();
        }
    }

    @Override
    public void destroy() {
        view = null;
    }
}
