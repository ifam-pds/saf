package br.edu.ifam.saf.enums;

public enum StatusAluguel {
    REQUISICAO_PENDENTE("Pendente"),
    REQUISICAO_EXPIRADA("Expirado"),
    REQUISICAO_APROVADA("Aprovado"),
    REQUISICAO_PARCIALMENTE_APROVADA("Parcialmente Aprovado"),
    REQUISICAO_REPROVADA("Reprovado"),
    REQUISICAO_ENCERRADA("Encerrado");

    private final String descricao;

    StatusAluguel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
