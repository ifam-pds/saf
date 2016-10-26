
package br.edu.ifam.saf.api.data;

import java.util.Arrays;
import java.util.List;

import br.edu.ifam.saf.api.dto.CategoriaDTO;

public class CategoriasResponse {
    private List<CategoriaDTO> categorias;

    public CategoriasResponse(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public CategoriasResponse(CategoriaDTO... itens) {
        this.categorias = Arrays.asList(itens);

    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }
}
