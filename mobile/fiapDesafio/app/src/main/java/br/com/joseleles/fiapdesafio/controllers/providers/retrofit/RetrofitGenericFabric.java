package br.com.joseleles.fiapdesafio.controllers.providers.retrofit;

import org.json.JSONException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class RetrofitGenericFabric<Requisicao, Resposta> extends Converter.Factory {

    DelegateConversionOfRequestAndResponse<Requisicao, Resposta> comoConverter;

    public RetrofitGenericFabric(DelegateConversionOfRequestAndResponse comoConverter){
        this.comoConverter = comoConverter;
    }

    @Override
    public Converter<ResponseBody, Resposta> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return response -> {
            try {
                return comoConverter.criarObjetoAPartirDoJson(response.string());
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        };
    }

    @Override
    public Converter<Requisicao, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return requisicao -> {
            try {
                return RequestBody.create(MediaType.parse("application/json"),
                        comoConverter.criarJsonAPartirDoObjeto(requisicao));
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        };
    }
}
