package br.edu.ifam.saf.listarcategorias;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.CategoriaDTO;
import br.edu.ifam.saf.util.ApiManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListarCategoriasFragment extends Fragment implements ListarCategoriasContract.View{

    @BindView(R.id.listaCategorias)
    ListView listaCategorias;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ListarCategoriasPresenter presenter;

    private ArrayAdapter<CategoriaDTO> categoriaAdapter;

    public ListarCategoriasFragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter = new ListarCategoriasPresenter(this, ApiManager.getService());
        presenter.carregarCategorias();

        categoriaAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        listaCategorias.setAdapter(categoriaAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onFabAction();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_listar_categorias, container, false);

    }

    @Override
    public void mostrarCategorias(List<CategoriaDTO> categorias) {
        categoriaAdapter.clear();
        categoriaAdapter.addAll(categorias);
        categoriaAdapter.notifyDataSetChanged();
    }

    @Override
    public void mostrarDialogEntradaCategoria() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Nova Categoria");

        final EditText input = new EditText(getContext());

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String categoriaNome = input.getText().toString();

                CategoriaDTO categoriaDTO = new CategoriaDTO();
                categoriaDTO.setNome(categoriaNome);
                presenter.cadastrarCategoria(categoriaDTO);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    public void mostrarLoading() {

        listaCategorias.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void esconderLoading() {

        listaCategorias.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void mostrarInfoMensagem(String mensagem) {
        Toast.makeText(getContext(), mensagem, Toast.LENGTH_SHORT).show();
    }
}
