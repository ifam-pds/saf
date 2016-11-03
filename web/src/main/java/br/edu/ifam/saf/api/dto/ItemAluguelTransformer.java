package br.edu.ifam.saf.api.dto;


import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.edu.ifam.saf.modelo.Item_Aluguel;

@ApplicationScoped
public class ItemAluguelTransformer implements DTOTransformer<Item_Aluguel, ItemAluguelDTO>{

    @Inject
    ItemTransformer itemTransformer;

    @Inject
    AluguelTransformer aluguelTransformer;

    @Override
    public Item_Aluguel toEntity(ItemAluguelDTO dto) {
        Item_Aluguel itemAluguel = new Item_Aluguel();

        itemAluguel.setId(dto.getId());
        itemAluguel.setItem(itemTransformer.toEntity(dto.getItem()));
        itemAluguel.setQuantidade(dto.getQuantidade());
        itemAluguel.setAluguel(aluguelTransformer.toEntity(dto.getAluguel()));
        return itemAluguel;
    }

    @Override
    public ItemAluguelDTO toDTO(Item_Aluguel entity) {
        ItemAluguelDTO itemAluguelDTO = new ItemAluguelDTO();

        itemAluguelDTO.setId(entity.getId());
        itemAluguelDTO.setQuantidade(entity.getQuantidade());
//        itemAluguelDTO.setAluguel(aluguelTransformer.toDTO(entity.getAluguel()));
        itemAluguelDTO.setItem(itemTransformer.toDTO(entity.getItem()));

        return itemAluguelDTO;
    }

    public List<Item_Aluguel> toEntityList(List<ItemAluguelDTO> dtos) {
        List<Item_Aluguel> itens = new ArrayList<>();
        for (ItemAluguelDTO dto : dtos) {
            itens.add(toEntity(dto));
        }
        return itens;
    }

    public List<ItemAluguelDTO> toDTOList(List<Item_Aluguel> itens) {
        List<ItemAluguelDTO> dtos = new ArrayList<>();

        for (Item_Aluguel itemAluguel : itens) {
            dtos.add(toDTO(itemAluguel));
        }

        return dtos;
    }
}
