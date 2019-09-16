package br.com.joseleles.fiapdesafio.views.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.SubMenu;

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

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //configurando a barra superior

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



}
