package br.edu.ifam.saf.criaritem;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.CategoriasResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CriarItemPresenter implements CriarItemContract.Presenter {

    private CriarItemContract.View view;

    private SAFService service;

    public CriarItemPresenter(CriarItemContract.View view, SAFService service) {

        this.service = service;
        this.view = view;

    }

    @Override
    public void carregarCategorias() {
        service.listarCategorias()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<CategoriasResponse>() {
                    @Override
                    public void onSuccess(CategoriasResponse response) {
                        view.mostrarCategorias(response.getCategorias());
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.mostrarMensagemDeErro(mensagem.getMensagens().get(0));
                    }

                    @Override
                    public boolean canExecute() {
                        return view != null;
                    }
                });


    }

    @Override
    public void registrar(ItemDTO itemDTO) {
        service.registrarItem(itemDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Void>() {
                    @Override
                    public void onSuccess(Void entity) {
                        view.mostrarMensagemItemCriado();
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.mostrarMensagemDeErro(mensagem.getMensagens().get(0));
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