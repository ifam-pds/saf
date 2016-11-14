package br.edu.ifam.saf.listarcarrinho;

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
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.data.LocalRepositoryImpl;
import br.edu.ifam.saf.util.ApiManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListarCarrinhoFragment extends Fragment implements ListarCarrinhoContract.View {

    @BindView(R.id.listarCarrinho)
    RecyclerView rvCarrinho;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    CarrinhoAdapter adapter;
    ListarCarrinhoContract.Presenter presenter;

    public ListarCarrinhoFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {
        return inflater.inflate(R.layout.fragment_listar_carrinho, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstaceState) {
        super.onViewCreated(view, savedInstaceState);

        ButterKnife.bind(this, view);
        rvCarrinho.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CarrinhoAdapter(new ArrayList<ItemAluguelDTO>());
        rvCarrinho.setAdapter(adapter);
        presenter = new ListarCarrinhoPresenter(this, ApiManager.getService(), LocalRepositoryImpl.getInstance());
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.carregarCarrinho();
            }
        });
        presenter.carregarCarrinho();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
    }

    @Override
    public void mostrarCarrinho(List<ItemAluguelDTO> itensCarrinho) {
        adapter.replaceItens(itensCarrinho);
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
        mostrarMensagem(mensagem);
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }
}
