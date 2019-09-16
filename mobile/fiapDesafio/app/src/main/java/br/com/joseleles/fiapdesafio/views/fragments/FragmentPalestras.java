package br.com.joseleles.fiapdesafio.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.controllers.providers.consumers.PalestraAPI;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Callback;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Message;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.models.Palestra;
import br.com.joseleles.fiapdesafio.views.adapters.AdaperPalestras;
import br.com.joseleles.fiapdesafio.views.adapters.DelegateAdapterOnItemClick;
import br.com.joseleles.fiapdesafio.views.adapters.SeccionadorDeAdapterPalestra;
import br.com.joseleles.fiapdesafio.views.adapters.SeccionadorDeAdapterPalestra.Seccao;

public class FragmentPalestras extends FragmentBase implements DelegateAdapterOnItemClick<Palestra> {

    RecyclerView reciclerView;
    AdaperPalestras adpter;
    List<Categoria> listaCategorias = new ArrayList<>(); // dentro tem as palestras
    Seccao[] mapaDasSeccoes;
    List<Palestra> listaCompleta = new ArrayList<>(); // lista sem as categorias

    Categoria filtro;

    private TextView avisoErro;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_palestras, container, false);

        reciclerView = root.findViewById(R.id.lista_palestras);
        reciclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        avisoErro = root.findViewById(R.id.aviso_erro);

        reciclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        if(savedInstanceState != null){
            filtro = savedInstanceState.getParcelable(BundleTags.FILTRO_PALESTRAS);
        }
        if(filtro!=null){
            root.findViewById(R.id.button_limpar_filtro).setVisibility(View.VISIBLE);
            root.findViewById(R.id.button_limpar_filtro).setOnClickListener(v->{
                v.setVisibility(View.GONE);
                filtro=null;
                if(listaCategorias!=null){
                    setAdpterSeccionadoParaORecyclerView(reciclerView);
                }
            });
        }else{
            root.findViewById(R.id.button_limpar_filtro).setVisibility(View.GONE);

        }

        populateCategoriasAndPalestras();



        return root;
    }

    private void populateCategoriasAndPalestras() {
        if(getContext() != null){
            new PalestraAPI().getCategoriasEPalestras(getContext(), new Callback<List<Categoria>, Message>() {
                @Override
                public void sucesso(List<Categoria> data) {
                    if(data!=null && data.size()>0){
                        listaCategorias = data;
                        reciclerView.setVisibility(View.VISIBLE);
                        avisoErro.setVisibility(View.GONE);
                        setAdpterSeccionadoParaORecyclerView(reciclerView);
                    }else{
                        avisoErro.setVisibility(View.VISIBLE);
                        reciclerView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void erro(Message data) {
                    avisoErro.setVisibility(View.VISIBLE);
                    reciclerView.setVisibility(View.GONE);
                    Toast.makeText(getContext(),data.getMessage(),Toast.LENGTH_LONG);
                }
            }, "url_base");

        }
    }

    private void setAdpterSeccionadoParaORecyclerView(RecyclerView reciclerView) {
        listaCompleta.clear();
        // [ {0,"categoria 1"}, {3, "categoria2"}]
        if(filtro == null){
            mapaDasSeccoes = new Seccao[listaCategorias.size()];
            //sempre comeca onde o anterior termina
            int palestrasAdicionadas = 0;
            for(int c= 0; c<listaCategorias.size(); c++){
                mapaDasSeccoes[c] = new Seccao(palestrasAdicionadas,listaCategorias.get(c).getDescricao());

                palestrasAdicionadas += listaCategorias.get(c).getPalestras().size();

                // for cada palestra da minha categoria
                List<Palestra> referenciasDasPalestrasDaCategoria = new ArrayList<>(listaCategorias.get(c).getPalestras());
                for(int p= 0; p<referenciasDasPalestrasDaCategoria.size(); p++){
                    listaCompleta.add(referenciasDasPalestrasDaCategoria.get(p));
                    referenciasDasPalestrasDaCategoria.remove(p);
                    p--;
                }
            }

        }else{
            mapaDasSeccoes = new Seccao[1];
            for(int c= 0; c<listaCategorias.size(); c++){
                if(listaCategorias.get(c).getCodigo() == filtro.getCodigo()){
                    mapaDasSeccoes[0] = new Seccao(0,filtro.getDescricao());
                    List<Palestra> referenciasDasPalestrasDaCategoria = new ArrayList<>(listaCategorias.get(c).getPalestras());
                    for(int p= 0; p<referenciasDasPalestrasDaCategoria.size(); p++){
                        listaCompleta.add(referenciasDasPalestrasDaCategoria.get(p));
                        referenciasDasPalestrasDaCategoria.remove(p);
                        p--;
                    }
                }
            }
        }

        adpter = new AdaperPalestras(getContext(), listaCompleta, this);

        //Add your adapter to the sectionAdapter
        SeccionadorDeAdapterPalestra adaperSeccionado = new
                SeccionadorDeAdapterPalestra(getContext(),R.layout.item_seccao_palestra,R.id.titulo_seccao_categoria,adpter);
        adaperSeccionado.setSections(mapaDasSeccoes);

        //Apply this adapter to the RecyclerView
        reciclerView.setAdapter(adaperSeccionado);

    }

    @Override
    public void onItemClicked(Palestra clicado, int position) {
        Categoria categoriaDaPalestraEscolhida=null;
        for(Categoria c :listaCategorias){
            if(c.getPalestras().contains(clicado)){
                categoriaDaPalestraEscolhida = c;
                break;
            }
        }
        redirect(FragmentDetalhes.newInstance(clicado,categoriaDaPalestraEscolhida));
        Toast.makeText(getContext(),"Position ="+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTagOfFragment() {
        this.tag = getClass().getSimpleName();
    }


    public static FragmentPalestras newInstance(Categoria filtro) {
        FragmentPalestras fragment = new FragmentPalestras();
        fragment.setCategoriaFiltro(filtro);
        return fragment;
    }

    public void setCategoriaFiltro(Categoria filtro){
        this.filtro = filtro;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BundleTags.FILTRO_PALESTRAS,filtro);
    }
}
