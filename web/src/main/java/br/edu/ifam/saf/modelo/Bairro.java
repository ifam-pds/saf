package br.edu.ifam.saf.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bairro")
public class Bairro extends EntidadeBase {

    @Column(nullable = false)
    private String nome;

    @ManyToOne(optional = false)
    private Cidade cidade;

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Bairro bairro = (Bairro) o;

        if (nome != null ? !nome.equals(bairro.nome) : bairro.nome != null) return false;
        return cidade != null ? cidade.equals(bairro.cidade) : bairro.cidade == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cidade != null ? cidade.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bairro{" +
                "nome='" + nome + '\'' +
                ", cidade=" + cidade +
                '}';
    }
}
