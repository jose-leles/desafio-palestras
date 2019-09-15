package br.com.joseleles.fiapdesafio.views.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentBase;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentPalestras;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout holderDeTodaTela;
    private ActionBarDrawerToggle botaoDoMenu;
    private NavigationView navigationRecolhivel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //configurando a barra superior
        Toolbar barraSuperior = findViewById(R.id.toolbar);
        setSupportActionBar(barraSuperior);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // dizendo para o minha tela que alguem pode desenhar algo nela, no caso, desenhar meu menu
        holderDeTodaTela = (DrawerLayout)findViewById(R.id.holder_geral);
        botaoDoMenu = new ActionBarDrawerToggle(this, holderDeTodaTela, barraSuperior, R.string.aberto,R.string.fechado);
        holderDeTodaTela.addDrawerListener(botaoDoMenu);
        botaoDoMenu.syncState();

        // setando propriedades do menu e listener dos items
        navigationRecolhivel = (NavigationView) findViewById(R.id.navigation_recolhivel);
        setNavigationEsquerda();

        //carregando o primeiro fragment
        carregarFragment(new FragmentPalestras());

    }


    private void setNavigationEsquerda() {
        navigationRecolhivel.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.item_ver_inscricoes:

                    break;
            }
            return false;
        });
    }

    private void carregarFragment(FragmentBase fragment){
        FragmentManager fm = getSupportFragmentManager();
        if(fm != null){
            if(fm.getBackStackEntryCount() < 2){
                fm.beginTransaction()
                        .replace(R.id.fundo_para_preencher,fragment)
                        .addToBackStack(fragment.getClass().getSimpleName())
                        .commit();
            }else{
                fm.popBackStack();
                fm.beginTransaction()
                        .replace(R.id.fundo_para_preencher,fragment, fragment.getClass().getSimpleName())
                        .addToBackStack(fragment.getClass().getSimpleName())
                        .commit();
            }
        }
    }


}
