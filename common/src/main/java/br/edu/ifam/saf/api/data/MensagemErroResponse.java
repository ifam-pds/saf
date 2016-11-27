package br.edu.ifam.saf.api.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MensagemErroResponse {
    private List<String> mensagens = new ArrayList<>();


    public MensagemErroResponse(String... mensagens) {
        this.mensagens.addAll(Arrays.asList(mensagens));
    }

    public String join() {
        String joinedMensagens = "";
        for (String mensagem : mensagens) {
            joinedMensagens += mensagem + "\n";
        }
        return joinedMensagens;
    }


    public List<String> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }
}
