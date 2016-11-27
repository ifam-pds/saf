package br.edu.ifam.saf.modelo;

import com.google.common.base.Preconditions;

import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "aluguel")
public class Aluguel extends EntidadeBase {

    @ManyToOne(optional = false)
    private Usuario cliente;

    @ManyToOne
    private Usuario funcionario;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dataHoraRequisicao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "aluguel", orphanRemoval = true)
    private List<ItemAluguel> itens = new ArrayList<>();

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }

    public List<ItemAluguel> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void setItens(List<ItemAluguel> itens) {
        Preconditions.checkNotNull(itens, "lista de itens não deve ser nula");
        this.itens = itens;
        for (ItemAluguel item : itens) {
            item.setAluguel(this);
        }

    }

    public Date getDataHoraRequisicao() {
        return dataHoraRequisicao;
    }

    public void setDataHoraRequisicao(Date dataHoraRequisicao) {
        this.dataHoraRequisicao = dataHoraRequisicao;
    }

    public void adicionarItem(ItemAluguel itemAluguel) {
        Preconditions.checkNotNull(itemAluguel, "itemAluguel não deve ser nulo");
        Preconditions.checkNotNull(itemAluguel.getItem(), "itemAluguel.item não deve ser nulo");

        if (!itens.contains(itemAluguel)) {
            itens.add(itemAluguel);
        }

        if (!this.equals(itemAluguel.getAluguel())) {
            itemAluguel.setAluguel(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluguel)) return false;
        if (!super.equals(o)) return false;

        Aluguel aluguel = (Aluguel) o;

        if (cliente != null ? !cliente.equals(aluguel.cliente) : aluguel.cliente != null)
            return false;
        return funcionario != null ? funcionario.equals(aluguel.funcionario) : aluguel.funcionario == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cliente != null ? cliente.hashCode() : 0);
        result = 31 * result + (funcionario != null ? funcionario.hashCode() : 0);
        return result;
    }
}

