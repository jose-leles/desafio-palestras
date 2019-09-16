package br.com.joseleles.fiapdesafio.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.controllers.DAOs.UsuarioDAO;
import br.com.joseleles.fiapdesafio.controllers.providers.consumers.PalestraAPI;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Callback;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Message;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.models.Palestra;
import br.com.joseleles.fiapdesafio.models.Usuario;

public class FragmentDetalhes extends FragmentBase {

    private Palestra palestra;
    private Categoria categoriaPalestra;

    private Button btnInscrever;
    private Button btnEnviar;
    private TextView textDescricao;


    private TextView textAgendado;

    private LinearLayout formularioInscricao;
    private EditText textNome;
    private EditText textEmail;
    private EditText textCargo;
    private EditText textEmpresa;

    @Override
    public void setTagOfFragment() {
        this.tag = getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detalhes_palestra, container, false);

        if(savedInstanceState !=null){
            palestra = savedInstanceState.getParcelable(BundleTags.PALESTRA_DETALHES);
            categoriaPalestra = savedInstanceState.getParcelable(BundleTags.CATEGORIA_PALESTRA_DETALHES);
        }

        ((TextView)root.findViewById(R.id.categoria_palestra_detalhes)).setText(categoriaPalestra.getDescricao());

        ((TextView)root.findViewById(R.id.titulo_palestra_detalhes))
                .setText(palestra.getTitulo());
        ((TextView)root.findViewById(R.id.palestrante_palestra_detalhes))
                .setText(String.format("Palestrante: %s",palestra.getPalestrante()));
        ((TextView)root.findViewById(R.id.data_palestra_detalhes))
                .setText(String.format("Data: %s",palestra.getData()));
        ((TextView)root.findViewById(R.id.hora_palestra_detalhes))
                .setText(String.format("Horário: %s",palestra.getHora()));

        textDescricao = root.findViewById(R.id.descricao_palestra_detalhes);
        textDescricao.setText(palestra.getDescricao());

        formularioInscricao = root.findViewById(R.id.formulario_inscricao);
        formularioInscricao.setVisibility(View.GONE);
        textNome = root.findViewById(R.id.text_nome);
        textEmail = root.findViewById(R.id.text_email);
        textCargo = root.findViewById(R.id.text_cargo);
        textEmpresa = root.findViewById(R.id.text_empresa);

        textAgendado = root.findViewById(R.id.agendado_desde);
        textAgendado.setVisibility(View.GONE);

        btnInscrever = root.findViewById(R.id.button_realizar_inscricao);
        btnInscrever.setOnClickListener(v->{
            textDescricao.setVisibility(View.GONE);
            formularioInscricao.setVisibility(View.VISIBLE);

            v.setVisibility(View.GONE);
            btnEnviar.setVisibility(View.VISIBLE);
        });
        if(palestra.getQtdVagasDisponiveis() ==0){
            btnInscrever.setVisibility(View.GONE);
            textAgendado.setVisibility(View.VISIBLE);
            textAgendado.setTextColor(getResources().getColor(R.color.vermelho));
            textAgendado.setText(String.format(getString(R.string.vagas_estotadas_detalhes)));
        }

        btnEnviar = root.findViewById(R.id.button_enviar_solicitacao);
        btnEnviar.setOnClickListener(v->{
            Usuario informacoes = validaFormularioParaEnvio();
            if(informacoes != null && getContext() !=null){
                new UsuarioDAO(getContext()).deleteOthersEmail();
                new UsuarioDAO(getContext()).insertEmail(informacoes.getEmail());
                new PalestraAPI().inscreverUsuario(getContext(), informacoes,palestra.getCodigo()
                        , new Callback<Message, Message>(){

                            @Override
                            public void sucesso(Message data) {
                                if(data.isSucesso()){
                                    safeShowAlertDialog("Parabens!","Inscrição realizada");
                                    btnEnviar.setVisibility(View.GONE);
                                    textDescricao.setVisibility(View.VISIBLE);
                                    formularioInscricao.setVisibility(View.GONE);
                                    populateDetalhes();
                                }else{
                                    safeShowAlertDialog("Mensagem", data.getMessage());
                                }
                            }

                            @Override
                            public void erro(Message data) {
                                safeShowAlertDialog("Mensagem", data.getMessage());
                            }
                        }
                        ,"url_base");
            }

        });


        root.findViewById(R.id.button_voltar_detalhes).setOnClickListener(v->{
            popFragment();

        });

        populateDetalhes();

        return root;
    }

    public static FragmentDetalhes newInstance(Palestra palestra, Categoria categoria) {
        FragmentDetalhes fragment = new FragmentDetalhes();
        fragment.setPalestra(palestra);
        fragment.setCategoria(categoria);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BundleTags.PALESTRA_DETALHES, palestra);
        outState.putParcelable(BundleTags.CATEGORIA_PALESTRA_DETALHES, categoriaPalestra);
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }

    public void setCategoria(Categoria categoriaPalestra) {
        this.categoriaPalestra = categoriaPalestra;
    }

    public void populateDetalhes(){
        if(getContext()!=null){
            String emailLogado = new UsuarioDAO(getContext()).getEmail();
            new PalestraAPI().getDetalhesOfPalestra(getContext(), emailLogado, palestra.getCodigo(), new Callback<Palestra, Message>() {
                @Override
                public void sucesso(Palestra palestra) {
                    if(palestra!=null){
                        if(palestra.getEmailCadastrado()){
                            btnInscrever.setVisibility(View.GONE);
                            textAgendado.setVisibility(View.VISIBLE);
                            textAgendado.setText(
                                    String.format(
                                            getString(R.string.text_agendado)
                                            ,palestra.getDataInscricao()
                                            ,palestra.getHoraInscricao()
                                    )
                            );
                        }else if(palestra.getQtdVagasDisponiveis() > 0){
                            btnInscrever.setEnabled(true);
                            btnInscrever.setVisibility(View.VISIBLE);
                        }else{
                            btnInscrever.setVisibility(View.GONE);
                            textAgendado.setVisibility(View.VISIBLE);
                            textAgendado.setText(String.format(getString(R.string.vagas_estotadas)));
                        }
                    }
                }

                @Override
                public void erro(Message data) {
                    safeShowAlertDialog("Errro", data.getMessage());
                }
            }, "url_base");
        }

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
        Usuario user = new Usuario();
        user.setNome(textNome.getText().toString());
        user.setEmail(textEmail.getText().toString());
        user.setCargo(textCargo.getText().toString());
        user.setEmpresa(textEmpresa.getText().toString());
        return user;
    }
}
