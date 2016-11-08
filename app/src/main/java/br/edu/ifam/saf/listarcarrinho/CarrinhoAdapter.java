package br.edu.ifam.saf.listarcarrinho;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.AluguelDTO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ViewHolder>{

    List<AluguelDTO> itensCarrinho;

    public CarrinhoAdapter(List<AluguelDTO> itensCarrinho){

        this.itensCarrinho = itensCarrinho;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        AluguelDTO itemCarrinho = itensCarrinho.get(position);
        viewHolder.bindCarrinho(itemCarrinho);
    }

    @Override
    public int getItemCount() {
        return itensCarrinho.size();
    }

    public void replaceUsuarios(List<AluguelDTO> itensCarrinho) {

        this.itensCarrinho = itensCarrinho;
        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nome_usuario)
        TextView nome;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindCarrinho(AluguelDTO itemCarrinho) {
            nome.setText(itemCarrinho.getCliente().getNome());
        }

    }

}
