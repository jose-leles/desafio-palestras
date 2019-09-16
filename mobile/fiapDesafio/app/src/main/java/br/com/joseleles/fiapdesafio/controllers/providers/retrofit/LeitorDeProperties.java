package br.com.joseleles.fiapdesafio.controllers.providers.retrofit;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

public class LeitorDeProperties {
    Context context;
    int estadoDeConsumo = 0; // inicialmente em teste
    String[] nomeDoArquivoPorEstado = new String[]{"homologacao", "producao"};
    public LeitorDeProperties (Context context){
        this.context = context;
        try {
            InputStream is = this.context.getAssets().open("chave_app.properties");
            Properties p = new Properties();
            p.load(is);
            estadoDeConsumo = Integer.parseInt(p.getProperty("estado"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getUrlByPropertie(String propertie) {
        try {
            // 0 é a primeira posicao e 1 é a segunda
            InputStream is = context.getAssets().open(String.format("%s.properties", nomeDoArquivoPorEstado[estadoDeConsumo]));
            Properties p = new Properties();
            p.load(is);
            return p.getProperty(propertie);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
