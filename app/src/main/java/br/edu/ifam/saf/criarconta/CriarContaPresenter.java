package br.edu.ifam.saf.criarconta;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class CriarContaPresenter implements CriarContaContract.Presenter {


    private CriarContaContract.View view;

    private SAFService service;

    public CriarContaPresenter(CriarContaContract.View view, SAFService service) {
        this.service = service;
        this.view = view;
    }

    @Override
    public void registrar(UsuarioDTO usuarioDTO) {

        if (usuarioDTO.getDataNascimento() == null) {
            view.mostrarMensagemDeErro("Data de nascimento inválida.");
        }

        service.registrarUsuario(usuarioDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Void>() {

                    @Override
                    public void onSuccess(Void entity) {
                        view.mostrarMensagemContaCriada();
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
