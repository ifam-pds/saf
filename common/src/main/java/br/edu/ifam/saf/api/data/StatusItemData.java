package br.edu.ifam.saf.api.data;

import br.edu.ifam.saf.enums.StatusItem;

public class StatusItemData {
    private StatusItem status;

    public StatusItemData(StatusItem status) {
        this.status = status;
    }

    public StatusItemData() {
    }

    public StatusItem getStatus(){
        return status;
    }

    public void setStatus(StatusItem status){
        this.status = status;
    }
}
