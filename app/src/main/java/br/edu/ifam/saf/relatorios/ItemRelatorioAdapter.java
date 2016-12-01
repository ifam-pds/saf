package br.edu.ifam.saf.relatorios;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.data.ItemRelatorio;
import br.edu.ifam.saf.util.InteiroFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemRelatorioAdapter extends RecyclerView.Adapter<ItemRelatorioAdapter.ViewHolder> {

    private List<ItemRelatorio> itens;

    public ItemRelatorioAdapter() {
        itens = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = ((LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_relatorio, parent, false));
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemRelatorio item = itens.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public void replaceData(List<ItemRelatorio> newItens) {
        this.itens = newItens;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_relatorio_descricao)
        TextView descricao;
        @BindView(R.id.item_relatorio_valor)
        TextView valor;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(ItemRelatorio item) {
            descricao.setText(item.getDescricao());
            valor.setText(InteiroFormatter.format(item.getValor()));
        }

    }

}
