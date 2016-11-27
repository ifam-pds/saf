package br.edu.ifam.saf.itens;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemDTO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItensAdapter extends RecyclerView.Adapter<ItensAdapter.ViewHolder> {

    private List<ItemDTO> dataset;
    private ItemClickListener listener;

    public ItensAdapter(List<ItemDTO> dataset, ItemClickListener listener) {
        this.dataset = dataset;
        this.listener = listener;
    }

    public void replaceData(List<ItemDTO> newItens) {
        this.dataset = newItens;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = ((CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemDTO item = dataset.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    interface ItemClickListener {
        void onClick(ViewHolder view, ItemDTO item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CardView cardView;

        final ItemClickListener listener;

        ItemDTO item;

        @BindView(R.id.nome_item)
        TextView nomeItem;
        @BindView(R.id.preco_item)
        TextView precoItem;
        @BindView(R.id.item_image)
        ImageView imagem;

        public ViewHolder(CardView view, ItemClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            this.cardView = view;
            this.listener = listener;

            view.setOnClickListener(this);
        }

        @SuppressLint("DefaultLocale")
        public void setItem(ItemDTO item) {
            this.item = item;
            nomeItem.setText(item.getNome());
            precoItem.setText(String.format("R$ %.2f/hora", item.getPrecoPorHora()));
        }

        @Override
        public void onClick(View v) {
            listener.onClick(this, item);
        }
    }
}
