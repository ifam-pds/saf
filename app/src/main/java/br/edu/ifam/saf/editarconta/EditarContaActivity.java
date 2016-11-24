package br.edu.ifam.saf.editarconta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

import br.edu.ifam.saf.R;
import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.util.ApiManager;
import br.edu.ifam.saf.util.DateFormatter;
import br.edu.ifam.saf.view.FieldView;
import butterknife.BindView;
import butterknife.ButterKnife;
import br.edu.ifam.saf.criaritem.CriarItemPresenter;
import butterknife.OnClick;

/**
 * Created by marciogabriel on 21/11/16.
 */

public class EditarContaActivity extends AppCompatActivity implements EditarContaContract.View {

    public static final String EXTRA_USUARIO_ID = "usuario_id";

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

    @BindView(R.id.perfil_spinner)
    Spinner perfil;

    private EditarContaContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_conta);
        ButterKnife.bind(this);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            int usuarioId = bundle.getInt(EXTRA_USUARIO_ID);
            presenter = new EditarContaPresenter(this, ApiManager.getService(), usuarioId);
            presenter.carregarUsuario();
        } else {
            Toast.makeText(this, "Usuário Inválido!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick(R.id.salvar_button)
    void salvarClick() {
        presenter.alterar(getUsuario());
    }

    private UsuarioDTO getUsuario() {

        Date nascimento;
        try {
            nascimento = DateFormatter.parse(dataNascimento.getText());
        } catch (ParseException e) {
            nascimento = null;
        }


        return new UsuarioDTO.Builder()
                .nome(nome.getText())
                .cpf(cpf.getText())
                .endereco(endereco.getText())
                .telefone(telefone.getText())
                .dataNascimento(nascimento)
                .numeroHabilitacao(habilitacao.getText())
                .email(email.getText())
                .senha(senha.getText())
                .senha(perfil.getSelectedItem().toString())
                .build();
    }

    @Override
    public void mostrarMensagemContaAlterada() {
        Toast.makeText(this, "Conta alterada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void mostrarUsuario(UsuarioDTO usuarioDTO) {
        nome.setText(usuarioDTO.getNome());
        cpf.setText(usuarioDTO.getCpf());
        endereco.setText(usuarioDTO.getEndereco());
        telefone.setText(usuarioDTO.getTelefone());
        dataNascimento.setText(DateFormatter.format(usuarioDTO.getDataNascimento()));
        habilitacao.setText(usuarioDTO.getNumeroHabilitacao());
        email.setText(usuarioDTO.getEmail());
        senha.setText(usuarioDTO.getSenha());
        perfil.setSelection(usuarioDTO.getPerfil().getNivel());
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}