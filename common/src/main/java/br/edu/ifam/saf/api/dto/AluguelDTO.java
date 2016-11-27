package br.edu.ifam.saf.api.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class AluguelDTO {
    private Integer id;
    private UsuarioDTO cliente;
    private UsuarioDTO funcionario;
    private Date dataHoraRequisicao;
    private List<ItemAluguelDTO> itens = new ArrayList<>();

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
        for (ItemAluguelDTO itemAluguel : itens) {
            total += itemAluguel.calcularTotal();
        }
        return total;
    }

    public void adicionarItem(ItemAluguelDTO itemAluguelDTO) {
        if (!itens.contains(itemAluguelDTO)) {
            itens.add(itemAluguelDTO);
        }
    }

    public void removerItem(ItemAluguelDTO itemAluguelDTO) {
        itens.remove(itemAluguelDTO);
    }

    public Date getDataHoraRequisicao() {
        return dataHoraRequisicao;
    }

    public void setDataHoraRequisicao(Date dataHoraRequisicao) {
        this.dataHoraRequisicao = dataHoraRequisicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AluguelDTO)) return false;

        AluguelDTO that = (AluguelDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cliente != null ? !cliente.equals(that.cliente) : that.cliente != null) return false;
        if (funcionario != null ? !funcionario.equals(that.funcionario) : that.funcionario != null)
            return false;
        return dataHoraRequisicao != null ? dataHoraRequisicao.equals(that.dataHoraRequisicao) : that.dataHoraRequisicao == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cliente != null ? cliente.hashCode() : 0);
        result = 31 * result + (funcionario != null ? funcionario.hashCode() : 0);
        result = 31 * result + (dataHoraRequisicao != null ? dataHoraRequisicao.hashCode() : 0);
        return result;
    }
}
