package br.edu.ifam.saf.listarrequisicoes;

import java.util.List;

import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.enums.StatusItemAluguel;
import br.edu.ifam.saf.mvp.BasePresenter;
import br.edu.ifam.saf.mvp.LoadingView;

public interface ListarRequisicoesContract {

    interface View extends LoadingView {
        void mostrarItens(List<ItemAluguelDTO> itens);

        void mostrarMensagem(String mensagem);
    }

    interface Presenter extends BasePresenter {
        void carregarRequisicoes(StatusItemAluguel status);

        void refresh();

        void atualizarStatus(ItemAluguelDTO item, StatusItemAluguel status);
    }

}
