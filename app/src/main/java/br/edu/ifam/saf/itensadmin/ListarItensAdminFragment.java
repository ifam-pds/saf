package br.edu.ifam.saf.itensadmin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.criaritem.CriarItemActivity;
import br.edu.ifam.saf.editaritem.EditarItemActivity;
import br.edu.ifam.saf.enums.StatusItem;
import br.edu.ifam.saf.util.ApiManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListarItensAdminFragment extends Fragment implements ItensAdminContract.View, ItensAdminAdapter.ItemAdminClickListener {

    ItensAdminAdapter adapter;

    @BindView(R.id.listarItensAdmin)
    RecyclerView listar_itens_admin;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    ItensAdminContract.Presenter presenter;

    private Spinner toolbarSpinner;
    private ArrayAdapter<StatusItemWrapper> spinnerAdapter;

    public ListarItensAdminFragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        listar_itens_admin.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ItensAdminAdapter(new ArrayList<ItemDTO>(), this);
        listar_itens_admin.setAdapter(adapter);
        presenter = new ListarItensAdminPresenter(this, ApiManager.getService());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                presenter.carregarListaDeItens(StatusItem.ATIVO);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onFabAction();
            }
        });
        presenter.carregarListaDeItens(StatusItem.ATIVO);

        toolbarSpinner = ((Spinner) getActivity().findViewById(R.id.toolbar_spinner));

        final List<StatusItemWrapper> statuses = StatusItemWrapper.asList(StatusItem.values());
        spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, statuses);

        toolbarSpinner.setAdapter(spinnerAdapter);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarSpinner.setVisibility(View.VISIBLE);

        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatusItemWrapper statusItemWrapper = statuses.get(position);
                presenter.carregarListaDeItens(statusItemWrapper.status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listar_itens_admin, container, false);
    }

    @Override
    public void mostrarItens(List<ItemDTO> itens) {
        adapter.rapleceData(itens);
    }

    @Override
    public void mostrarLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void esconderLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void mostrarInfoMensagem(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarTelaEditarItem(Integer itemId) {
        Intent intent = new Intent(getContext(), EditarItemActivity.class);
        intent.putExtra(EditarItemActivity.EXTRA_ITEM_ID, itemId);
        startActivity(intent);
    }

    @Override
    public void mostrarTelaNovoItem() {
        Intent intent = new Intent(getContext(), CriarItemActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicado(int posicao, ItemDTO itemDTO) {
        presenter.itemClicado(itemDTO);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    private static class StatusItemWrapper {
        private final StatusItem status;

        public StatusItemWrapper(StatusItem status){
            this.status = status;
        }

        public static List<StatusItemWrapper> asList(StatusItem[] statuses){
            List<StatusItemWrapper> list = new ArrayList<>();

            for (StatusItem status : statuses){
                list.add(new StatusItemWrapper(status));
            }
            return list;
        }

        @Override
        public String toString() {
            return status.getDescricao();
        }
    }

}
