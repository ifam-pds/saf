package br.edu.ifam.saf.api.dto;


import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.edu.ifam.saf.modelo.ItemAluguel;

@ApplicationScoped
public class ItemAluguelTransformer implements DTOTransformer<ItemAluguel, ItemAluguelDTO>{

    @Inject
    ItemTransformer itemTransformer;

    @Inject
    AluguelTransformer aluguelTransformer;

    @Override
    public ItemAluguel toEntity(ItemAluguelDTO dto) {
        ItemAluguel itemAluguel = new ItemAluguel();

        itemAluguel.setId(dto.getId());
        itemAluguel.setItem(itemTransformer.toEntity(dto.getItem()));
        itemAluguel.setQuantidade(dto.getQuantidade());
        itemAluguel.setAluguel(aluguelTransformer.toEntity(dto.getAluguel()));
        return itemAluguel;
    }

    @Override
    public ItemAluguelDTO toDTO(ItemAluguel entity) {
        ItemAluguelDTO itemAluguelDTO = new ItemAluguelDTO();

        itemAluguelDTO.setId(entity.getId());
        itemAluguelDTO.setQuantidade(entity.getQuantidade());
//        itemAluguelDTO.setAluguel(aluguelTransformer.toDTO(entity.getAluguel()));
        itemAluguelDTO.setItem(itemTransformer.toDTO(entity.getItem()));

        return itemAluguelDTO;
    }

    public List<ItemAluguel> toEntityList(List<ItemAluguelDTO> dtos) {
        List<ItemAluguel> itens = new ArrayList<>();
        for (ItemAluguelDTO dto : dtos) {
            itens.add(toEntity(dto));
        }
        return itens;
    }

    public List<ItemAluguelDTO> toDTOList(List<ItemAluguel> itens) {
        List<ItemAluguelDTO> dtos = new ArrayList<>();

        for (ItemAluguel itemAluguel : itens) {
            dtos.add(toDTO(itemAluguel));
        }

        return dtos;
    }
}
