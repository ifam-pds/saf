package br.edu.ifam.saf.listarusuarios;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.DateFormatter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    List<UsuarioDTO> usuarios;
    private UsuarioClickListener listener;

    public UsuarioAdapter(List<UsuarioDTO> usuarios, UsuarioClickListener listener) {
        this.listener = listener;
        this.usuarios = usuarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new ViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        UsuarioDTO usuario = usuarios.get(position);
        viewHolder.bindUsuario(usuario);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void replaceUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.nome_usuario)
        TextView nome;
        @BindView(R.id.cpf_usuario)
        TextView cpf;
        @BindView(R.id.data_nascimento)
        TextView nascimento;
        private UsuarioClickListener listener;
        private UsuarioDTO usuario;

        public ViewHolder(View itemView, UsuarioClickListener listener) {
            super(itemView);
            this.listener = listener;
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void bindUsuario(UsuarioDTO usuario) {
            this.usuario = usuario;
            nome.setText(usuario.getNome());
            cpf.setText(usuario.getCpf());
            nascimento.setText(DateFormatter.format(usuario.getDataNascimento()));
        }

        @Override
        public void onClick(View v) {
            listener.onUsuarioClick(getAdapterPosition(), usuario);
        }
    }

    interface UsuarioClickListener {
        void onUsuarioClick(int posicao, UsuarioDTO usuarioDTO);
    }

}