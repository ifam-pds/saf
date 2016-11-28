package br.edu.ifam.saf.criaritem;

import java.io.File;
import java.util.List;

import br.edu.ifam.saf.api.dto.CategoriaDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

interface CriarItemContract {

    interface View extends LoadingView{
        void mostrarMensagemItemCriado();

        void mostrarCategorias(List<CategoriaDTO> categorias);
    }

    interface Presenter extends BasePresenter{
        void carregarCategorias();
        void registrar(ItemDTO itemDTO);

        void onImagemSelecionada(File file);
    }

}