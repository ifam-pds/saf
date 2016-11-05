package br.edu.ifam.saf.listarrequisicoes;


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
import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.util.ApiManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListarRequisicoesFragment extends Fragment implements ListarRequisicoesContract.View {

    @BindView(R.id.lista_requisicoes_aluguel)
    RecyclerView rvRequisicoes;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    AluguelAdapter adapter;
    ListarRequisicoesContract.Presenter presenter;

    public ListarRequisicoesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listar_requisicoes_reservas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        rvRequisicoes.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AluguelAdapter(new ArrayList<AluguelDTO>());
        rvRequisicoes.setAdapter(adapter);
        presenter = new ListarRequisicoesPresenter(this, ApiManager.getService());
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.carregarReservas();
            }
        });
        presenter.carregarReservas();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        esconderLoading();
        presenter.destroy();
    }

    @Override
    public void mostrarRequisicoes(List<AluguelDTO> alugueis) {
        adapter.replaceData(alugueis);
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
    public void mostrarMensagemDeErro(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }
}
