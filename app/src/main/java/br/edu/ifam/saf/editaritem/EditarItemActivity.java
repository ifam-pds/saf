package br.edu.ifam.saf.editaritem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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

public class EditarItemActivity extends AppCompatActivity implements EditarItemContract.View{

    public static final String EXTRA_ITEM_ID = "item_id";

    @BindView(R.id.nome)
    FieldView nome;

    @BindView(R.id.categoria_spinner)
    Spinner categoriaSpinner;

    @BindView(R.id.marca)
    FieldView marca;

    @BindView(R.id.modelo)
    FieldView modelo;

    @BindView(R.id.preco_hora)
    FieldView precoHora;

    @BindView(R.id.descricao)
    FieldView descricao;

    @BindView(R.id.status_spinner)
    Spinner statusSpinner;

    ArrayAdapter<CategoriaDTO> categoriasAdapter;
    private EditarItemContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_item);
        ButterKnife.bind(this);

        categoriasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(categoriasAdapter);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int itemId = bundle.getInt(EXTRA_ITEM_ID);
            presenter = new EditarItemPresenter(this, ApiManager.getService(), itemId);
            presenter.carregarCategorias();
            presenter.carregarItem();
            Log.d("editar","Carregar Item" + itemId);
        } else {
            Toast.makeText(this, "Item Inválido!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick(R.id.salvar_button)
    void salvarClick(){
        presenter.alterar(getItem());
    }

    private ItemDTO getItem() {

        CategoriaDTO categoria = (CategoriaDTO) categoriaSpinner.getSelectedItem();
        return new ItemDTO.Builder()
                .nome(nome.getText())
                .categoria(categoria)
                .marca(marca.getText())
                .modelo(modelo.getText())
                .precoPorHora(Double.valueOf(precoHora.getText()))
                .descricao(descricao.getText())
                .build();
    }

    @Override
    public void mostrarMensagemItemAlterado() {
        Toast.makeText(this, "Item alterado com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void mostrarItem(ItemDTO itemDTO) {

        nome.setText(itemDTO.getNome());
        categoriaSpinner.setSelection(0);
        marca.setText(itemDTO.getMarca());
        modelo.setText(itemDTO.getModelo());
        precoHora.setText(itemDTO.getPrecoPorHora().toString());
        descricao.setText(itemDTO.getDescricao());
        Log.d("carregar", "carregado" + nome);

    }

    @Override
    public void mostrarCategorias(List<CategoriaDTO> categoriaDTOs) {
        categoriasAdapter.clear();
        categoriasAdapter.addAll(categoriaDTOs);
        categoriasAdapter.notifyDataSetChanged();
    }

    @Override
    public void mostrarLoading() {

    }

    @Override
    public void esconderLoading() {

    }

    @Override
    public void mostrarInfoMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
