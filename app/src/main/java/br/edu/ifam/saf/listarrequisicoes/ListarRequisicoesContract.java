package br.edu.ifam.saf.listarrequisicoes;

import java.util.List;

import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.api.dto.UsuarioDTO;

public interface ListarRequisicoesContract {

    interface View{
        void mostrarRequisicoes(List<AluguelDTO> alugueis);

        void mostrarLoading();

        void esconderLoading();

        void mostrarMensagem(String mensagem);
    }

    interface Presenter{

        void destroy();

        void carregarReservas();

    }

}
