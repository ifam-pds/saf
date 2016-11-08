package br.edu.ifam.saf.mvp;

public interface LoadingView {

    void mostrarLoading();

    void esconderLoading();

    void mostrarMensagemDeErro(String mensagem);

}
