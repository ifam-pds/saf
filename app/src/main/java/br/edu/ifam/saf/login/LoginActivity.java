package br.edu.ifam.saf.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.data.LoginData;
import br.edu.ifam.saf.view.FieldView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.email)
    FieldView email;
    @BindView(R.id.senha)
    FieldView senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);

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
    }
}
