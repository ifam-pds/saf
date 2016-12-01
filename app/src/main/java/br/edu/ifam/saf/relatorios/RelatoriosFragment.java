package br.edu.ifam.saf.relatorios;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.data.ItemRelatorio;
import br.edu.ifam.saf.util.ApiManager;
import br.edu.ifam.saf.util.InteiroFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RelatoriosFragment extends Fragment implements RelatoriosContract.View {

    @BindView(R.id.rv_mais_alugados)
    RecyclerView rvMaisAlugados;

    @BindView(R.id.rv_usuarios_mais_frequentes)
    RecyclerView rvUsuariosMaisFrequentes;

    ItemRelatorioAdapter usuariosMaisFrequentesAdapter;


    @BindView(R.id.report_media_intens_por_aluguel)
    TextView mediaItensPorAluguel;

    ItemRelatorioAdapter maisAlugadosAdapter;

    RelatoriosContract.Presenter presenter;

    public RelatoriosFragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        maisAlugadosAdapter = new ItemRelatorioAdapter();

        rvMaisAlugados.setLayoutManager(new LinearLayoutManager(getContext()));

        rvUsuariosMaisFrequentes.setLayoutManager(new LinearLayoutManager(getContext()));

        rvMaisAlugados.setAdapter(maisAlugadosAdapter);
        usuariosMaisFrequentesAdapter = new ItemRelatorioAdapter();
        rvUsuariosMaisFrequentes.setAdapter(usuariosMaisFrequentesAdapter);

        presenter = new RelatoriosPresenter(this, ApiManager.getService());
        presenter.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_relatorios, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }


    @Override
    public void mostrarItensMaisAlugados(List<ItemRelatorio> itens) {
        maisAlugadosAdapter.replaceData(itens);

    }

    @Override
    public void mostrarMediaDeItensPorAluguel(ItemRelatorio item) {
        mediaItensPorAluguel.setText(InteiroFormatter.format(item.getValor()));

    }

    @Override
    public void mostrarUsuariosMaisFrequentes(List<ItemRelatorio> itens) {
        usuariosMaisFrequentesAdapter.replaceData(itens);
    }
}
