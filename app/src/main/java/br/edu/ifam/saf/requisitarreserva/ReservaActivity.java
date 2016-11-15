package br.edu.ifam.saf.requisitarreserva;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifam.saf.MainApplication;
import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.ItemAluguelDTO;
import br.edu.ifam.saf.api.dto.ItemDTO;
import br.edu.ifam.saf.util.ApiManager;
import br.edu.ifam.saf.view.FieldView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReservaActivity extends AppCompatActivity implements ReservaContract.View {
    public static final String EXTRA_ITEM_ID = "item_id";


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.quantidade_item)
    FieldView quantidadeItem;

    @BindView(R.id.valor_total)
    FieldView valorTotal;

    @BindView(R.id.nome_item)
    TextView nomeItem;

    @BindView(R.id.marca_item)
    TextView marcaItem;


    @BindView(R.id.descricao_item)
    TextView descricaoItem;

    @BindView(R.id.form)
    View formContainer;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ReservaContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reserva);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new ReservaPresenter(this, ApiManager.getService(), MainApplication.getRepository());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemAluguelDTO itemAluguelDTO = new ItemAluguelDTO();
                itemAluguelDTO.setQuantidade(Integer.valueOf(quantidadeItem.getText()));
                presenter.adicionarItem(itemAluguelDTO);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setEnabled(false);
        formContainer.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int itemId = extras.getInt(EXTRA_ITEM_ID);
            presenter.carregarItem(itemId);
        }


        quantidadeItem.addTextChangeListener(new FieldView.TextWatcherAdapter() {

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().trim().isEmpty()) {
                    Integer quantidade = Integer.valueOf(s.toString());

                    presenter.onQuantidadeChanged(quantidade);

                } else {
                    valorTotal.setText("");
                }

            }
        });
        quantidadeItem.setText("1");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }

        return false;
    }

    @Override
    public void mostrarDetalhesItem(ItemDTO item) {
        esconderLoading();

        fab.setEnabled(true);
        formContainer.setVisibility(View.VISIBLE);

        nomeItem.setText(item.getNome());
        marcaItem.setText(item.getMarca() + "(" + item.getModelo() + ")");
        descricaoItem.setText(item.getDescricao());

    }

    @Override
    public void fechar() {
        finish();
    }

    @Override
    public void atualizarTotal(double valor) {
        valorTotal.setText(String.valueOf(valor));
    }

    @Override
    public void mostrarLoading() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void esconderLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void mostrarMensagemDeErro(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
