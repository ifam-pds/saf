package br.edu.ifam.saf.api.dto;

import javax.enterprise.context.ApplicationScoped;

import br.edu.ifam.saf.modelo.Cidade;

@ApplicationScoped
public class CidadeTransformer implements DTOTransformer<Cidade, CidadeDTO> {


    @Override
    public Cidade toEntity(CidadeDTO dto) {
        Cidade cidade = new Cidade();
        cidade.setNome(dto.getNome());
        cidade.setEstado(dto.getEstado());
        cidade.setId(dto.getId());
        return cidade;
    }

    @Override
    public CidadeDTO toDTO(Cidade entity) {
        if (entity == null) return null;
        CidadeDTO dto = new CidadeDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEstado(entity.getEstado());
        return dto;
    }
}
