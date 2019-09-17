package br.com.joseleles.fiapdesafio.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.controllers.DAOs.UsuarioDAO;
import br.com.joseleles.fiapdesafio.controllers.providers.consumers.UsuarioAPI;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Callback;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Message;
import br.com.joseleles.fiapdesafio.models.Usuario;
import br.com.joseleles.fiapdesafio.views.fragments.BundleTags;

public class CadastroActivity extends AppCompatActivity {


    private EditText textNome;
    private EditText textEmail;
    private EditText textEmpresa;
    private EditText textCargo;

    private EditText textSenha;
    private EditText textConfirmarSenha;

    private Button buttonCadastrar;
    private Button buttonVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_se);

        textNome = findViewById(R.id.text_nome);
        textEmail = findViewById(R.id.text_email);
        textCargo = findViewById(R.id.text_cargo);
        textEmpresa = findViewById(R.id.text_empresa);

        textSenha = findViewById(R.id.text_senha);
        textConfirmarSenha = findViewById(R.id.text_confirmar_senha);

        buttonCadastrar = findViewById(R.id.button_cadastrar_se);

        buttonVoltar = findViewById(R.id.button_voltar_cadastro);
        buttonVoltar.setOnClickListener(v->finish());
        buttonCadastrar.setOnClickListener(v -> {
            Usuario paraCadastrar = validaFormularioParaEnvio();
            if(paraCadastrar!=null){
                new UsuarioAPI().cadastrarUsuario(this, paraCadastrar, new Callback<Usuario, Message>() {
                    @Override
                    public void sucesso(Usuario data) {
                        if(data!=null){
                            UsuarioDAO dao = new UsuarioDAO(CadastroActivity.this);
                            dao.deleteOthersEmail();
                            dao.insertEmail(paraCadastrar.getEmail());
                            logarAutenticado(paraCadastrar);
                        }else{
                            safeShowAlertDialog("Aviso", "Falha ao cadastrar-se");
                        }
                    }

                    @Override
                    public void erro(Message data) {
                        safeShowAlertDialog("Aviso", data.getMessage());
                    }
                },"url_base");
            }
        });


    }

    // usar para atualizar a base de dados quando for cadastrrar

//


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

    // return null == invalido
    // return Usuario == validado
    // Pattern references: [ "https://howtodoinjava.com/regex/java-regex-validate-email-address" ,
    // "https://stackoverflow.com/questions/42266148/email-validation-regex-java" ]
    public Usuario validaFormularioParaEnvio(){
        Pattern regexDeEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if(textNome.getText().toString().length()<3){
            safeShowAlertDialog("Validação", "Nome muito curto");
            return null;
        }

        if(!regexDeEmail.matcher(textEmail.getText().toString()).find()){
            safeShowAlertDialog("Validação", "Email invalido");
            return null;
        }

        if(textEmpresa.getText().toString().length() < 3){
            safeShowAlertDialog("Validação", "Empresa de nome muito curto");
            return null;
        }

        if(textCargo.getText().toString().length() < 3){
            safeShowAlertDialog("Validação", "Cargo com descricao muito curta");
            return null;
        }

        if(textSenha.getText().toString().length() < 3){
            safeShowAlertDialog("Validação", "Senha muito curta");
            return null;
        }

        if(!textConfirmarSenha.getText().toString().equals(textSenha.getText().toString())){
            safeShowAlertDialog("Validação", "Senhas não coincidem");
            return null;
        }

        Usuario user = new Usuario();
        user.setNome(textNome.getText().toString());
        user.setEmail(textEmail.getText().toString());
        user.setCargo(textCargo.getText().toString());
        user.setEmpresa(textEmpresa.getText().toString());
        user.setSenha(textSenha.getText().toString());
        return user;
    }



}
