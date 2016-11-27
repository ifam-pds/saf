package br.edu.ifam.saf.listarrequisicoes;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.enums.StatusItemAluguel;
import br.edu.ifam.saf.util.ApiManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListarRequisicoesFragment extends Fragment implements ListarRequisicoesContract.View, RequisicaoAdapter.StatusActionListener {

    @BindView(R.id.listarRequisicao)
    RecyclerView rvRequisicoes;

    @BindView(R.id.empty_box)
    ImageView box;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    RequisicaoAdapter adapter;
    ListarRequisicoesContract.Presenter presenter;
    private Spinner toolbarSpinner;
    private ArrayAdapter<StatusWrapper> spinnerAdapter;

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
        adapter = new RequisicaoAdapter(this);
        rvRequisicoes.setAdapter(adapter);
        presenter = new ListarRequisicoesPresenter(this, ApiManager.getService());
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        presenter.carregarRequisicoes(StatusItemAluguel.RESERVA_PENDENTE);


        toolbarSpinner = ((Spinner) getActivity().findViewById(R.id.toolbar_spinner));

        final List<StatusWrapper> statuses = StatusWrapper.asList(StatusItemAluguel.values());
        spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, statuses);

        toolbarSpinner.setAdapter(spinnerAdapter);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarSpinner.setVisibility(View.VISIBLE);

        toolbarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StatusWrapper statusWrapper = statuses.get(position);
                presenter.carregarRequisicoes(statusWrapper.status);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        esconderLoading();
        presenter.destroy();
    }

    @Override
    public void mostrarItens(List<ItemAluguelDTO> itens) {
        adapter.replaceData(itens);
        checkItens(itens.size());
    }

    private void checkItens(int itemCount) {
        if (itemCount > 0) {
            box.setVisibility(View.GONE);
            rvRequisicoes.setVisibility(View.VISIBLE);

        } else {
            box.setVisibility(View.VISIBLE);
            rvRequisicoes.setVisibility(View.GONE);
        }
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
    public void mostrarInfoMensagem(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusAction(ItemAluguelDTO item, StatusItemAluguel status) {
        presenter.atualizarStatus(item, status);
    }

    private static class StatusWrapper {
        private final StatusItemAluguel status;

        public StatusWrapper(StatusItemAluguel status) {
            this.status = status;
        }

        public static List<StatusWrapper> asList(StatusItemAluguel[] statuses) {
            List<StatusWrapper> list = new ArrayList<>();

            for (StatusItemAluguel status : statuses) {
                list.add(new StatusWrapper(status));
            }
            return list;
        }

        @Override
        public String toString() {
            return status.getDescricao();
        }
    }
}
