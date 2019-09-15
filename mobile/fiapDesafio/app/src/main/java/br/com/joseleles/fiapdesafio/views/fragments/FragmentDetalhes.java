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

public class FragmentDetalhes extends FragmentBase {

    private Palestra palestra;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detalhes_palestra, container, false);

        if(savedInstanceState !=null){
            palestra = savedInstanceState.getParcelable(BundleTags.PALESTRA_DETALHES);
        }

        return root;
    }

    public static FragmentDetalhes newInstance(Palestra p) {
        FragmentDetalhes fragment = new FragmentDetalhes();
        fragment.setPalestra(p);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BundleTags.PALESTRA_DETALHES, palestra);
    }

    public void setPalestra(Palestra palestra) {
        this.palestra = palestra;
    }
}
