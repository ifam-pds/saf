package br.edu.ifam.saf.listarcarrinho;

import java.util.List;

import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;

public interface ListarCarrinhoContract {

    interface View{
        void mostrarCarrinho(List<ItemDTO> itensCarrinho);

        void mostrarLoading();

        void esconderLoading();

        void mostrarMensagem(String mensagem);
    }

    interface Presenter{

        void destroy();

        void carregarCarrinho();

    }

}
