package br.edu.ifam.saf.api.data;


public class ArquivoResponse {
    private String nomeArquivo;

    public ArquivoResponse(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public ArquivoResponse() {
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}

