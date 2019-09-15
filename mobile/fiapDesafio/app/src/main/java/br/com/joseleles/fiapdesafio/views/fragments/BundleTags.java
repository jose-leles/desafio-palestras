package br.com.joseleles.fiapdesafio.views.fragments;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static br.com.joseleles.fiapdesafio.views.fragments.BundleTags.*;

@Retention(RetentionPolicy.SOURCE)
@StringDef({PALESTRA_DETALHES})
public @interface BundleTags {
    String PALESTRA_DETALHES = "PALESTRA_DETALHES";
}
