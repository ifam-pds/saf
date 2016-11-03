package br.edu.ifam.saf.listarrequisicoes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.AluguelDTO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AluguelAdapter extends RecyclerView.Adapter<AluguelAdapter.ViewHolder> {

    private List<AluguelDTO> dataset;


    public AluguelAdapter(List<AluguelDTO> dataset) {
        this.dataset = dataset;
    }

    public void replaceData(List<AluguelDTO> newItens) {
        this.dataset = newItens;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = ((CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requisicao, parent, false));
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AluguelDTO aluguel = dataset.get(position);
        holder.bind(aluguel);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;

        AluguelDTO aluguel;

        @BindView(R.id.aluguel_status)
        TextView statusAluguel;
        @BindView(R.id.nome_usuario)
        TextView nomeUsuario;
        @BindView(R.id.numero_itens)
        TextView numeroItens;

        public ViewHolder(CardView view) {
            super(view);
            ButterKnife.bind(this, view);
            this.cardView = view;
        }

        public void bind(AluguelDTO aluguel) {
            this.aluguel = aluguel;
            statusAluguel.setText(String.valueOf(aluguel.getStatus()));
            nomeUsuario.setText(aluguel.getCliente().getNome());
            numeroItens.setText(String.valueOf(aluguel.getItens().size()));
        }

    }
}
