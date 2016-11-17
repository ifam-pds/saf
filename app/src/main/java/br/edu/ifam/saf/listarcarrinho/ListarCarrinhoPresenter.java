package br.edu.ifam.saf.listarcarrinho;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.data.LocalRepository;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListarCarrinhoPresenter implements ListarCarrinhoContract.Presenter {

    ListarCarrinhoContract.View view;
    private SAFService service;
    private LocalRepository repository;

    public ListarCarrinhoPresenter(ListarCarrinhoContract.View view, SAFService service, LocalRepository repository) {

        this.view = view;
        this.service = service;
        this.repository = repository;
    }


    @Override
    public void realizarCheckout() {
        service.cadastrarAluguel(repository.getCarrinho())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        view.esconderLoading();
                        view.mostrarMensagem("Pedido enviado com sucesso");
                        repository.limpaCarrinho();
                        view.mostrarCarrinho(repository.getCarrinho().getItens());
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.esconderLoading();
                        view.mostrarMensagem(mensagem.getMensagens().get(0));
                    }

                    @Override
                    public boolean canExecute() {
                        return view != null;
                    }
                })
        ;

    }

    @Override
    public void carregarCarrinho() {
        view.mostrarCarrinho(repository.getCarrinho().getItens());
        view.atualizarTotal(repository.getCarrinho().calcularValorTotal());
    }


    @Override
    public void removerItem(ItemAluguelDTO itemAluguelDTO) {
        repository.removerAluguelItem(itemAluguelDTO);
    }

    @Override
    public void destroy() {
        view = null;
    }
}
