package br.edu.ifam.saf.api.data;

import java.util.Arrays;
import java.util.List;

import br.edu.ifam.saf.api.dto.UsuarioDTO;

public class UsuariosResponse {
    private List<UsuarioDTO> usuarios;

    public UsuariosResponse(List<UsuarioDTO> lista) {
        this.usuarios = lista;
    }

    public UsuariosResponse(UsuarioDTO... lista) {
        this.usuarios = Arrays.asList(lista);

    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }
}
