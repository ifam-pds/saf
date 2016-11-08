package br.edu.ifam.saf.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.ifam.saf.enums.StatusItem;

@Entity
@Table(name = "item")
public class Item extends EntidadeBase {
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, length = 512)
    private String descricao;
    private String marca;
    private String modelo;

    @Column(nullable = false)
    private Double precoPorHora;

    @Enumerated(EnumType.STRING)
    private StatusItem status;

    @ManyToOne
    private Categoria categoria;

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

    public Double getPrecoPorHora() {
        return precoPorHora;
    }

    public void setPrecoPorHora(Double precoPorHora) {
        this.precoPorHora = precoPorHora;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public StatusItem getStatus() {
        return status;
    }

    public void setStatus(StatusItem status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Item item = (Item) o;

        if (nome != null ? !nome.equals(item.nome) : item.nome != null) return false;
        if (descricao != null ? !descricao.equals(item.descricao) : item.descricao != null)
            return false;
        if (marca != null ? !marca.equals(item.marca) : item.marca != null) return false;
        if (modelo != null ? !modelo.equals(item.modelo) : item.modelo != null) return false;
        if (precoPorHora != null ? !precoPorHora.equals(item.precoPorHora) : item.precoPorHora != null)
            return false;
        if (status != item.status) return false;
        return categoria != null ? categoria.equals(item.categoria) : item.categoria == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (descricao != null ? descricao.hashCode() : 0);
        result = 31 * result + (marca != null ? marca.hashCode() : 0);
        result = 31 * result + (modelo != null ? modelo.hashCode() : 0);
        result = 31 * result + (precoPorHora != null ? precoPorHora.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (categoria != null ? categoria.hashCode() : 0);
        return result;
    }
}
