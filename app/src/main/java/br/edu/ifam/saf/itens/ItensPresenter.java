package br.edu.ifam.saf.itens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.edu.ifam.saf.data.Item;

public class ItensPresenter implements ItensContract.Presenter {
    private ItensContract.View view;

    public ItensPresenter(ItensContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        Random random = new Random();
        List<Item> itens = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            itens.add(new Item("Jet Ski " + i, random.nextDouble() * 100));
        }
        if (view != null) {
            view.showItens(itens);
        }

    }

    @Override
    public void destroy() {
        view = null;

    }

    @Override
    public void onItemClick(Item item) {
        view.showItem(item);
    }
}
