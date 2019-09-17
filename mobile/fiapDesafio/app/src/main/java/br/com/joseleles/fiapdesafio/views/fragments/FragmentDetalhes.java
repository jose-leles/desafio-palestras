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
    private TextView textDescricao;


    private TextView textAgendado;

    private Usuario logado;

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
            logado = savedInstanceState.getParcelable(BundleTags.USUARIO_LOGADO);
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

        textAgendado = root.findViewById(R.id.agendado_desde);
        textAgendado.setVisibility(View.GONE);

        btnInscrever = root.findViewById(R.id.button_realizar_inscricao);
        btnInscrever.setOnClickListener(v->{
            if(getContext() !=null){
                new PalestraAPI().inscreverUsuario(getContext(), logado,palestra.getCodigo()
                        , new Callback<Message, Message>(){
                            @Override
                            public void sucesso(Message data) {
                                if(data.isSucesso()){
                                    safeShowAlertDialog("Parabens!","Inscrição realizada");
                                    btnInscrever.setVisibility(View.GONE);
                                    textDescricao.setVisibility(View.VISIBLE);
                                    populateDetalhes();
                                }else{
                                    safeShowAlertDialog("Mensagem", data.getMessage());
                                    populateDetalhes();
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
        if(palestra.getQtdVagasDisponiveis() ==0){
            btnInscrever.setVisibility(View.GONE);
            textAgendado.setVisibility(View.VISIBLE);
            textAgendado.setTextColor(getResources().getColor(R.color.vermelho));
            textAgendado.setText(String.format(getString(R.string.vagas_estotadas_detalhes)));
        }

        root.findViewById(R.id.button_voltar_detalhes).setOnClickListener(v->{
            popFragment();

        });

        populateDetalhes();

        return root;
    }

    public static FragmentDetalhes newInstance(Usuario logado, Palestra palestra, Categoria categoria) {
        FragmentDetalhes fragment = new FragmentDetalhes();
        fragment.setPalestra(palestra);
        fragment.setLogado(logado);
        fragment.setCategoria(categoria);
        return fragment;
    }

    private void setLogado(Usuario logado){
        this.logado = logado;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BundleTags.PALESTRA_DETALHES, palestra);
        outState.putParcelable(BundleTags.CATEGORIA_PALESTRA_DETALHES, categoriaPalestra);
        outState.putParcelable(BundleTags.USUARIO_LOGADO, logado);
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }

    public void setCategoria(Categoria categoriaPalestra) {
        this.categoriaPalestra = categoriaPalestra;
    }

    public void populateDetalhes(){
        if(getContext()!=null){
            new PalestraAPI().getDetalhesOfPalestra(getContext(), logado, palestra.getCodigo(), new Callback<Palestra, Message>() {
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


}
