package br.edu.ifam.saf.enums;

public enum Perfil {
    CLIENTE(0,"Cliente"), FUNCIONARIO(1,"Funcionário"), ADMINISTRADOR(2,"Administrador");

    private final int nivel;

    private String descricao;

    Perfil(int nivel, String descricao) {
        this.nivel = nivel;
        this.descricao = descricao;
    }

    public int getNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
