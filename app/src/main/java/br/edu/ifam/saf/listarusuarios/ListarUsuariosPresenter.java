package br.edu.ifam.saf.listarusuarios;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.api.data.UsuariosResponse;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListarUsuariosPresenter implements ListarUsuariosContract.Presenter {

    ListarUsuariosContract.View view;
    private SAFService service;

    public ListarUsuariosPresenter(ListarUsuariosContract.View view, SAFService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void carregarUsuarios() {
        service.listarUsuarios().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiCallback<UsuariosResponse>() {
                    @Override
                    public void onSuccess(UsuariosResponse response) {
                        view.esconderLoading();
                        view.mostrarUsuarios(response.getUsuarios());
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
    public void usuarioClicado(UsuarioDTO usuarioDTO) {
        view.mostrarTelaEditarUsuario(usuarioDTO.getId());
    }

}
