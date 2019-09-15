package br.com.joseleles.fiapdesafio.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.joseleles.fiapdesafio.R;
import br.com.joseleles.fiapdesafio.models.Categoria;

public class FragmentPalestras extends FragmentBase {

    RecyclerView reciclerView;
    List<Categoria> listaCategorias; // dentro tem as palestras

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
        reciclerView.setAdapter();

        return root;
    }

}
