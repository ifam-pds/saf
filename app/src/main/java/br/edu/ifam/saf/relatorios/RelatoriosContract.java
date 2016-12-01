package br.edu.ifam.saf.relatorios;

import java.util.List;

import br.edu.ifam.saf.api.data.ItemRelatorio;

public class RelatoriosContract {
    public interface View {
        void mostrarItensMaisAlugados(List<ItemRelatorio> itens);

        void mostrarMediaDeItensPorAluguel(ItemRelatorio item);

        void mostrarUsuariosMaisFrequentes(List<ItemRelatorio> itens);
    }

    public interface Presenter {
        void start();

        void destroy();
    }
}
