package br.edu.ifam.saf.enums;

public enum StatusItemAluguel {
    RESERVA_PENDENTE("Pendente"),
    RESERVA_EXPIRADA("Expirado"),
    RESERVA_APROVADA("Aprovado"),
    RESERVA_REPROVADA("Reprovado"),
    RESERVA_ENCERRADA("Encerrado");

    private final String descricao;

    StatusItemAluguel(String descricao){
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
