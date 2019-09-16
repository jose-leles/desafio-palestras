package br.com.joseleles.fiapdesafio.views.fragments;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static br.com.joseleles.fiapdesafio.views.fragments.BundleTags.*;

@Retention(RetentionPolicy.SOURCE)
@StringDef({PALESTRA_DETALHES})
public @interface BundleTags {
    String PALESTRA_DETALHES = "PALESTRA_DETALHES";
    String CATEGORIA_PALESTRA_DETALHES = "CATEGORIA_PALESTRA_DETALHES";
    String TAG_FRAGMENT = "TAG_FRAGMENT";
    String FILTRO_PALESTRAS = "FILTRO_PALESTRAS";
    String AVISO_SEM_CONEXAO = "AVISO_SEM_CONEXAO";
}
