package br.edu.ifam.saf.data;


import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.api.dto.UsuarioDTO;

public interface LocalRepository {

    void adicionarAluguelItem(ItemAluguelDTO itemAluguelDTO);

    AluguelDTO getCarrinho();

    void limpaCarrinho();


    UsuarioDTO getInfoUsuario();

    void salvarInfoUsuario(UsuarioDTO usuario);

    String getApiHost();
}
