package br.edu.ifam.saf.itens;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.reserva.ReservaActivity;
import br.edu.ifam.saf.util.ApiManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItensFragment extends Fragment implements ItensContract.View, ItensAdapter.ItemAluguelClickListener {

    ItensAdapter adapter;

    @BindView(R.id.lista_itens)
    RecyclerView lista_itens;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    ItensContract.Presenter presenter;

    public static ItensFragment newInstance() {
        return new ItensFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ItensAdapter(new ArrayList<ItemDTO>(), this);

        lista_itens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        lista_itens.setAdapter(adapter);
        lista_itens.setLayoutManager(layoutManager);

        presenter = new ItensPresenter(this, ApiManager.getService());
        presenter.start();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_itens, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showItens(List<ItemDTO> itens) {
        adapter.replaceData(itens);
    }

    @Override
    public void showItem(ItemDTO item) {
        Intent intent = new Intent(getContext(), ReservaActivity.class);
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle();
//
        startActivity(intent, options);
    }

    @Override
    public void showLoadingIndicator() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoadingIndicator() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideLoadingIndicator();
        presenter.destroy();
    }

    @Override
    public void onClick(ItensAdapter.ViewHolder view, ItemDTO item) {
//        tmpLastView = view;
        presenter.onItemClick(item);
    }

}
