package br.edu.ifam.saf.itensadmin;


import java.util.List;

import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

interface ItensAdminContract {
    interface View extends LoadingView {

        void mostrarItens(List<ItemDTO> itens);

        void mostrarTelaEditarItem(Integer itemId);

    }

    interface Presenter extends BasePresenter {

        void carregarListaDeItens();

        void itemClicado(ItemDTO item);

    }

}