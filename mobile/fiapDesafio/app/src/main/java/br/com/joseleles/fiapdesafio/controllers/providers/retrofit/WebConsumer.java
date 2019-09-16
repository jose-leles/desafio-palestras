package br.com.joseleles.fiapdesafio.controllers.providers.retrofit;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class WebConsumer {
    LeitorDeProperties leitorDeProperties;

    public WebConsumer(Context context){
        this.leitorDeProperties = new LeitorDeProperties(context);
    }

    public <Endpoint> Endpoint getEndpoint(Class<Endpoint> classeEndPoint, DelegateConversionOfRequestAndResponse requisicaoEResposta, String propertieBaseUrl){
        OkHttpClient.Builder builderHttpClient = new OkHttpClient.Builder();
        OkHttpClient httpClient = builderHttpClient.build();
        builderHttpClient.connectTimeout(1, TimeUnit.MINUTES);
        builderHttpClient.readTimeout(30, TimeUnit.SECONDS);
        builderHttpClient.writeTimeout(30, TimeUnit.SECONDS);
        Retrofit.Builder builderRetrofit = new Retrofit
                .Builder()
                .baseUrl(leitorDeProperties.getUrlByPropertie(propertieBaseUrl))
                .addConverterFactory(new RetrofitGenericFabric(requisicaoEResposta));
        return builderRetrofit.client(httpClient).build().create(classeEndPoint);
    }

}
