package br.edu.ifam.saf.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.saf.modelo.Aluguel;
import br.edu.ifam.saf.modelo.ItemAluguel;


public class AluguelTransformer implements DTOTransformer<Aluguel, AluguelDTO> {

    @Inject
    UsuarioTransformer usuarioTransformer;

    @Inject
    ItemAluguelTransformer itemAluguelTransformer;

    @Override
    public Aluguel toEntity(AluguelDTO dto) {
        Aluguel aluguel = new Aluguel();

        for (ItemAluguelDTO itemAluguelDTO : dto.getItens()) {
            aluguel.adicionarItem(itemAluguelTransformer.toEntity(itemAluguelDTO));
        }
        aluguel.setId(dto.getId());
        aluguel.setCliente(usuarioTransformer.toEntity(dto.getCliente()));
        aluguel.setFuncionario(usuarioTransformer.toEntity(dto.getFuncionario()));
        aluguel.setDataHoraRequisicao(dto.getDataHoraRequisicao());
        return aluguel;
    }

    @Override
    public AluguelDTO toDTO(Aluguel entity) {
        final AluguelDTO dto = new AluguelDTO();

        for (ItemAluguel itemAluguelEntity : entity.getItens()) {
            dto.adicionarItem(itemAluguelTransformer.toDTO(itemAluguelEntity));
        }

        dto.setId(entity.getId());
        dto.setCliente(usuarioTransformer.toDTO(entity.getCliente()));
        dto.setFuncionario(usuarioTransformer.toDTO(entity.getFuncionario()));
        dto.setDataHoraRequisicao(entity.getDataHoraRequisicao());

        return dto;
    }

    public List<Aluguel> toEntityList(List<AluguelDTO> dtos) {
        List<Aluguel> itens = new ArrayList<>();
        for (AluguelDTO dto : dtos) {
            itens.add(toEntity(dto));
        }
        return itens;
    }

    public List<AluguelDTO> toDTOList(List<Aluguel> alugueis) {
        List<AluguelDTO> dtos = new ArrayList<>();

        for (Aluguel aluguel : alugueis) {
            dtos.add(toDTO(aluguel));
        }

        return dtos;
    }
}
