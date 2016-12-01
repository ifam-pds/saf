package br.edu.ifam.saf;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Iterator;

import br.edu.ifam.saf.api.dto.UsuarioDTO;
import br.edu.ifam.saf.configuracoes.SettingsActivity;
import br.edu.ifam.saf.itens.ItensFragment;
import br.edu.ifam.saf.itensadmin.ListarItensAdminFragment;
import br.edu.ifam.saf.listarcarrinho.ListarCarrinhoFragment;
import br.edu.ifam.saf.listarcategorias.ListarCategoriasFragment;
import br.edu.ifam.saf.listarrequisicoes.ListarRequisicoesFragment;
import br.edu.ifam.saf.listarusuarios.ListarUsuariosFragment;
import br.edu.ifam.saf.login.LoginActivity;
import br.edu.ifam.saf.relatorios.RelatoriosFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.toolbar_spinner)
    Spinner toolbarSpinner;
    //drawer header
    TextView headerTitulo;
    TextView headerEmail;
    TextView headerLoginButton;
    private MainContract.Presenter presenter;

    private static Iterable<MenuItem> inMenu(final Menu menu) {

        return new Iterable<MenuItem>() {
            private int currIndex = 0;

            @Override
            public Iterator<MenuItem> iterator() {
                return new Iterator<MenuItem>() {
                    @Override
                    public boolean hasNext() {
                        return currIndex < menu.size();
                    }

                    @Override
                    public MenuItem next() {
                        return menu.getItem(currIndex++);
                    }
                };
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().getItem(1));

        View header = navigationView.getHeaderView(0);
        headerEmail = (TextView) header.findViewById(R.id.header_email);
        headerTitulo = (TextView) header.findViewById(R.id.header_titulo);
        headerLoginButton = (TextView) header.findViewById(R.id.login_button);


        presenter = new MainPresenter(this, MainApplication.getRepository());
        presenter.init();

        headerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLogActionClicked();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

        }

    }

    private void setMenuVisibility(int id, boolean visible) {

        for (MenuItem item : inMenu(navigationView.getMenu())) {
            if (item.getItemId() == id) {
                item.setVisible(visible);
            }
            if (item.hasSubMenu()) {
                for (MenuItem subItem : inMenu(item.getSubMenu())) {
                    if (subItem.getItemId() == id) {
                        subItem.setVisible(visible);
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.recarregarPermissoes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        toolbarSpinner.setVisibility(View.GONE);
        toolbarSpinner.setAdapter(null);
        toolbarSpinner.setOnItemSelectedListener(null);

        switch (id) {
            case R.id.nav_itens:
                fragment = ItensFragment.newInstance();
                setTitle("Itens");
                break;
            case R.id.nav_usuarios:
                setTitle("Usuários");
                fragment = new ListarUsuariosFragment();
                break;
            case R.id.nav_listar_requisicoes:
                setTitle("Requisições");
                fragment = new ListarRequisicoesFragment();
                break;
            case R.id.nav_categorias:
                setTitle("Categorias");
                fragment = new ListarCategoriasFragment();
                break;
            case R.id.nav_carrinho:
                setTitle("Carrinho");
                fragment = new ListarCarrinhoFragment();
                break;
            case R.id.nav_itens_admin: {
                setTitle("Itens Administração");
                fragment = new ListarItensAdminFragment();
                break;
            }
            case R.id.nav_configuracoes: {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            case R.id.nav_relatorios: {
                setTitle("Relatórios");
                fragment = new RelatoriosFragment();
                break;
            }
            default:
                setTitle("Itens");
                fragment = ItensFragment.newInstance();
                break;

        }

//        fragment.setEnterTransition(new Slide(Gravity.STA));
        fragment.setExitTransition(new Slide(Gravity.END));
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setInfoUsuario(UsuarioDTO usuario) {
        headerTitulo.setText(usuario.getNome());
        headerEmail.setText(usuario.getEmail());
        headerLoginButton.setText("Logout");

    }

    @Override
    public void limpaInfoUsuario() {
        headerTitulo.setText(R.string.default_header_titulo);
        headerEmail.setText("");
        headerLoginButton.setText("Login");

    }

    @Override
    public void esconderOpcaoAdminCategorias() {
        setMenuVisibility(R.id.nav_categorias, false);

    }

    @Override
    public void mostrarOpcaoAdminCategorias() {
        setMenuVisibility(R.id.nav_categorias, true);

    }

    @Override
    public void esconderOpcaoAdminItens() {
        setMenuVisibility(R.id.nav_itens_admin, false);

    }

    @Override
    public void mostrarOpcaoAdminItens() {
        setMenuVisibility(R.id.nav_itens_admin, true);

    }

    @Override
    public void esconderOpcaoAdminRequisicoes() {
        setMenuVisibility(R.id.nav_listar_requisicoes, false);

    }

    @Override
    public void mostrarOpcaoAdminRequisicoes() {
        setMenuVisibility(R.id.nav_listar_requisicoes, true);

    }

    @Override
    public void esconderOpcaoAdminUsuarios() {
        setMenuVisibility(R.id.nav_usuarios, false);

    }

    @Override
    public void mostrarOpcaoAdminUsuarios() {
        setMenuVisibility(R.id.nav_usuarios, true);

    }

    @Override
    public void esconderOpcaoRelatorios() {
        setMenuVisibility(R.id.nav_relatorios, false);
    }

    @Override
    public void mostrarOpcaoRelatorios() {
        setMenuVisibility(R.id.nav_relatorios, true);
    }

    @Override
    public void iniciaTelaLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
