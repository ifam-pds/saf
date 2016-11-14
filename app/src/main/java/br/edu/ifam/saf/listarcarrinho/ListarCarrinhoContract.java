package br.edu.ifam.saf.listarcarrinho;

import java.util.List;

import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

public interface ListarCarrinhoContract {

    interface View extends LoadingView {
        void mostrarCarrinho(List<ItemAluguelDTO> itensCarrinho);

        void mostrarMensagem(String mensagem);
    }

    interface Presenter extends BasePresenter {
        void carregarCarrinho();

    }

}
