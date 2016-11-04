package br.edu.ifam.saf.api.data;


import br.edu.ifam.saf.enums.StatusAluguel;

public class StatusData {
    private StatusAluguel status;


    public StatusAluguel getStatus() {
        return status;
    }

    public void setStatus(StatusAluguel status) {
        this.status = status;
    }
}
