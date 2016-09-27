package br.edu.ifam.saf.data;

public class Item {
    private String nome;
    private Double precoPorHora;

    public Item() {
    }

    public Item(String nome, Double precoPorHora) {
        this.nome = nome;
        this.precoPorHora = precoPorHora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoPorHora() {
        return precoPorHora;
    }

    public void setPrecoPorHora(Double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }

}
