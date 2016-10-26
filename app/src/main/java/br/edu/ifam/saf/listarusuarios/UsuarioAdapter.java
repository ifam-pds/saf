package br.edu.ifam.saf.listarusuarios;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    List<UsuarioDTO> usuarios;

    public UsuarioAdapter(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new ViewHolder(v);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nome_usuario)
        TextView nome;
        @BindView(R.id.cpf_usuario)
        TextView cpf;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindUsuario(UsuarioDTO usuario) {
            nome.setText(usuario.getNome());
            cpf.setText(usuario.getCpf());
        }

    }

}