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

public interface PalestraEndpoints {

    @GET("palestra/listar")
    Call<List<Categoria>> getCategoriesWithLectures();

    @GET("palestra/detalhes/{id}")
    Call<Palestra> getDeatailsOfPalestra(@Path("id") int id, @Query("emailUsuario") String email);

    @POST("palestra/inscrever/{id}")
    Call<Message> inscreverUsuario(@Path("id") int id, @Body Usuario user);

    @GET("palestra/listar")
    Call<List<Categoria>> listarMinhasPalestras(@Query("emailUsuario") String email);
}
