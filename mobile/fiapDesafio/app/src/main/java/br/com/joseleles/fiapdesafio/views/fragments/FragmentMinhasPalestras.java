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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.models.Palestra;
import br.com.joseleles.fiapdesafio.views.adapters.AdaperPalestras;
import br.com.joseleles.fiapdesafio.views.adapters.DelegateAdapterOnItemClick;
import br.com.joseleles.fiapdesafio.views.adapters.SeccionadorDeAdapterPalestra;
import br.com.joseleles.fiapdesafio.views.adapters.SeccionadorDeAdapterPalestra.Seccao;

public class FragmentMinhasPalestras extends FragmentBase implements DelegateAdapterOnItemClick<Palestra> {

    RecyclerView reciclerView;
    AdaperPalestras adpter;
    List<Categoria> listaCategorias = new ArrayList<>(); // dentro tem as palestras
    Seccao[] mapaDasSeccoes;
    List<Palestra> listaCompleta = new ArrayList<>(); // lista sem as categorias

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

        reciclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        populateFakeCategorias();

        setAdpterSeccionadoParaORecyclerView(reciclerView);



        return root;
    }

    private void populateFakeCategorias() {
        listaCategorias = new ArrayList<>();
        listaCategorias.add(new Categoria());
        listaCategorias.get(0).setDescricao("Tecnologia empresarial");
        listaCategorias.get(0).setPalestras(new ArrayList<>(Arrays.asList(new Palestra(),new Palestra())));
        listaCategorias.get(0).getPalestras().get(0).setTitulo("Gestao de pessoas por competencias");
        listaCategorias.get(0).getPalestras().get(0).setData("19/10/2019");
        listaCategorias.get(0).getPalestras().get(0).setHora("19h10");
        listaCategorias.get(0).getPalestras().get(0).setImagem("3.jpg");
        listaCategorias.get(0).getPalestras().get(0).setQtdVagasDisponiveis(1);
        listaCategorias.get(0).getPalestras().get(0).setPalestrante("Jose Victor B. Leles");
        listaCategorias.get(0).getPalestras().get(1).setTitulo("Gestao de pessoas por competencias 2");
        listaCategorias.get(0).getPalestras().get(1).setData("20/10/2019");
        listaCategorias.get(0).getPalestras().get(1).setHora("19h20");
        listaCategorias.get(0).getPalestras().get(1).setImagem("3.jpg");
        listaCategorias.get(0).getPalestras().get(1).setQtdVagasDisponiveis(0);
        listaCategorias.get(0).getPalestras().get(1).setPalestrante("Jose Victor B. Leles 2");
//        listaCategorias.add(new Categoria());
//        listaCategorias.get(1).setDescricao("Categoria 2");
//        listaCategorias.get(1).setPalestras(new ArrayList<>(Arrays.asList(new Palestra(),new Palestra())));
//        listaCategorias.get(1).getPalestras().get(0).setTitulo("Elemento 3");
//        listaCategorias.get(1).getPalestras().get(1).setTitulo("Elemento 4");
    }

    private void setAdpterSeccionadoParaORecyclerView(RecyclerView reciclerView) {
        // [ {0,"categoria 1"}, {3, "categoria2"}]
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
    }

    @Override
    public void setTagOfFragment() {
        this.tag = getClass().getSimpleName();
    }
}
