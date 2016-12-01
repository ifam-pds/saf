package br.edu.ifam.saf.enums;

public enum StatusItem {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private String descricao;

    StatusItem(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
