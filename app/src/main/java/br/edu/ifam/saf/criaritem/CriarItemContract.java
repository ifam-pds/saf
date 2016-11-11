package br.edu.ifam.saf.criaritem;

import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

interface CriarItemContract {

    interface View extends LoadingView{
        void mostrarMensagemItemCriado();
    }

    interface Presenter extends BasePresenter{
        void registrar(ItemDTO itemDTO);
    }

}