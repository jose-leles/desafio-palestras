package br.com.joseleles.fiapdesafio.views.adapters;

import android.view.View;

public interface DelegateAdapterOnItemClick<T> {

    public void onItemClicked(T clicado, int position);
}
