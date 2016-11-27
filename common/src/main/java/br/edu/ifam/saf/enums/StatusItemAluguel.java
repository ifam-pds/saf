package br.edu.ifam.saf.enums;

public enum StatusItemAluguel {
    RESERVA_PENDENTE("Aguardando revisão"),
    RESERVA_EXPIRADA("Inválido"),
    RESERVA_APROVADA("Com o cliente"),
    RESERVA_REPROVADA("Recusado"),
    RESERVA_ENCERRADA("Devolvido");

    private final String descricao;

    StatusItemAluguel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
