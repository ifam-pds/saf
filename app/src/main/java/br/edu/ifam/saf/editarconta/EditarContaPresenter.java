package br.edu.ifam.saf.editarconta;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class EditarContaPresenter implements EditarContaContract.Presenter {

    private EditarContaContract.View view;

    private SAFService service;

    private Integer usuarioId;

    public EditarContaPresenter(EditarContaContract.View view, SAFService service, Integer usuarioId) {
        this.view = view;
        this.service = service;
        this.usuarioId = usuarioId;
    }

    @Override
    public void alterar(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getDataNascimento() == null) {
            view.mostrarMensagemDeErro("Data de nascimento inválida.");
        }

        service.atualizarUsuario(usuarioDTO.getId(), usuarioDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<Void>() {
                    @Override
                    public void onSuccess(Void response) {
                        view.mostrarMensagemContaAlterada();
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

    @Override
    public void carregarPerfil() {

    }

    @Override
    public void carregarUsuario() {

        service.consultarUsuario(usuarioId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<UsuarioDTO>() {
                    @Override
                    public void onSuccess(UsuarioDTO usuarioDTO) {
                        view.mostrarUsuario(usuarioDTO);
                    }

                    @Override
                    public void onError(MensagemErroResponse mensagem) {
                        view.mostrarMensagemDeErro(mensagem.getMensagens().get(0));
                    }

                    @Override
                    public boolean canExecute() {
                        return false;
                    }
                });

    }
}
