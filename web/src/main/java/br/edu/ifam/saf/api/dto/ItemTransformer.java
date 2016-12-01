package br.edu.ifam.saf.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.edu.ifam.saf.modelo.Item;

@ApplicationScoped
public class ItemTransformer implements DTOTransformer<Item, ItemDTO> {

    @Inject
    CategoriaTransformer categoriaTransformer;

    @Override
    public Item toEntity(ItemDTO dto) {
        if (dto == null) {
            return null;
        }
        final Item item = new Item();

        item.setStatus(dto.getStatus());
        item.setId(dto.getId());
        item.setImagem(dto.getImagem());
        item.setNome(dto.getNome());
        item.setPrecoPorHora(dto.getPrecoPorHora());
        item.setDescricao(dto.getDescricao());
        item.setMarca(dto.getMarca());
        item.setModelo(dto.getModelo());
        item.setCategoria(categoriaTransformer.toEntity(dto.getCategoria()));

        return item;
    }

    @Override
    public ItemDTO toDTO(Item entity) {
        if (entity == null) {
            return null;
        }

        return new ItemDTO.Builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .imagem(entity.getImagem())
                .status(entity.getStatus())
                .descricao(entity.getDescricao())
                .precoPorHora(entity.getPrecoPorHora())
                .marca(entity.getMarca())
                .modelo(entity.getModelo())
                .categoria(categoriaTransformer.toDTO(entity.getCategoria()))
                .build();
    }

    public List<Item> toEntityList(List<ItemDTO> dtos) {
        List<Item> itens = new ArrayList<>();
        for (ItemDTO dto : dtos) {
            itens.add(toEntity(dto));
        }
        return itens;
    }

    public List<ItemDTO> toDTOList(List<Item> itens) {
        List<ItemDTO> dtos = new ArrayList<>();

        for (Item item : itens) {
            dtos.add(toDTO(item));
        }

        return dtos;
    }
}
