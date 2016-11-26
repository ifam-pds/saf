package br.edu.ifam.saf.itensadmin;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.util.DinheiroFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItensAdminAdapter extends RecyclerView.Adapter<ItensAdminAdapter.ViewHolder> {

    List<ItemDTO> itens;
    private ItemAdminClickListener listener;

    public ItensAdminAdapter(List<ItemDTO> dataset, ItemAdminClickListener listener) {
        this.itens = dataset;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = ((CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false));
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemDTO item = itens.get(position);
        holder.setItemAdmin(item);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public void rapleceData(List<ItemDTO> newItens){
        this.itens = newItens;
        notifyDataSetChanged();
    }

    interface ItemAdminClickListener {
        void onItemClicado(int posicao, ItemDTO itemDTO);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.marca_item)
        TextView marcaItem;
        @BindView(R.id.categoria_item)
        TextView categoriaItem;
        @BindView(R.id.nome_item)
        TextView nomeItem;
        @BindView(R.id.modelo_item)
        TextView modeloItem;
        @BindView(R.id.preco_item)
        TextView precoItem;
        @BindView(R.id.status_item)
        TextView statusItem;
        @BindView(R.id.descricao_item)
        TextView descricaoItem;
        private ItemAdminClickListener listener;
        private ItemDTO item;

        public ViewHolder(CardView view, ItemAdminClickListener listener){
            super(view);
            this.listener = listener;
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @SuppressLint("DefaultLocale")
        public void setItemAdmin(ItemDTO item){
            this.item = item;
            nomeItem.setText(item.getNome());
            marcaItem.setText(item.getMarca());
            categoriaItem.setText(item.getCategoria().toString());
            modeloItem.setText(item.getModelo());
            precoItem.setText(DinheiroFormatter.format(item.getPrecoPorHora()));
            statusItem.setText("Ativo");
            descricaoItem.setText(item.getDescricao());
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicado(getAdapterPosition(), item);
        }
    }

}
