package br.edu.ifam.saf.api.data;

import java.util.Arrays;
import java.util.List;

import br.edu.ifam.saf.api.dto.AluguelDTO;

public class AluguelResponse {

    private List<AluguelDTO> Aluguel;

    public AluguelResponse(List<AluguelDTO> aluguel) {
        this.Aluguel = aluguel;
    }

    public AluguelResponse(AluguelDTO... aluguel) {
        this.Aluguel = Arrays.asList(aluguel);

    }

    public List<AluguelDTO> getAluguel() {
        return Aluguel;
    }

    public void setAluguel(List<AluguelDTO> aluguel) {
        this.Aluguel =aluguel;
    }
}
