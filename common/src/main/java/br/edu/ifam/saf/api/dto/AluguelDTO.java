package br.edu.ifam.saf.api.dto;

//import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.edu.ifam.saf.enums.StatusAluguel;


public class AluguelDTO {
    private Integer id;
    private UsuarioDTO cliente;
    private UsuarioDTO funcionario;
    private List<ItemAluguelDTO> itens = new ArrayList<>();

    private Date dataHoraInicio;
    private Date dataHoraDevolucao;

    private StatusAluguel status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioDTO getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioDTO cliente) {
        this.cliente = cliente;
    }

    public UsuarioDTO getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(UsuarioDTO funcionario) {
        this.funcionario = funcionario;
    }

    public List<ItemAluguelDTO> getItens() {
        return Collections.unmodifiableList(itens);
    }

    /*
        esse método é a mesma coisa do metodo "adicionarItem"??
     */
    public void setItens(List<ItemAluguelDTO> itens) {
        //Preconditions.checkNotNull(itens, "lista de itens não deve ser nula");
        this.itens = itens;
        for (ItemAluguelDTO item : itens) {
            item.setAluguel(this);
        }
    }

    public Double getValorTotal() {
        double total = 0.0;
        long duracaoEmMinutos = (dataHoraDevolucao.getTime() - dataHoraInicio.getTime()) / 1000 / 60;

        for (ItemAluguelDTO itemAluguel : itens) {
            total += itemAluguel.getItem().getPrecoPorHora() / 60 * duracaoEmMinutos;
        }
        return total;


    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraDevolucao() {
        return dataHoraDevolucao;
    }

    public void setDataHoraDevolucao(Date dataHoraDevolucao) {
        this.dataHoraDevolucao = dataHoraDevolucao;
    }

    public void adicionarItem(ItemAluguelDTO itemAluguel) {
        //Preconditions.checkNotNull(itemAluguel, "itemAluguel não deve ser nulo");
        //Preconditions.checkNotNull(itemAluguel.getItem(), "itemAluguel.item não deve ser nulo");

        if (!itens.contains(itemAluguel)) {
            itens.add(itemAluguel);
        }

        if (!this.equals(itemAluguel.getAluguel())) {
            itemAluguel.setAluguel(this);
        }
    }


    public StatusAluguel getStatus() {
        return status;
    }

    public void setStatus(StatusAluguel status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AluguelDTO aluguel = (AluguelDTO) o;

        if (cliente != null ? !cliente.equals(aluguel.cliente) : aluguel.cliente != null)
            return false;
        if (funcionario != null ? !funcionario.equals(aluguel.funcionario) : aluguel.funcionario != null)
            return false;
        if (dataHoraInicio != null ? !dataHoraInicio.equals(aluguel.dataHoraInicio) : aluguel.dataHoraInicio != null)
            return false;
        if (dataHoraDevolucao != null ? !dataHoraDevolucao.equals(aluguel.dataHoraDevolucao) : aluguel.dataHoraDevolucao != null)
            return false;
        return status == aluguel.status;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cliente != null ? cliente.hashCode() : 0);
        result = 31 * result + (funcionario != null ? funcionario.hashCode() : 0);
        result = 31 * result + (dataHoraInicio != null ? dataHoraInicio.hashCode() : 0);
        result = 31 * result + (dataHoraDevolucao != null ? dataHoraDevolucao.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}