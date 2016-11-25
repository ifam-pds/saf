package br.edu.ifam.saf.listarcategorias;

import java.util.List;

import br.edu.ifam.saf.api.dto.CategoriaDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

interface ListarCategoriasContract {

    interface View extends LoadingView{

        void mostrarCategorias(List<CategoriaDTO> categotiaDTO);

        void mostrarDialogEntradaCategoria();

    }

    interface Presenter extends BasePresenter{

        void onFabAction();

        void cadastrarCategoria(CategoriaDTO categoriaDTO);

        void carregarCategorias();

    }

}
