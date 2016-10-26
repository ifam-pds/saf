package br.edu.ifam.saf.api.dto;

public class CidadeDTO {
    private Integer id;
    private String nome;
    private String estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CidadeDTO cidadeDTO = (CidadeDTO) o;

        if (id != null ? !id.equals(cidadeDTO.id) : cidadeDTO.id != null) return false;
        if (nome != null ? !nome.equals(cidadeDTO.nome) : cidadeDTO.nome != null) return false;
        return estado != null ? estado.equals(cidadeDTO.estado) : cidadeDTO.estado == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }
}
