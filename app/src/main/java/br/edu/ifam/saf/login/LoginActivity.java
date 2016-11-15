package br.edu.ifam.saf.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.edu.ifam.saf.MainApplication;
import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.criarconta.CriarContaActivity;
import br.edu.ifam.saf.view.FieldView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.email)
    FieldView email;

    @BindView(R.id.senha)
    FieldView senha;
    @BindView(R.id.activity_login)
    View parentLayout;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this, MainApplication.getRepository());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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

    @OnClick(R.id.criar_conta)
    void onCriarContaClick() {
        startActivity(new Intent(this, CriarContaActivity.class));
    }


    @OnClick(R.id.login_button)
    void onLoginClick() {
        LoginData loginData = new LoginData();
        loginData.setEmail(email.getText());
        loginData.setSenha(senha.getText());
        presenter.login(loginData);
    }

    @Override
    public void mostrarMensagem(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();

        Snackbar.make(parentLayout, message, Snackbar.LENGTH_SHORT);
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
}
