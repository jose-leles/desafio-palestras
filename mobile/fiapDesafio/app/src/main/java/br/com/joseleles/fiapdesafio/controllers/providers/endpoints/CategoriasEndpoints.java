package br.com.joseleles.fiapdesafio.controllers.providers.endpoints;

import java.util.List;

import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Message;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.models.Palestra;
import br.com.joseleles.fiapdesafio.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoriasEndpoints {

    @GET("categorias/listar")
    Call<List<Categoria>> getCategories();

}
