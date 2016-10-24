package br.edu.ifam.saf.data;


import br.edu.ifam.saf.api.dto.UsuarioDTO;

public interface LocalRepository {
    UsuarioDTO getUserInfo();

    void saveUserInfo(UsuarioDTO usuario);

}
