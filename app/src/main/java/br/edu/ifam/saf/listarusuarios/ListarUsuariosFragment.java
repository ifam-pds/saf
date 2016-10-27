package br.edu.ifam.saf.listarusuarios;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListarUsuariosFragment extends Fragment implements ListarUsuariosContract.View {

    @BindView(R.id.listarUsuario)
    RecyclerView rvUsuarios;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    UsuarioAdapter adapter;
    ListarUsuariosContract.Presenter presenter;

    public ListarUsuariosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listar_usuarios, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        rvUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UsuarioAdapter(new ArrayList<UsuarioDTO>());
        rvUsuarios.setAdapter(adapter);
        presenter = new ListarUsuariosPresenter(this, ApiManager.getService());
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.carregarUsuarios();
            }
        });
        presenter.carregarUsuarios();

    }

    @Override
    public void mostrarUsuarios(List<UsuarioDTO> usuarios) {
        adapter.replaceUsuarios(usuarios);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
    }

    @Override
    public void mostrarLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void esconderLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }
}
