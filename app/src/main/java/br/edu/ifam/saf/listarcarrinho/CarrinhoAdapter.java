package br.edu.ifam.saf.listarcarrinho;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    List<ItemAluguelDTO> itensCarrinho;
    private ItemAluguelClickListener listener;

    public CarrinhoAdapter(ItemAluguelClickListener listener) {

        this.itensCarrinho = new ArrayList<>();

        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(v, listener);
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

    interface ItemAluguelClickListener {
        void onItemClick(View view, ItemAluguelDTO itemAluguelDTO);

        void onItemRemove(int pos, ItemAluguelDTO itemAluguelDTO);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.nome_item)
        TextView nomeItem;
        @BindView(R.id.quantidade_item)
        TextView quantidadeItem;
        @BindView(R.id.valor_item_x_quantidade)
        TextView valorItemXQuantidade;
        @BindView(R.id.botao_remover)
        Button botaoRemover;
        private ItemAluguelDTO itemAluguelDTO;
        private ItemAluguelClickListener listener;

        public ViewHolder(View itemView, ItemAluguelClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            botaoRemover.setOnClickListener(this);
            this.listener = listener;
        }

        public void bindCarrinho(ItemAluguelDTO itemAluguelDTO) {
            this.itemAluguelDTO = itemAluguelDTO;

            nomeItem.setText(itemAluguelDTO.getItem().getNome());
            quantidadeItem.setText(String.valueOf(itemAluguelDTO.getQuantidade()));
            valorItemXQuantidade.setText(String.valueOf(itemAluguelDTO.getValorXQuantidade()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.botao_remover:
                    listener.onItemRemove(getAdapterPosition(), itemAluguelDTO);
                    break;
                default:
                    listener.onItemClick(v, itemAluguelDTO);
                    break;
            }
        }
    }


}
