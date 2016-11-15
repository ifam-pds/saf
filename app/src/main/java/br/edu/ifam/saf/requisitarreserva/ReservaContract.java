package br.edu.ifam.saf.requisitarreserva;

import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

public class ReservaContract {

    interface View extends LoadingView {
        void mostrarDetalhesItem(ItemDTO item);

        void fechar();

        void atualizarTotal(double valorTotal);
    }

    interface Presenter extends BasePresenter {
        void carregarItem(long itemId);

        void onQuantidadeChanged(int quantidade);

        void adicionarItem(ItemAluguelDTO itemAluguel);

    }

}
