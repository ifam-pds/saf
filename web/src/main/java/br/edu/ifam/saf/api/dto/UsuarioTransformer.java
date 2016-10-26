package br.edu.ifam.saf.api.dto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.edu.ifam.saf.modelo.Usuario;


@ApplicationScoped
public class UsuarioTransformer implements DTOTransformer<Usuario, UsuarioDTO> {

    @Inject
    private BairroTransformer bairroTransformer;

    @Override
    public Usuario toEntity(UsuarioDTO dto) {
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
}
