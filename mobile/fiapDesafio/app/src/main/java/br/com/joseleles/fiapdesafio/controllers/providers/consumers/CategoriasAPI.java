package br.com.joseleles.fiapdesafio.controllers.providers.consumers;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.joseleles.fiapdesafio.controllers.providers.endpoints.CategoriasEndpoints;
import br.com.joseleles.fiapdesafio.controllers.providers.endpoints.PalestraEndpoints;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Callback;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.DelegateConversionOfRequestAndResponse;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.Message;
import br.com.joseleles.fiapdesafio.controllers.providers.retrofit.WebConsumer;
import br.com.joseleles.fiapdesafio.models.Categoria;
import br.com.joseleles.fiapdesafio.models.Palestra;
import br.com.joseleles.fiapdesafio.models.Usuario;
import retrofit2.Call;
import retrofit2.Response;

public class CategoriasAPI {

    public void getCategorias(@NonNull Context context, Callback<List<Categoria>, Message> callback, String urlPropertie){
        new WebConsumer(context).getEndpoint(CategoriasEndpoints.class, new DelegateConversionOfRequestAndResponse<Void, List<Categoria>>() {
            @Override
            public String criarJsonAPartirDoObjeto(Void corpo) throws JSONException {
                return null;
            }

            @Override
            public List<Categoria> criarObjetoAPartirDoJson(String corpo) throws JSONException {
                JSONArray jsonArray = new JSONArray(corpo);
                List<Categoria> lista = new ArrayList<>();
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonCategoria = jsonArray.getJSONObject(i);
                    Categoria categoria = new Categoria();
                    categoria.setCodigo(jsonCategoria.getInt("Codigo"));
                    categoria.setDescricao(jsonCategoria.getString("Descricao"));
                    categoria.setPalestras(new ArrayList<>());
                    lista.add(categoria);
                }
                return lista;
            }
        },urlPropertie).getCategories().enqueue(new retrofit2.Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    callback.sucesso(response.body());
                }else{
                    callback.erro(new Message("Erro na conversão da resposta. code: "+response.code(), false));
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                if(t instanceof IOException){
                    // sem conexao com servidor
                    callback.erro(new Message("Sem Conexao com o servidor.", false));
                }else{
                    callback.erro(new Message("Erro interno ao tratar a requisicao.", false));
                }
            }
        });
    }

}
