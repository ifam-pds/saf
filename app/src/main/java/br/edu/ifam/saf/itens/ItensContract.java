package br.edu.ifam.saf.itens;


import java.util.List;

import br.edu.ifam.saf.data.Item;

public interface ItensContract {
    interface View {
        void showItens(List<Item> itens);

        void showItem(Item item);
    }

    interface Presenter {
        void start();

        void destroy();

        void onItemClick(Item item);
    }

    interface Interactor {

    }
}
