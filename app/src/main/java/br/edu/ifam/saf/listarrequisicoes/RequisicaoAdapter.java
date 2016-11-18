package br.edu.ifam.saf.listarrequisicoes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.AluguelDTO;
import br.edu.ifam.saf.util.DateTimeFormatter;
import br.edu.ifam.saf.util.DinheiroFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RequisicaoAdapter extends RecyclerView.Adapter<RequisicaoAdapter.ViewHolder> {

    private List<AluguelDTO> dataset;
    private StatusClickCallback callback;

    public RequisicaoAdapter(StatusClickCallback callback) {
        this.callback = callback;
        this.dataset = new ArrayList<>();
    }

    public void replaceData(List<AluguelDTO> newItens) {
        this.dataset = newItens;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = ((CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requisicao, parent, false));
        return new ViewHolder(v, callback);
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

    interface StatusClickCallback {

        void onAluguelAprovado(AluguelDTO aluguel);

        void onAluguelReprovado(AluguelDTO aluguel);

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CardView cardView;

        AluguelDTO aluguel;

        @BindView(R.id.aluguel_status)
        TextView statusAluguel;
        @BindView(R.id.nome_usuario)
        TextView nomeUsuario;
        @BindView(R.id.numero_itens)
        TextView numeroItens;
        @BindView(R.id.data_requisicao)
        TextView dataRequisicao;
        @BindView(R.id.valor_total)
        TextView valorTotal;
        @BindView(R.id.aprovar_button)
        Button aprovarButton;
        @BindView(R.id.reprovar_button)
        Button reprovarButton;
        private StatusClickCallback callback;

        public ViewHolder(CardView view, StatusClickCallback callback) {
            super(view);
            this.callback = callback;
            ButterKnife.bind(this, view);
            this.cardView = view;
            aprovarButton.setOnClickListener(this);
            reprovarButton.setOnClickListener(this);
        }

        public void bind(AluguelDTO aluguel) {
            this.aluguel = aluguel;
            statusAluguel.setText(aluguel.getStatus().getDescricao());
            nomeUsuario.setText(aluguel.getCliente().getNome());
            numeroItens.setText(String.valueOf(aluguel.calcularNumeroTotalDeItens()));
            dataRequisicao.setText(DateTimeFormatter.format(aluguel.getDataHoraInicio()));
            valorTotal.setText(DinheiroFormatter.format(aluguel.calcularValorTotal()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.aprovar_button : callback.onAluguelAprovado(aluguel);
                    break;
                case R.id.reprovar_button : callback.onAluguelReprovado(aluguel);
                    break;
            }
        }
    }
}
