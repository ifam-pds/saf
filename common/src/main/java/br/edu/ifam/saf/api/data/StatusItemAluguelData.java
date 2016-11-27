package br.edu.ifam.saf.api.data;

import br.edu.ifam.saf.enums.StatusItemAluguel;

public class StatusItemAluguelData {
    private StatusItemAluguel status;

    public StatusItemAluguelData(StatusItemAluguel status) {
        this.status = status;
    }

    public StatusItemAluguelData() {
    }

    public StatusItemAluguel getStatus(){
        return status;
    }

    public void setStatus(StatusItemAluguel status){
        this.status = status;
    }
}