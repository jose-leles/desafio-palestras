package br.com.joseleles.fiapdesafio.controllers.providers.endpoints;

import br.com.joseleles.fiapdesafio.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioEndpoints {

    @POST("usuario/logar")
    Call<Usuario> tryLogin(@Body Usuario tentar);

    @POST("usuario/cadastrar")
    Call<Usuario> cadastrar(@Body Usuario tentar);
}
