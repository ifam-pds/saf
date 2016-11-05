package br.edu.ifam.saf.listarrequisicoes;

import java.util.List;

import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

public interface ListarRequisicoesContract {

    interface View extends LoadingView {
        void mostrarRequisicoes(List<AluguelDTO> alugueis);

        void mostrarMensagem(String mensagem);
    }

    interface Presenter extends BasePresenter {
        void carregarReservas();

    }

}
