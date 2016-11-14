package br.edu.ifam.saf.listarcarrinho;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    List<ItemAluguelDTO> itensCarrinho;

    public CarrinhoAdapter(List<ItemAluguelDTO> itensCarrinho) {

        this.itensCarrinho = itensCarrinho;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ItemAluguelDTO itemCarrinho = itensCarrinho.get(position);
        viewHolder.bindCarrinho(itemCarrinho);
    }

    @Override
    public int getItemCount() {
        return itensCarrinho.size();
    }

    public void replaceItens(List<ItemAluguelDTO> itensCarrinho) {

        this.itensCarrinho = itensCarrinho;
        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nome_item)
        TextView nomeItem;

        @BindView(R.id.quantidade_item)
        TextView quantidadeItem;

        @BindView(R.id.valor_item_x_quantidade)
        TextView valorItemXQuantidade;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindCarrinho(ItemAluguelDTO itemCarrinho) {
            nomeItem.setText(itemCarrinho.getItem().getNome());
            quantidadeItem.setText(String.valueOf(itemCarrinho.getQuantidade()));
            valorItemXQuantidade.setText(String.valueOf(itemCarrinho.getValorXQuantidade()));
        }

    }

}
