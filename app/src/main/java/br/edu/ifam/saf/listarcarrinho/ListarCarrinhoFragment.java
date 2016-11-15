package br.edu.ifam.saf.listarcarrinho;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifam.saf.MainApplication;
import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.util.ApiManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListarCarrinhoFragment extends Fragment implements ListarCarrinhoContract.View, CarrinhoAdapter.ItemAluguelClickListener {

    @BindView(R.id.listarCarrinho)
    RecyclerView rvCarrinho;

    @BindView(R.id.broken_cart)
    ImageView brokenCart;

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
        adapter = new CarrinhoAdapter(this);
        rvCarrinho.setAdapter(adapter);
        presenter = new ListarCarrinhoPresenter(this, ApiManager.getService(), MainApplication.getRepository());
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
        checkCarrinho(itensCarrinho.size());


    }

    private void checkCarrinho(int itemCount) {
        if (itemCount > 0) {
            brokenCart.setVisibility(View.GONE);
            rvCarrinho.setVisibility(View.VISIBLE);

        } else {
            brokenCart.setVisibility(View.VISIBLE);
            rvCarrinho.setVisibility(View.GONE);
        }
    }

    @Override
    public void mostrarMensagem(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, ItemAluguelDTO itemAluguelDTO) {
        //pass

    }

    @Override
    public void onItemRemove(int position, ItemAluguelDTO itemAluguelDTO) {
        presenter.removerItem(itemAluguelDTO);
        adapter.notifyItemRemoved(position);
        checkCarrinho(adapter.getItemCount());


    }
}
