package br.edu.ifam.saf.api.dto;

public class ItemDTO {
    private Integer id;
    private String nome;
    private Double precoPorHora;
    private String descricao;
    private String marca;
    private String modelo;
    private CategoriaDTO categoria;

    public ItemDTO(Integer id, String nome, Double precoPorHora, String descricao, String marca, String modelo, CategoriaDTO categoria) {
        this.id = id;
        this.nome = nome;
        this.precoPorHora = precoPorHora;
        this.descricao = descricao;
        this.marca = marca;
        this.modelo = modelo;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    public static class Builder {
        private Integer id;
        private String nome;
        private Double precoPorHora;
        private String descricao;
        private String marca;
        private String modelo;
        private CategoriaDTO categoria;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder precoPorHora(Double precoPorHora) {
            this.precoPorHora = precoPorHora;
            return this;
        }

        public Builder descricao(String descricao){
            this.descricao = descricao;
            return this;
        }

        public Builder marca(String marca){
            this.marca = marca;
            return this;
        }

        public Builder modelo(String modelo){
            this.modelo = modelo;
            return this;
        }

        public Builder categoria(CategoriaDTO categoria){
            this.categoria = categoria;
            return this;
        }

        public ItemDTO build(){
            return new ItemDTO(id,nome,precoPorHora,descricao,marca,modelo,categoria);
        }
    }

}
