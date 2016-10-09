package br.edu.ifam.saf.itens;


import java.util.List;

import br.edu.ifam.saf.api.dto.ItemDTO;

public interface ItensContract {
    interface View {
        void showItens(List<ItemDTO> itens);

        void showItem(ItemDTO item);
    }

    interface Presenter {
        void start();

        void destroy();

        void onItemClick(ItemDTO item);
    }

    interface Interactor {

    }
}
