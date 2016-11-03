package br.edu.ifam.saf.api.data;

import java.util.Arrays;
import java.util.List;

import br.edu.ifam.saf.api.dto.ItemAluguelDTO;


public class ItensAluguelResponse {

    private List<ItemAluguelDTO> itensAluguel;

    public ItensAluguelResponse(List<ItemAluguelDTO> itensAluguel) {
        this.itensAluguel = itensAluguel;
    }

    public ItensAluguelResponse(ItemAluguelDTO... itensAluguel) {
        this.itensAluguel = Arrays.asList(itensAluguel);

    }

    public List<ItemAluguelDTO> getItensAluguel() {
        return itensAluguel;
    }

    public void setItemsAluguel(List<ItemAluguelDTO> itemsAluguel) {
        this.itensAluguel = itemsAluguel;
    }
}
