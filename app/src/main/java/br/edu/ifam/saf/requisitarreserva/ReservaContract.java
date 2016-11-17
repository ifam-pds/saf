package br.edu.ifam.saf.requisitarreserva;

import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

public class ReservaContract {

    interface View extends LoadingView {
        void mostrarDetalhesItem(ItemAluguelDTO item);

        void fechar();

        void atualizarTotal(double valorTotal);
    }

    interface Presenter extends BasePresenter {

        void carregarItem(int itemId);

        void onQuantidadeChanged(int quantidade);

        void salvarReserva(int quantidade);

    }

}
