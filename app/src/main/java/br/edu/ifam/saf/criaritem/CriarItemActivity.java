package br.edu.ifam.saf.criaritem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.CategoriaDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.util.ApiManager;
import br.edu.ifam.saf.view.FieldView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CriarItemActivity extends AppCompatActivity implements CriarItemContract.View {

    @BindView(R.id.nome_item)
    FieldView nomeItem;

    @BindView(R.id.marca)
    FieldView marca;

    @BindView(R.id.modelo)
    FieldView modelo;

    @BindView(R.id.preco_hore)
    FieldView preco;

    @BindView(R.id.descricao)
    FieldView descricao;

    @BindView(R.id.categoria_spinner)
    Spinner categoriaSpinner;

    ArrayAdapter<CategoriaDTO> categoriasAdapter;
    private CriarItemContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_item);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        categoriasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        categoriaSpinner.setAdapter(categoriasAdapter);

        presenter = new CriarItemPresenter(this, ApiManager.getService());
        presenter.carregarCategorias();
    }

    @OnClick(R.id.salvar_button)
    void salvarClick() {
        presenter.registrar(getItem());
    }

    private ItemDTO getItem() {

        CategoriaDTO categoria = (CategoriaDTO) categoriaSpinner.getSelectedItem();
        return new ItemDTO.Builder()
                .nome(nomeItem.getText())
                .categoria(categoria)
                .marca(marca.getText())
                .modelo(modelo.getText())
                .precoPorHora(Double.valueOf(preco.getText()))
                .descricao(descricao.getText())
                .build();
    }

    @Override
    public void mostrarMensagemItemCriado() {
        Toast.makeText(this, "Item criado com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void mostrarCategorias(List<CategoriaDTO> categorias) {
        categoriasAdapter.clear();
        categoriasAdapter.addAll(categorias);
    }

    @Override
    public void mostrarLoading() {

    }

    @Override
    public void esconderLoading() {

    }

    @Override
    public void mostrarMensagemDeErro(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}