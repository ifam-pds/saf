package br.edu.ifam.saf.api.dto;


import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.edu.ifam.saf.modelo.ItemAluguel;

@ApplicationScoped
public class ItemAluguelTransformer implements DTOTransformer<ItemAluguel, ItemAluguelDTO> {

    @Inject
    ItemTransformer itemTransformer;
    @Inject
    UsuarioTransformer usuarioTransformer;

    @Override
    public ItemAluguel toEntity(ItemAluguelDTO dto) {
        ItemAluguel itemAluguel = new ItemAluguel();

        itemAluguel.setId(dto.getId());
        itemAluguel.setItem(itemTransformer.toEntity(dto.getItem()));
        itemAluguel.setDuracaoEmMinutos(dto.getDuracaoEmMinutos());
        itemAluguel.setStatus(dto.getStatus());
        return itemAluguel;
    }

    @Override
    public ItemAluguelDTO toDTO(ItemAluguel entity) {
        ItemAluguelDTO dto = new ItemAluguelDTO();

        dto.setId(entity.getId());
        UsuarioDTO usuario = usuarioTransformer.toDTO(entity.getAluguel().getCliente());
        usuario.setBairro(null);
        usuario.setToken(null);
        dto.setUsuario(usuario);
        dto.setDataHoraRequisicao(entity.getAluguel().getDataHoraRequisicao());
        dto.setDuracaoEmMinutos(entity.getDuracaoEmMinutos());
        dto.setItem(itemTransformer.toDTO(entity.getItem()));
        dto.setStatus(entity.getStatus());

        return dto;
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
