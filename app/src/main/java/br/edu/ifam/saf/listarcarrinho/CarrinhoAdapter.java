package br.edu.ifam.saf.listarcarrinho;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.util.DinheiroFormatter;
import br.edu.ifam.saf.util.TimeFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder> {

    List<ItemAluguelDTO> itensCarrinho;
    private ItemAluguelClickListener listener;

    public CarrinhoAdapter(final ItemAluguelClickListener listener, RecyclerView recyclerView) {

        this.itensCarrinho = new ArrayList<>();

        this.listener = listener;
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int adapterPosition = viewHolder.getAdapterPosition();
                listener.onItemRemove(adapterPosition, itensCarrinho.get(adapterPosition));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


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

    public void replaceItens(List<ItemAluguelDTO> newItens) {

        if (this.itensCarrinho.size() > 0) {
            notifyItemRangeRemoved(0, this.itensCarrinho.size());
        } else {
            this.itensCarrinho = newItens;
            notifyDataSetChanged();

        }

    }

    interface ItemAluguelClickListener {
        void onItemClick(View view, ItemAluguelDTO itemAluguelDTO);

        void onItemRemove(int pos, ItemAluguelDTO itemAluguelDTO);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.nome_item)
        TextView nomeItem;
        @BindView(R.id.duracao)
        TextView duracao;
        @BindView(R.id.valor_item_carrinho)
        TextView valorTotalItem;
        private ItemAluguelDTO itemAluguelDTO;
        private ItemAluguelClickListener listener;

        public ViewHolder(View itemView, ItemAluguelClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        public void bindCarrinho(ItemAluguelDTO itemAluguelDTO) {
            this.itemAluguelDTO = itemAluguelDTO;

            nomeItem.setText(itemAluguelDTO.getItem().getNome());

            valorTotalItem.setText(DinheiroFormatter.format(itemAluguelDTO.calcularTotal()));
            duracao.setText(TimeFormatter.format(itemAluguelDTO.getDuracaoEmMinutos()));

        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, itemAluguelDTO);
        }
    }
}
