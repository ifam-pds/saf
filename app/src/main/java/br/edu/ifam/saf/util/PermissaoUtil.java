package br.edu.ifam.saf.util;

import android.util.SparseArray;
import android.view.MenuItem;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.enums.Perfil;

public final class PermissaoUtil {

    static SparseArray<Perfil> permissoesMenu;


    static {

        permissoesMenu = new SparseArray<>();
        permissoesMenu.put(R.id.nav_listar_requisicoes, Perfil.FUNCIONARIO);
        permissoesMenu.put(R.id.nav_usuarios, Perfil.ADMINISTRADOR);

    }

    public static boolean temPermissaoMenu(MenuItem menuItem, UsuarioDTO usuario) {

        Perfil permPerfil = permissoesMenu.get(menuItem.getItemId());

        return (permPerfil == null) ||
                (usuario != null && permPerfil.getNivel() <= usuario.getPerfil().getNivel());

    }

}
