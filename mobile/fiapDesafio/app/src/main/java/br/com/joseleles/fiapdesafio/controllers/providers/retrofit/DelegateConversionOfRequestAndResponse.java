package br.com.joseleles.fiapdesafio.controllers.providers.retrofit;

import org.json.JSONException;

public interface DelegateConversionOfRequestAndResponse<Requisicao, Resposta> {

    public String criarJsonAPartirDoObjeto(Requisicao corpo) throws JSONException;

    public Resposta criarObjetoAPartirDoJson(String corpo) throws JSONException;
}
