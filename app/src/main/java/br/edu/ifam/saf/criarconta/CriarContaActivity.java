package br.edu.ifam.saf.criarconta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Date;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiManager;
import br.edu.ifam.saf.view.FieldView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CriarContaActivity extends AppCompatActivity implements CriarContaContract.View {

    @BindView(R.id.nome)
    FieldView nome;

    @BindView(R.id.cpf)
    FieldView cpf;

    @BindView(R.id.endereco)
    FieldView endereco;

    @BindView(R.id.telefone)
    FieldView telefone;

    @BindView(R.id.data_nascimento)
    FieldView dataNascimento;

    @BindView(R.id.habilitacao)
    FieldView habilitacao;

    @BindView(R.id.senha)
    FieldView senha;

    @BindView(R.id.email)
    FieldView email;

    private CriarContaContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        presenter = new CriarContaPresenter(this, ApiManager.getService());
    }

    @OnClick(R.id.salvar_button)
    void salvarClick() {
        presenter.registrar(getUsuario());
    }


    private UsuarioDTO getUsuario() {
        return new UsuarioDTO.Builder()
                .nome(nome.getText())
                .cpf(cpf.getText())
                .endereco(endereco.getText())
                .telefone(telefone.getText())
                .dataNascimento(new Date())
                .numeroHabilitacao(habilitacao.getText())
                .email(email.getText())
                .senha(senha.getText())
                .build();
    }


    @Override
    public void mostrarLoading() {

    }

    @Override
    public void esconderLoading() {

    }

    @Override
    public void mostrarMensagemDeErro(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    @Override
    public void mostrarMensagemContaCriada() {
        Toast.makeText(this, "Conta criada, você já pode fazer login", Toast.LENGTH_SHORT).show();
        finish();
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