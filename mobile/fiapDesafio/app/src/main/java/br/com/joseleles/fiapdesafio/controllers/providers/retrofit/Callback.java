package br.com.joseleles.fiapdesafio.controllers.providers.retrofit;

public interface Callback<Sucesso, Erro> {
    public void sucesso(Sucesso data);

    public void erro(Erro data);
}
