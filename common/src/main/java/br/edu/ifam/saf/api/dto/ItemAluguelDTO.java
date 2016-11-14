package br.edu.ifam.saf.api.dto;


public class ItemAluguelDTO {

    private Integer id;
    private Integer quantidade;
    private ItemDTO item;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValorXQuantidade() {
        return quantidade * item.getPrecoPorHora();
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ItemAluguelDTO that = (ItemAluguelDTO) o;

        if (quantidade != null ? !quantidade.equals(that.quantidade) : that.quantidade != null)
            return false;
        return item != null ? item.equals(that.item) : that.item == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (quantidade != null ? quantidade.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }
}
