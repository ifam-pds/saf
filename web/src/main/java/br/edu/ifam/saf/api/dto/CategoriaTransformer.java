package br.edu.ifam.saf.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.edu.ifam.saf.modelo.Categoria;

@ApplicationScoped
public class CategoriaTransformer implements DTOTransformer<Categoria, CategoriaDTO> {

    @Override
    public Categoria toEntity(CategoriaDTO dto) {
        Categoria entity = new Categoria();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        return entity;
    }

    @Override
    public CategoriaDTO toDTO(Categoria entity) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        return dto;
    }

    public List<Categoria> toEntityList(List<CategoriaDTO> dtos) {
        List<Categoria> categorias = new ArrayList<>();
        for (CategoriaDTO dto : dtos) {
            categorias.add(toEntity(dto));
        }
        return categorias;
    }

    public List<CategoriaDTO> toDTOList(List<Categoria> categorias) {
        List<CategoriaDTO> dtos = new ArrayList<>();

        for (Categoria categoria : categorias) {
            dtos.add(toDTO(categoria));
        }

        return dtos;
    }
}
