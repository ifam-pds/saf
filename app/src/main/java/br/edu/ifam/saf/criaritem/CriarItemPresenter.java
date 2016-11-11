package br.edu.ifam.saf.criaritem;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.util.ApiManager;
import retrofit2.adapter.rxjava.Result;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CriarItemPresenter implements CriarItemContract.Presenter {

    private CriarItemContract.View view;

    private SAFService service;

    public CriarItemPresenter(CriarItemContract.View view, SAFService service){

        this.service = service;
        this.view = view;

    }

    @Override
    public void registrar(ItemDTO itemDTO) {
        service.registrarItem(itemDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Result<Void>>() {
                    @Override
                    public void call(Result<Void> result) {
                        if (!result.response().isSuccessful()){
                            MensagemErroResponse mensagem = ApiManager.parseErro(result.response());
                            view.mostrarMensagemDeErro(mensagem.getMensagens().get(0));
                        } else {
                            view.mostrarMensagemItemCriado();
                        }
                    }
                });
    }

    @Override
    public void destroy() {
        view = null;
    }
}