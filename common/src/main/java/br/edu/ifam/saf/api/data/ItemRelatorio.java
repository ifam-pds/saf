package br.edu.ifam.saf.api.data;

public class ItemRelatorio {
    private String descricao;
    private Double valor;

    public ItemRelatorio(String descricao, Double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public ItemRelatorio() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemRelatorio that = (ItemRelatorio) o;

        if (descricao != null ? !descricao.equals(that.descricao) : that.descricao != null)
            return false;
        return valor != null ? valor.equals(that.valor) : that.valor == null;

    }

    @Override
    public int hashCode() {
        int result = descricao != null ? descricao.hashCode() : 0;
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        return result;
    }
}
