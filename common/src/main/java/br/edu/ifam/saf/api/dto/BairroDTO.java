package br.edu.ifam.saf.api.dto;

public class BairroDTO {
    private Integer id;
    private String nome;
    private CidadeDTO cidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CidadeDTO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDTO cidade) {
        this.cidade = cidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BairroDTO bairroDTO = (BairroDTO) o;

        if (id != null ? !id.equals(bairroDTO.id) : bairroDTO.id != null) return false;
        if (nome != null ? !nome.equals(bairroDTO.nome) : bairroDTO.nome != null) return false;
        return cidade != null ? cidade.equals(bairroDTO.cidade) : bairroDTO.cidade == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cidade != null ? cidade.hashCode() : 0);
        return result;
    }
}
