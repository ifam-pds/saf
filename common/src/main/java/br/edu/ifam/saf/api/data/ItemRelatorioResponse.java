package br.edu.ifam.saf.api.data;

import java.util.Arrays;
import java.util.List;

import br.edu.ifam.saf.api.data.ItemRelatorio;

public class ItemRelatorioResponse {
    private List<ItemRelatorio> itens;

    public ItemRelatorioResponse(List<ItemRelatorio> itens) {
        this.itens = itens;
    }

    public ItemRelatorioResponse() {
    }

    public ItemRelatorioResponse(ItemRelatorio... itens) {
        this.itens = Arrays.asList(itens);
    }


    public List<ItemRelatorio> getItens() {
        return itens;
    }

    public void setItens(List<ItemRelatorio> itens) {
        this.itens = itens;
    }
}
