package br.edu.ifam.saf.api.dto;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.edu.ifam.saf.modelo.Usuario;


@ApplicationScoped
public class UsuarioTransformer implements DTOTransformer<Usuario, UsuarioDTO> {

    @Inject
    private BairroTransformer bairroTransformer;

    @Override
    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Usuario.Builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .token(dto.getToken())
                .cpf(dto.getCpf())
                .nome(dto.getNome())
                .dataNascimento(dto.getDataNascimento())
                .telefone(dto.getTelefone())
                .numeroHabilitacao(dto.getNumeroHabilitacao())
                .perfil(dto.getPerfil())
                .endereco(dto.getEndereco())
                .senha(dto.getSenha())
                .bairro(bairroTransformer.toEntity(dto.getBairro()))
                .build();

    }

    @Override
    public UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) {
            return null;
        }

        return new UsuarioDTO.Builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .token(entity.getToken())
                .cpf(entity.getCpf())
                .nome(entity.getNome())
                .cpf(entity.getCpf())
                .dataNascimento(entity.getDataNascimento())
                .telefone(entity.getTelefone())
                .numeroHabilitacao(entity.getNumeroHabilitacao())
                .perfil(entity.getPerfil())
                .endereco(entity.getEndereco())
                .bairro(bairroTransformer.toDTO(entity.getBairro()))
                .build();
    }

    public List<Usuario> toEntityList(List<UsuarioDTO> dtos) {
        List<Usuario> itens = new ArrayList<>();
        for (UsuarioDTO dto : dtos) {
            itens.add(toEntity(dto));
        }
        return itens;
    }

    public List<UsuarioDTO> toDTOList(List<Usuario> itens) {
        List<UsuarioDTO> dtos = new ArrayList<>();

        for (Usuario usuario : itens) {
            dtos.add(toDTO(usuario));
        }

        return dtos;
    }
}
