package br.edu.ifam.saf.editaritem;

import java.util.Arrays;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.CategoriasResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.enums.StatusItem;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditarItemPresenter implements  EditarItemContract.Presenter {

    private EditarItemContract.View view;

    private SAFService service;

    private Integer itemId;

    public EditarItemPresenter(EditarItemContract.View view, SAFService service, Integer itemId) {
        this.view = view;
        this.service = service;
        this.itemId = itemId;
    }

    @Override
    public void alterar(ItemDTO itemDTO) {
        if (itemDTO.getNome() == null) {
            view.mostrarInfoMensagem("Nome de usuário inválido.");
        }

        service.atualizarItem(itemId, itemDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        view.mostrarMensagemItemAlterado();
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
                        view.mostrarCategorias(response.getCategorias());
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
    public void carregarStatus() {
        view.mostrarStatusItem(Arrays.asList(StatusItem.values()));
    }

    @Override
    public void carregarItem() {
        service.consultarItem(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<ItemDTO>() {
                    @Override
                    public void onSuccess(ItemDTO itemDTO) {
                        view.mostrarItem(itemDTO);
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
    public void destroy() {
        view = null;
    }
}
