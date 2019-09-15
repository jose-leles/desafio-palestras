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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import java.util.ArrayList;
import java.util.List;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentBase;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentPalestras;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout holderDeTodaTela;
    private ActionBarDrawerToggle botaoDoMenu;
    private NavigationView navigationRecolhivel;
    private List<Categoria> listaDoMenuDireito;


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
        populateListaDeCategorias();
        setNavigationEsquerda();

        //carregando o primeiro fragment
        carregarFragment(new FragmentPalestras());

    }

    public void populateListaDeCategorias(){
        listaDoMenuDireito = new ArrayList<>();

        Categoria fake1 = new Categoria();
        fake1.setDescricao("Categoria 1");
        fake1.setCodigo(1);
        listaDoMenuDireito.add(fake1);

        Categoria fake2 = new Categoria();
        fake2.setDescricao("Categoria 2");
        fake2.setCodigo(2);
        listaDoMenuDireito.add(fake2);

        if(navigationRecolhivel !=null && navigationRecolhivel.getMenu() !=null){

            Menu todosGrupos = navigationRecolhivel.getMenu();
            for(int i=0; i<todosGrupos.size();i++){
                if(todosGrupos.getItem(i).getItemId() == R.id.group_categorias){
                    SubMenu subMenu = todosGrupos.getItem(i).getSubMenu();
                    subMenu.clear();
                    for(int c=0; c<listaDoMenuDireito.size(); c++){
                        final Categoria categoria = listaDoMenuDireito.get(c);
                        subMenu.add(categoria.getDescricao());
                        subMenu.getItem(c).setOnMenuItemClickListener(item -> {
                            Log.i("DEBUG_CATEGORIA_SLCND",categoria.getDescricao());
                            return false;
                        });
                    }
                }
            }

        }



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
