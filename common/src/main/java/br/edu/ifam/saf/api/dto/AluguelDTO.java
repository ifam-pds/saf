package br.edu.ifam.saf.api.dto;

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

    public void setItens(List<ItemAluguelDTO> itens) {
        this.itens = itens;
    }

    public Double calcularValorTotal() {
        double total = 0.0;
        if (dataHoraDevolucao == null || dataHoraInicio == null) {
            return 0.0;
        }
        long duracaoEmMinutos = (dataHoraDevolucao.getTime() - dataHoraInicio.getTime()) / 1000 / 60;

        for (ItemAluguelDTO itemAluguel : itens) {
            total += itemAluguel.getItem().getPrecoPorHora() / 60 * duracaoEmMinutos;
        }
        return total;

    }

    public int calcularNumeroTotalDeItens() {
        int totalItens = 0;
        for (ItemAluguelDTO item : itens) {
            totalItens += item.getQuantidade();
        }
        return totalItens;

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

    public void adicionarItem(ItemAluguelDTO itemAluguelDTO) {

        if (!itens.contains(itemAluguelDTO)) {
            itens.add(itemAluguelDTO);
        }
    }

    public void removerItem(ItemAluguelDTO itemAluguelDTO) {
        itens.remove(itemAluguelDTO);
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
