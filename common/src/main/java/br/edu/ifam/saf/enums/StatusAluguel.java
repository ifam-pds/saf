package br.edu.ifam.saf.enums;

public enum StatusAluguel {
    RESERVA_PENDENTE("Pendente"),
    RESERVA_EXPIRADA("Expirado"),
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    ENCERRADO("Encerrado");

    private final String descricao;

    StatusAluguel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
