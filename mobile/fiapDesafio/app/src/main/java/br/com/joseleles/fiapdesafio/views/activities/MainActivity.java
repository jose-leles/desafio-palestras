package br.com.joseleles.fiapdesafio.views.activities;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;

import java.util.List;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.controllers.providers.consumers.CategoriasAPI;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Callback;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Message;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.views.fragments.BundleTags;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentBase;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentMinhasPalestras;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentPalestras;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout holderDeTodaTela;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationRecolhivel;
    private List<Categoria> listaDoMenuDireito;
    private boolean avisoSemConexaoJaFoiDado =false;

    private FragmentManager fm;


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
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, holderDeTodaTela, barraSuperior, R.string.aberto,R.string.fechado);
        holderDeTodaTela.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        // setando propriedades do menu e listener dos items
        navigationRecolhivel = (NavigationView) findViewById(R.id.navigation_recolhivel);
        populateListaDeCategorias();
        setNavigationEsquerda();

        //carregando o primeiro fragment, só uma vez
        if(savedInstanceState == null){
            if(fm == null) fm = getSupportFragmentManager();
            carregarFragment(new FragmentPalestras());
        }else{
            avisoSemConexaoJaFoiDado = savedInstanceState.getBoolean(BundleTags.AVISO_SEM_CONEXAO);
        }

    }

    public void populateListaDeCategorias(){


        if(!isFinishing() && !isDestroyed()){
            new CategoriasAPI().getCategorias(this, new Callback<List<Categoria>, Message>() {
                @Override
                public void sucesso(List<Categoria> data) {
                    listaDoMenuDireito = data;
                    if(data !=null && data.size()>0){
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
                                            carregarFragment(FragmentPalestras.newInstance(categoria));
                                            return false;
                                        });
                                    }
                                }
                            }

                        }
                    }
                }

                @Override
                public void erro(Message data) {
                    if(!avisoSemConexaoJaFoiDado){
                        avisoSemConexaoJaFoiDado = true;
                        safeShowAlertDialog("Aviso",data.getMessage());
                    }
                    new Handler(getMainLooper()).postDelayed(MainActivity.this::populateListaDeCategorias,5000);
                }
            },"url_base");

        }





    }

    public void safeShowAlertDialog(String title, String message){
        if(!isFinishing() && !isDestroyed()){
            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setPositiveButton(getResources().getString(R.string.ok), (dialog,escolha)->{
                dialog.dismiss();
            });
            alert.show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BundleTags.AVISO_SEM_CONEXAO,avisoSemConexaoJaFoiDado);
    }

    private void setNavigationEsquerda() {

        navigationRecolhivel.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.item_ver_inscricoes:
                    carregarFragment(new FragmentMinhasPalestras());
                    break;
            }
            holderDeTodaTela.closeDrawers();
            return true;
        });
    }

    private void carregarFragment(FragmentBase fragment){
        if(fm == null) fm = getSupportFragmentManager();

        // se o fragment nao esta adicionado
        if(fm != null && fm.findFragmentByTag(fragment.getTagFragment()) == null){
            //se tem mais de 2 um na pilha tira um
            if(fm.getBackStackEntryCount() > 2){
                fm.popBackStack();
            }
        }else if(fm!=null){ //se o fragment estiver na pilha
            fm.popBackStack();
        }

        if(fm!=null){
            fm.beginTransaction()
                    .replace(R.id.fundo_para_preencher, fragment, fragment.getTagFragment())
                    .addToBackStack(fragment.getTagFragment())
                    .commit();
        }


    }


    @Override
    public void onBackPressed() {
        AlertDialog.OnClickListener paraDeslogar = (dialog, which) -> {
            if(which == AlertDialog.BUTTON_POSITIVE){
                finish();
            }
        };
        FragmentManager fm = getSupportFragmentManager();
        int backStackCount = fm.getBackStackEntryCount();
        if (backStackCount == 1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Sair");
            alert.setMessage("Deseja sair?");
            alert.setPositiveButton(getText(R.string.yes),paraDeslogar);
            alert.setNegativeButton(getText(R.string.no),paraDeslogar);
            alert.show();
        }
        super.onBackPressed();
    }

}
