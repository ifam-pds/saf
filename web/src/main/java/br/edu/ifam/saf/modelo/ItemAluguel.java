package br.edu.ifam.saf.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.ifam.saf.enums.StatusItemAluguel;

@Entity
@Table(name = "item_aluguel")
public class ItemAluguel extends EntidadeBase {

    @JoinColumn(name = "aluguel_id")
    @ManyToOne(optional = false)
    private Aluguel aluguel;

    @ManyToOne(optional = false)
    private Item item;

    @Enumerated(EnumType.STRING)
    private StatusItemAluguel status;

    @Column(nullable = false, name = "duracao_minutos")
    private Integer duracaoEmMinutos;

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

    public Integer getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public void setDuracaoEmMinutos(Integer duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public StatusItemAluguel getStatus() {
        return status;
    }

    public void setStatus(StatusItemAluguel status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemAluguel)) return false;
        if (!super.equals(o)) return false;

        ItemAluguel that = (ItemAluguel) o;

        if (aluguel != null ? !aluguel.equals(that.aluguel) : that.aluguel != null) return false;
        if (item != null ? !item.equals(that.item) : that.item != null) return false;
        if (status != that.status) return false;
        return duracaoEmMinutos != null ? duracaoEmMinutos.equals(that.duracaoEmMinutos) : that.duracaoEmMinutos == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (aluguel != null ? aluguel.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (duracaoEmMinutos != null ? duracaoEmMinutos.hashCode() : 0);
        return result;
    }
}
