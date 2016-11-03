package br.edu.ifam.saf.api.data;


import java.util.List;

import br.edu.ifam.saf.api.dto.AluguelDTO;

public class AlugueisResponse {
    private List<AluguelDTO> alugueis;
    public AlugueisResponse(List<AluguelDTO> alugueis) {
        this.alugueis = alugueis;
    }

    public List<AluguelDTO> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(List<AluguelDTO> alugueis) {
        this.alugueis = alugueis;
    }

    public static AlugueisResponse from(List<AluguelDTO> dtos) {
        return new AlugueisResponse(dtos);
    }
}
