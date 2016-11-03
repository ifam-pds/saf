package br.edu.ifam.saf.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_aluguel")
public class Item_Aluguel extends EntidadeBase {

    @Column(nullable = false)
    private Integer quantidade;

    @JoinColumn(name = "aluguel_id")
    @ManyToOne(optional = false)
    private Aluguel aluguel;

    @ManyToOne(optional = false)
    private Item item;


    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Aluguel getAluguel() {
        return aluguel;
    }

    public void setAluguel(Aluguel aluguel) {
        this.aluguel = aluguel;

        if (aluguel == null) {
            return;
        }

        if (aluguel.getItens().contains(this)) {
            return;
        }

        aluguel.adicionarItem(this);


    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Item_Aluguel that = (Item_Aluguel) o;

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
