package br.edu.ifam.saf.criaritem;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
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
    public static final int RESULT_PICK_IMAGE = 1;


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

    @BindView(R.id.selecionar_imagem_button)
    Button selecionarImagemButton;

    @BindView(R.id.imagem_item)
    ImageView imagemItem;
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
        selecionarImagemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, RESULT_PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_PICK_IMAGE:
                imagemItem.setVisibility(View.VISIBLE);
                Uri uri = data.getData();
                imagemItem.setImageURI(uri);
                presenter.onImagemSelecionada(new File(getPath(uri)));
                break;
        }

    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
    public void mostrarInfoMensagem(String mensagem) {
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