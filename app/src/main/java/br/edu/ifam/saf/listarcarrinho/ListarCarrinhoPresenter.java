package br.edu.ifam.saf.listarcarrinho;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.data.LocalRepository;

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
    public void carregarCarrinho() {
        view.mostrarCarrinho(repository.getCarrinho().getItens());
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
