package br.edu.ifam.saf.itens;


import java.util.List;

import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

public interface ItensContract {
    interface View extends LoadingView {

        void mostrarItens(List<ItemDTO> itens);

        void mostrarItem(ItemDTO item);
    }

    interface Presenter extends BasePresenter {

        void carregarListaDeItens();

        void onItemClick(ItemDTO item);

        void atualizar();
    }

}
