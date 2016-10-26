package br.edu.ifam.saf.api.data;

import java.util.Arrays;
import java.util.List;

import br.edu.ifam.saf.api.dto.BairroDTO;

public class BairrosResponse {
    private List<BairroDTO> bairros;

    public BairrosResponse(List<BairroDTO> bairros) {
        this.bairros = bairros;
    }

    public BairrosResponse(BairroDTO... itens) {
        this.bairros = Arrays.asList(itens);

    }

    public List<BairroDTO> getBairros() {
        return bairros;
    }

    public void setBairros(List<BairroDTO> bairros) {
        this.bairros = bairros;
    }
}
