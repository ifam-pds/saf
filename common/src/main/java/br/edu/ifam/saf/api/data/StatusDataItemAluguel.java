package br.edu.ifam.saf.api.data;

import br.edu.ifam.saf.enums.StatusItemAluguel;

public class StatusDataItemAluguel {
    private StatusItemAluguel status;

    public StatusDataItemAluguel(StatusItemAluguel status){
        this.status = status;
    }

    public StatusDataItemAluguel(){
    }

    public StatusItemAluguel getStatus(){
        return status;
    }

    public void setStatus(StatusItemAluguel status){
        this.status = status;
    }
}