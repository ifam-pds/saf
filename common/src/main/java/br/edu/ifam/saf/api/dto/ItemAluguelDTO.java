package br.edu.ifam.saf.api.dto;


public class ItemAluguelDTO {

    private Integer id;
    private Integer quantidade;
    private AluguelDTO aluguel;
    private ItemDTO item;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public AluguelDTO getAluguel() {
        return aluguel;
    }

    public void setAluguel(AluguelDTO aluguel) {
        this.aluguel = aluguel;

        if (aluguel == null) {
            return;
        }

        if (aluguel.getItens().contains(this)) {
            return;
        }

        aluguel.adicionarItem(this);


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
        if (aluguel != null ? !aluguel.equals(that.aluguel) : that.aluguel != null) return false;
        return item != null ? item.equals(that.item) : that.item == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (quantidade != null ? quantidade.hashCode() : 0);
        result = 31 * result + (aluguel != null ? aluguel.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }
}
