package br.edu.ifam.saf.api.dto;


import java.util.Date;

import br.edu.ifam.saf.enums.StatusItemAluguel;

public class ItemAluguelDTO {

    private Integer id;
    private Integer duracaoEmMinutos;
    private Date dataHoraRequisicao;
    private ItemDTO item;
    private StatusItemAluguel status;
    private UsuarioDTO usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getDataHoraRequisicao() {
        return dataHoraRequisicao;
    }

    public void setDataHoraRequisicao(Date dataHoraRequisicao) {
        this.dataHoraRequisicao = dataHoraRequisicao;
    }

    public double calcularTotal() {
        return duracaoEmMinutos * item.getPrecoPorMinuto();
    }


    public Integer getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public void setDuracaoEmMinutos(Integer duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public StatusItemAluguel getStatus() {
        return status;
    }

    public void setStatus(StatusItemAluguel status) {
        this.status = status;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemAluguelDTO)) return false;

        ItemAluguelDTO that = (ItemAluguelDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (duracaoEmMinutos != null ? !duracaoEmMinutos.equals(that.duracaoEmMinutos) : that.duracaoEmMinutos != null)
            return false;
        if (item != null ? !item.equals(that.item) : that.item != null) return false;
        return status == that.status;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (duracaoEmMinutos != null ? duracaoEmMinutos.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
