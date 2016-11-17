package br.edu.ifam.saf.requisitarreserva;


import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.data.LocalRepository;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReservaPresenter implements ReservaContract.Presenter {

    private ReservaContract.View view;
    private SAFService service;
    private LocalRepository repository;

    private ItemAluguelDTO itemAluguel;

    public ReservaPresenter(ReservaContract.View view, SAFService service, LocalRepository repository) {
        this.view = view;
        this.service = service;
        this.repository = repository;
    }


    @Override
    public void carregarItem(int itemId) {

        // verifica se o item já existe no carrinho
        for (ItemAluguelDTO itemAluguelDTO : repository.getCarrinho().getItens()) {
            if (itemAluguelDTO.getItem().getId().equals(itemId)) {
                itemAluguel = itemAluguelDTO;
                view.mostrarDetalhesItem(itemAluguel);
                return;
            }
        }


        service.consultarItem(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<ItemDTO>() {
                    @Override
                    public void onSuccess(ItemDTO item) {
                        view.esconderLoading();
                        itemAluguel = new ItemAluguelDTO();
                        itemAluguel.setQuantidade(1);
                        itemAluguel.setItem(item);

                        view.mostrarDetalhesItem(itemAluguel);

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
                });

    }

    @Override
    public void onQuantidadeChanged(int quantidade) {
        if (itemAluguel != null) {
            view.atualizarTotal(itemAluguel.getItem().getPrecoPorHora() * quantidade);
        }

    }

    @Override
    public void salvarReserva(int quantidade) {

        itemAluguel.setQuantidade(quantidade);
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
