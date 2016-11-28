package br.edu.ifam.saf.criaritem;

import java.io.File;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.ArquivoResponse;
import br.edu.ifam.saf.api.data.CategoriasResponse;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.util.ApiCallback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CriarItemPresenter implements CriarItemContract.Presenter {

    private CriarItemContract.View view;

    private SAFService service;

    private String nomeImagem;

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
                        view.mostrarInfoMensagem(mensagem.getMensagens().get(0));
                    }

                    @Override
                    public boolean canExecute() {
                        return view != null;
                    }
                });
    }

    @Override
    public void registrar(ItemDTO itemDTO) {
        itemDTO.setImagem(nomeImagem);

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
                        view.mostrarInfoMensagem(mensagem.getMensagens().get(0));
                    }

                    @Override
                    public boolean canExecute() {
                        return view != null;
                    }
                });
    }

    @Override
    public void onImagemSelecionada(File file) {

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("arquivo", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        MultipartBody.Part namePart = MultipartBody.Part.createFormData("nomeArquivo", file.getName());


        service.uploadImagem(namePart, filePart).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<ArquivoResponse>() {
                    @Override
                    public void onSuccess(ArquivoResponse response) {
                        nomeImagem = response.getNomeArquivo();
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.mostrarInfoMensagem("Erro ao enviar imagem");
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