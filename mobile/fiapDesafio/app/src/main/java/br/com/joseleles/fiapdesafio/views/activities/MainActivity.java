package br.com.joseleles.fiapdesafio.views.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.joseleles.fiapdesafio.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout holderDeTodaTela;
    private ActionBarDrawerToggle botaoDoMenu;
    private NavigationView navigationRecolhivel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        holderDeTodaTela = (DrawerLayout)findViewById(R.id.holder_geral);
        botaoDoMenu = new ActionBarDrawerToggle(this, holderDeTodaTela, R.string.aberto,R.string.fechado);

        holderDeTodaTela.addDrawerListener(botaoDoMenu);
        botaoDoMenu.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationRecolhivel = (NavigationView) findViewById(R.id.navigation_recolhivel);
        navigationRecolhivel.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.item_ver_inscricoes:

                    break;
            }
            return false;
        });
    }
}
