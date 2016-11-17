package br.edu.ifam.saf.enums;

public enum Perfil {
    CLIENTE(0), FUNCIONARIO(1), ADMINISTRADOR(2);

    private final int nivel;

    Perfil(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}
