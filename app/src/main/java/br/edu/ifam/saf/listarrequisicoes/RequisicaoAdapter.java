package br.edu.ifam.saf.listarrequisicoes;

import android.animation.ValueAnimator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.enums.StatusItemAluguel;
import br.edu.ifam.saf.util.DinheiroFormatter;
import br.edu.ifam.saf.util.TimeFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RequisicaoAdapter extends RecyclerView.Adapter<RequisicaoAdapter.ViewHolder> {

    private List<ItemAluguelDTO> dataset;
    private StatusActionListener callback;

    public RequisicaoAdapter(StatusActionListener callback) {
        this.callback = callback;
        this.dataset = new ArrayList<>();
    }

    public void replaceData(List<ItemAluguelDTO> newItens) {
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
        ItemAluguelDTO aluguel = dataset.get(position);
        holder.bind(aluguel);

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    interface StatusActionListener {
        void onStatusAction(ItemAluguelDTO item, StatusItemAluguel status);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CardView view;

        ItemAluguelDTO item;

        @BindView(R.id.nome_item)
        TextView nomeItem;
        @BindView(R.id.aluguel_status)
        TextView statusAluguel;
        @BindView(R.id.nome_usuario)
        TextView nomeUsuario;
        @BindView(R.id.duracao)
        TextView duracao;
        @BindView(R.id.valor_total)
        TextView valorTotal;
        @BindView(R.id.expando)
        ImageView expando;

        @BindView(R.id.action_area)
        LinearLayout actionArea;

        @BindView(R.id.marcar_entregue_button)
        Button marcarEntregueButton;

        @BindView(R.id.marcar_devolvido_button)
        Button marcarDevolvidoButton;

        @BindView(R.id.marcar_invalido_button)
        Button marcarInvalidoButton;


        private int originalHeight = 0;
        private boolean isExpanded;

        private StatusActionListener listener;

        public ViewHolder(CardView view, StatusActionListener listener) {

            super(view);
            this.listener = listener;
            ButterKnife.bind(this, view);
            this.view = view;
            view.setOnClickListener(this);
            marcarDevolvidoButton.setOnClickListener(this);
            marcarEntregueButton.setOnClickListener(this);
            marcarInvalidoButton.setOnClickListener(this);
        }

        public void bind(ItemAluguelDTO item) {
            this.item = item;

            statusAluguel.setText(item.getStatus().getDescricao());
            nomeItem.setText(item.getItem().getNome());
            nomeUsuario.setText(item.getUsuario().getNome());
            duracao.setText(TimeFormatter.format(item.getDuracaoEmMinutos()));
            valorTotal.setText(DinheiroFormatter.format(item.calcularTotal()));
        }

        @Override
        public void onClick(View v) {
            toggleVisibility();
            switch (v.getId()) {
                case R.id.marcar_entregue_button:
                    listener.onStatusAction(item, StatusItemAluguel.RESERVA_APROVADA);
                    break;
                case R.id.marcar_invalido_button:
                    listener.onStatusAction(item, StatusItemAluguel.RESERVA_EXPIRADA);
                    break;
                case R.id.marcar_devolvido_button:
                    listener.onStatusAction(item, StatusItemAluguel.RESERVA_ENCERRADA);
                    break;
            }
        }

        private void toggleVisibility() {
            if (originalHeight == 0) {
                originalHeight = view.getHeight();
            }

            ValueAnimator valueAnimator;
            if (!isExpanded) {
                expando.setImageResource(R.drawable.ic_expand_less_black_24dp);
                actionArea.setVisibility(View.VISIBLE);
                actionArea.setEnabled(true);
                isExpanded = true;
                valueAnimator = ValueAnimator.ofInt(originalHeight, originalHeight + actionArea.getHeight());
            } else {
                expando.setImageResource(R.drawable.ic_expand_more_black_24dp);
                isExpanded = false;
                valueAnimator = ValueAnimator.ofInt(originalHeight + actionArea.getHeight(), originalHeight);

                Animation a = new AlphaAnimation(1.00f, 0.00f); // Fade out

                a.setDuration(200);
                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        actionArea.setVisibility(View.INVISIBLE);
                        actionArea.setEnabled(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                // Set the animation on the custom view
                actionArea.startAnimation(a);
            }
            valueAnimator.setDuration(200);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    view.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                    view.requestLayout();
                }
            });
            valueAnimator.start();
        }

    }
}
