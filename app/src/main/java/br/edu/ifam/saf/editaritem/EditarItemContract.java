package br.edu.ifam.saf.editaritem;

import java.util.List;

import br.edu.ifam.saf.api.dto.CategoriaDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.enums.StatusItem;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

interface EditarItemContract {
    interface View extends LoadingView {
        void mostrarMensagemItemAlterado();

        void mostrarItem(ItemDTO itemDTO);

        void mostrarCategorias(List<CategoriaDTO> categoriaDTOs);

        void mostrarStatusItem(List<StatusItem> status);
    }

    interface Presenter extends BasePresenter {
        void carregarCategorias();

        void carregarStatus();

        void carregarItem();

        void alterar(ItemDTO itemDTO);
    }
}
