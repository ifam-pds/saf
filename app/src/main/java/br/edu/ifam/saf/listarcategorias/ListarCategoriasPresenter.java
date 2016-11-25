package br.edu.ifam.saf.listarcategorias;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.CategoriasResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.CategoriaDTO;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListarCategoriasPresenter implements ListarCategoriasContract.Presenter {

    ListarCategoriasContract.View view;
    private SAFService service;

    public ListarCategoriasPresenter(ListarCategoriasContract.View view, SAFService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void onFabAction() {
        view.mostrarDialogEntradaCategoria();
    }

    @Override
    public void cadastrarCategoria(CategoriaDTO categoriaDTO) {
        service.cadastrarCategorias(categoriaDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        view.mostrarInfoMensagem("Categoria cadastrada com sucesso!");
                        carregarCategorias();
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.mostrarInfoMensagem(mensagem.getMensagens().get(0));
                    }

                    @Override
                    public boolean canExecute() {
                        return view != null;
                    }
                });
    }

    @Override
    public void carregarCategorias() {
        service.listarCategorias()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<CategoriasResponse>() {
                    @Override
                    public void onSuccess(CategoriasResponse response) {
                        view.esconderLoading();
                        view.mostrarCategorias(response.getCategorias());
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.esconderLoading();
                        view.mostrarInfoMensagem(mensagem.getMensagens().get(0));
                    }

                    @Override
                    public boolean canExecute() {
                        return view != null;
                    }
                });

    }

    @Override
    public void destroy() {
        view = null;
    }

}
