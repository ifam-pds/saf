package br.edu.ifam.saf.api.dto;


public interface DTOTransformer<E, D> {
    E toEntity(D dto);

    D toDTO(E entity);
}
