package br.edu.ifam.saf.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.edu.ifam.saf.modelo.Aluguel;
import br.edu.ifam.saf.modelo.ItemAluguel;
import br.edu.ifam.saf.modelo.Usuario;


public class AluguelTransformer implements DTOTransformer<Aluguel, AluguelDTO> {

    @Inject
    UsuarioTransformer usuarioTransformer;

    @Inject
    ItemAluguelTransformer itemAluguelTransformer;

    @Override
    public Aluguel toEntity(AluguelDTO dto) {
        Aluguel aluguel = new Aluguel();

        for(ItemAluguelDTO itemAluguelDTO : dto.getItens()){
            aluguel.adicionarItem(itemAluguelTransformer.toEntity(itemAluguelDTO));
        }
        aluguel.setId(dto.getId());
        aluguel.setCliente(usuarioTransformer.toEntity(dto.getCliente()));
        aluguel.setFuncionario(usuarioTransformer.toEntity(dto.getFuncionario()));
        aluguel.setStatus(dto.getStatus());
        aluguel.setDataHoraDevolucao(dto.getDataHoraDevolucao());
        aluguel.setDataHoraInicio(dto.getDataHoraInicio());


        return aluguel;
    }

    @Override
    public AluguelDTO toDTO(Aluguel entity) {
        final AluguelDTO aluguelDTO = new AluguelDTO();

        for(ItemAluguel itemAluguelEntity : entity.getItens()){
            aluguelDTO.adicionarItem(itemAluguelTransformer.toDTO(itemAluguelEntity));
        }

        aluguelDTO.setId(entity.getId());
        aluguelDTO.setCliente(usuarioTransformer.toDTO(entity.getCliente()));
        aluguelDTO.setFuncionario(usuarioTransformer.toDTO(entity.getFuncionario()));
        aluguelDTO.setStatus(entity.getStatus());
        aluguelDTO.setDataHoraDevolucao(entity.getDataHoraDevolucao());
        aluguelDTO.setDataHoraInicio(entity.getDataHoraInicio());

        return aluguelDTO;
    }

}
