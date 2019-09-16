package br.com.joseleles.fiapdesafio.controllers.providers.consumers;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.joseleles.fiapdesafio.controllers.providers.endpoints.PalestraEndpoints;
import br.com.joseleles.fiapdesafio.controllers.providers.endpoints.UsuarioEndpoints;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Callback;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.DelegateConversionOfRequestAndResponse;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Message;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.WebConsumer;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.models.Palestra;
import br.com.joseleles.fiapdesafio.models.Usuario;
import retrofit2.Call;
import retrofit2.Response;

public class UsuarioAPI {

    public void logarUsuario(@NonNull Context context, Usuario usuario, Callback<Usuario, Message> callback, String urlPropertie){
        new WebConsumer(context).getEndpoint(UsuarioEndpoints.class, new DelegateConversionOfRequestAndResponse<Usuario, Usuario>() {
            @Override
            public String criarJsonAPartirDoObjeto(Usuario corpo) throws JSONException {
                JSONObject jsonUsuario = new JSONObject();
                jsonUsuario.put("Senha",corpo.getSenha());
                jsonUsuario.put("Email",corpo.getEmail());
                return jsonUsuario.toString();
            }

            @Override
            public Usuario criarObjetoAPartirDoJson(String corpo) throws JSONException {
                JSONObject jsonUsuario = new JSONObject(corpo);
                Usuario autenticado = new Usuario();
                autenticado.setEmpresa(jsonUsuario.getString("Empresa"));
                autenticado.setNome(jsonUsuario.getString("Nome"));
                autenticado.setCargo(jsonUsuario.getString("Cargo"));
                autenticado.setEmail(jsonUsuario.getString("Email"));
                return autenticado;
            }
        },urlPropertie).tryLogin(usuario).enqueue(new retrofit2.Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    callback.sucesso(response.body());
                }else{
                    callback.erro(new Message("Erro na conversão da resposta. code: "+response.code(), false));
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                if(t instanceof IOException){
                    // sem conexao com servidor
                    callback.erro(new Message("Sem Conexao com o servidor.", false));
                }else{
                    callback.erro(new Message("Credenciais invalidas", false));
                }
            }
        });
    }

}
