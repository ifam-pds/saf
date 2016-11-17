package br.edu.ifam.saf.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import br.edu.ifam.saf.MainApplication;
import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiManager;

public final class LocalRepositoryImpl implements LocalRepository {

    private static LocalRepository instance;

    SharedPreferences sharedPreferences;

    private UsuarioDTO usuarioDTO;
    private AluguelDTO carrinho;


    private LocalRepositoryImpl(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        reloadData();

        carrinho = new AluguelDTO();
    }

    public static LocalRepository getInstance() {
        if (instance == null) {
            instance = new LocalRepositoryImpl(MainApplication.getAppContext());
        }
        return instance;
    }

    private void reloadData() {
        String userData = sharedPreferences.getString("userData", null);

        if (userData != null) {
            usuarioDTO = ApiManager.getGson().fromJson(userData, UsuarioDTO.class);
        } else {
            usuarioDTO = null;
        }
    }

    @Override
    public void adicionarAluguelItem(ItemAluguelDTO itemAluguelDTO) {
        if (!carrinho.getItens().contains(itemAluguelDTO)) {
            carrinho.adicionarItem(itemAluguelDTO);
        }
    }

    @Override
    public void removerAluguelItem(ItemAluguelDTO itemAluguelDTO) {
        carrinho.removerItem(itemAluguelDTO);
    }

    @Override
    public void limpaCarrinho() {
        carrinho = new AluguelDTO();
    }

    @Override
    public AluguelDTO getCarrinho() {
        return carrinho;

    }

    @Override
    public UsuarioDTO getInfoUsuario() {
        if (usuarioDTO == null) {
            reloadData();
        }
        return usuarioDTO;
    }

    @Override
    public void salvarInfoUsuario(UsuarioDTO usuario) {

        String serializedUser = ApiManager.getGson().toJson(usuario);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d(LocalRepositoryImpl.class.getSimpleName(), serializedUser);
        editor.putString("userData", serializedUser);
        editor.apply();
        reloadData();

    }

    @Override
    public String getApiHost() {
        String host = sharedPreferences.getString("api_host", "http://127.0.0.1/");
        String port = sharedPreferences.getString("api_port", "80");
        return String.format("http://%s:%s/api/", host, port);
    }
}
