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

    public void getDetalhesOfPalestra(@NonNull Context context, String email, int idPalestra, Callback<Palestra, Message> callback, String urlPropertie){
        new WebConsumer(context).getEndpoint(PalestraEndpoints.class, new DelegateConversionOfRequestAndResponse<Void, Palestra>() {
            @Override
            public String criarJsonAPartirDoObjeto(Void corpo) throws JSONException {
                return null;
            }

            @Override
            public Palestra criarObjetoAPartirDoJson(String corpo) throws JSONException {
                    JSONObject jsonPalestra = new JSONObject(corpo);
                    Palestra palestra = new Palestra();

                    palestra.setCodigo(jsonPalestra.getInt("Codigo"));
                    palestra.setImagem(jsonPalestra.getString("Imagem"));
                    palestra.setCodigoTipoCategoria(jsonPalestra.getInt("CodigoTipoCategoria"));
                    palestra.setTitulo(jsonPalestra.getString("Titulo"));
                    palestra.setPalestrante(jsonPalestra.getString("Palestrante"));
                    palestra.setDescricao(jsonPalestra.getString("Descricao"));
                    palestra.setData(jsonPalestra.getString("Data"));
                    palestra.setHora(jsonPalestra.getString("Hora"));
                    palestra.setQtdVagasDisponiveis(jsonPalestra.getInt("QtdVagasDisponiveis"));
                    if(jsonPalestra.has("EmailCadastrado"))
                        palestra.setEmailCadastrado(jsonPalestra.getString("EmailCadastrado"));
                    if(jsonPalestra.has("DataInscricao"))
                        palestra.setDataInscricao(jsonPalestra.getString("DataInscricao"));
                    if(jsonPalestra.has("HoraInscricao"))
                        palestra.setHoraInscricao(jsonPalestra.getString("HoraInscricao"));

                    return palestra;
            }
        },urlPropertie).getDeatailsOfPalestra(idPalestra, email).enqueue(new retrofit2.Callback<Palestra>() {
            @Override
            public void onResponse(Call<Palestra> call, Response<Palestra> response) {
                if(response.isSuccessful()){
                    callback.sucesso(response.body());
                }else{
                    callback.erro(new Message("Erro na conversão da resposta. code: "+response.code(), false));
                }
            }

            @Override
            public void onFailure(Call<Palestra> call, Throwable t) {
                if(t instanceof IOException){
                    // sem conexao com servidor
                    callback.erro(new Message("Sem Conexao com o servidor.", false));
                }else{
                    callback.erro(new Message("Erro interno ao tratar a requisicao.", false));
                }
            }
        });
    }

    public void inscreverUsuario(@NonNull Context context, Usuario usuario, int idPalestra, Callback<Message, Message> callback, String urlPropertie){
        new WebConsumer(context).getEndpoint(PalestraEndpoints.class, new DelegateConversionOfRequestAndResponse<Usuario, Message>() {
            @Override
            public String criarJsonAPartirDoObjeto(Usuario corpo) throws JSONException {
                JSONObject json = new JSONObject();
                json.put("Codigo",usuario.getCodigo());
                json.put("Cargo",usuario.getCargo());
                json.put("Nome",usuario.getNome());
                json.put("Empresa",usuario.getEmpresa());
                json.put("Email",usuario.getEmail());
                return json.toString();
            }

            @Override
            public Message criarObjetoAPartirDoJson(String corpo) throws JSONException {
                    JSONObject messageJson = new JSONObject(corpo);
                    Message mensagem = new Message(messageJson.getString("Mensagem"),messageJson.getBoolean("Sucesso"));

                    return mensagem;
            }
        },urlPropertie).inscreverUsuario(idPalestra,usuario).enqueue(new retrofit2.Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()){
                    callback.sucesso(response.body());
                }else{
                    callback.erro(new Message("Erro na conversão da resposta. code: "+response.code(), false));
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                if(t instanceof IOException){
                    // sem conexao com servidor
                    callback.erro(new Message("Sem Conexao com o servidor.", false));
                }else{
                    callback.erro(new Message("Erro interno ao tratar a requisicao.", false));
                }
            }
        });
    }

    public void getMinhasPalestras(Context context, String email, Callback<List<Categoria>, Message> callback, String url_base) {
        new WebConsumer(context).getEndpoint(PalestraEndpoints.class, new DelegateConversionOfRequestAndResponse<Void, List<Categoria>>() {
            @Override
            public String criarJsonAPartirDoObjeto(Void corpo) {
                return null;
            }

            @Override
            public List<Categoria> criarObjetoAPartirDoJson(String corpo) throws JSONException {
                JSONArray jsonArray = new JSONObject(corpo).getJSONArray("Categorias");
                List<Categoria> lista = new ArrayList<>();
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonCategoria = jsonArray.getJSONObject(i);
                    Categoria categoria = new Categoria();
                    categoria.setCodigo(jsonCategoria.getInt("Codigo"));
                    categoria.setDescricao(jsonCategoria.getString("Descricao"));
                    categoria.setPalestras(new ArrayList<>());
                    JSONArray listaDePalestra = jsonCategoria.getJSONArray("Palestras");

                    for(int j=0; j< listaDePalestra.length(); j++){
                        JSONObject jsonPalestra = listaDePalestra.getJSONObject(j);
                        Palestra palestra = new Palestra();
                        palestra.setCodigo(jsonPalestra.getInt("Codigo"));
                        palestra.setImagem(jsonPalestra.getString("Imagem"));
                        palestra.setCodigoTipoCategoria(jsonPalestra.getInt("CodigoTipoCategoria"));
                        palestra.setTitulo(jsonPalestra.getString("Titulo"));
                        palestra.setPalestrante(jsonPalestra.getString("Palestrante"));
                        palestra.setDescricao(jsonPalestra.getString("Descricao"));
                        palestra.setData(jsonPalestra.getString("Data"));
                        palestra.setHora(jsonPalestra.getString("Hora"));
                        palestra.setQtdVagasDisponiveis(jsonPalestra.getInt("QtdVagasDisponiveis"));
                        if(jsonPalestra.has("EmailCadastrado"))
                            palestra.setEmailCadastrado(jsonPalestra.getString("EmailCadastrado"));
                        if(jsonPalestra.has("DataInscricao"))
                            palestra.setEmailCadastrado(jsonPalestra.getString("DataInscricao"));
                        if(jsonPalestra.has("HoraInscricao"))
                            palestra.setEmailCadastrado(jsonPalestra.getString("HoraInscricao"));
                        categoria.getPalestras().add(palestra);
                    }
                    lista.add(categoria);
                }
                return lista;
            }
        },url_base).listarMinhasPalestras(email).enqueue(new retrofit2.Callback<List<Categoria>>() {
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
