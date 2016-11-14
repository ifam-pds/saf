package br.edu.ifam.saf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifam.saf.configuracoes.SettingsActivity;
import br.edu.ifam.saf.itens.ItensFragment;
import br.edu.ifam.saf.listarcarrinho.ListarCarrinhoFragment;
import br.edu.ifam.saf.listarrequisicoes.ListarRequisicoesFragment;
import br.edu.ifam.saf.listarusuarios.ListarUsuariosFragment;
import br.edu.ifam.saf.login.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            case R.id.action_login:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;

        switch (id) {
            case R.id.nav_itens:
                fragment = ItensFragment.newInstance();
                break;
            case R.id.nav_usuarios:
                fragment = new ListarUsuariosFragment();
                break;
            case R.id.nav_listar_requisicoes:
                fragment = new ListarRequisicoesFragment();
                break;
            case R.id.nav_carrinho:
                fragment = new ListarCarrinhoFragment();
                break;
            default:
                fragment = ItensFragment.newInstance();
                break;

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
