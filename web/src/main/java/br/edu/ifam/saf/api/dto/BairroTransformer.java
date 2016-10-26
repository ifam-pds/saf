package br.edu.ifam.saf.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.edu.ifam.saf.modelo.Bairro;

@ApplicationScoped
public class BairroTransformer implements DTOTransformer<Bairro, BairroDTO> {

    @Inject
    private CidadeTransformer cidadeTransformer;

    @Override
    public Bairro toEntity(BairroDTO dto) {
        if (dto == null) return null;
        Bairro bairro = new Bairro();
        bairro.setNome(dto.getNome());
        bairro.setId(dto.getId());
        bairro.setCidade(cidadeTransformer.toEntity(dto.getCidade()));
        return bairro;
    }

    @Override
    public BairroDTO toDTO(Bairro entity) {
        if (entity == null) return null;

        BairroDTO dto = new BairroDTO();
        dto.setNome(entity.getNome());
        dto.setId(entity.getId());
        dto.setCidade(cidadeTransformer.toDTO(entity.getCidade()));
        return dto;
    }

    public List<Bairro> toEntityList(List<BairroDTO> dtos) {
        List<Bairro> bairros = new ArrayList<>();
        for (BairroDTO dto : dtos) {
            bairros.add(toEntity(dto));
        }
        return bairros;
    }

    public List<BairroDTO> toDTOList(List<Bairro> bairros) {
        List<BairroDTO> dtos = new ArrayList<>();

        for (Bairro bairro : bairros) {
            dtos.add(toDTO(bairro));
        }

        return dtos;
    }


}
