package br.com.joseleles.fiapdesafio.views.activities;

import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Pattern;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.controllers.DAOs.UsuarioDAO;
import br.com.joseleles.fiapdesafio.controllers.providers.consumers.CategoriasAPI;
import br.com.joseleles.fiapdesafio.controllers.providers.consumers.UsuarioAPI;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Callback;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Message;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.models.Usuario;
import br.com.joseleles.fiapdesafio.views.fragments.BundleTags;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentBase;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentMinhasPalestras;
import br.com.joseleles.fiapdesafio.views.fragments.FragmentPalestras;

public class LoginActivity extends AppCompatActivity {


    private EditText textLogin;
    private EditText textSenha;
    private Button buttonLogin;

    private TextView cadastrarSe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textLogin = findViewById(R.id.text_email_login);
        textSenha = findViewById(R.id.text_senha);
        buttonLogin = findViewById(R.id.button_login);

        cadastrarSe = findViewById(R.id.text_cadastrar_se);
        cadastrarSe.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
        buttonLogin.setOnClickListener(v -> {
            Pattern regexDeEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            if(!regexDeEmail.matcher(textLogin.getText().toString()).find()){
                safeShowAlertDialog("Validação", "Email invalido");
                return;
            }
            if(textSenha.getText().toString().length()==0){
                safeShowAlertDialog("Validação", "Senha vazia");
                return;
            }
            Usuario usuario = new Usuario();
            usuario.setEmail(textLogin.getText().toString());
            usuario.setSenha(textSenha.getText().toString());
            new UsuarioAPI().logarUsuario(this, usuario, new Callback<Usuario, Message>() {
                @Override
                public void sucesso(Usuario autenticado) {
                    if(autenticado!=null){
                        UsuarioDAO dao = new UsuarioDAO(LoginActivity.this);
                        dao.deleteOthersEmail();
                        dao.insertEmail(autenticado.getEmail());
                        logarAutenticado(autenticado);
                    }else{
                        safeShowAlertDialog("Aviso", "Credenciais Invalidas");
                    }
                }

                @Override
                public void erro(Message data) {
                    safeShowAlertDialog("Aviso", data.getMessage());
                }
            }, "url_base");
        });


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

    private void logarAutenticado(Usuario autenticado){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(BundleTags.USUARIO_LOGADO,autenticado);
        intent.putExtras(extras);
        startActivity(intent);
    }



}
