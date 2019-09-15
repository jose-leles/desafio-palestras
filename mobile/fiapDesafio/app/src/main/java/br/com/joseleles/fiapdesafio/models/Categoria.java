package br.com.joseleles.fiapdesafio.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Categoria implements Parcelable {
    @SerializedName("Codigo")
    private int codigo;

    @SerializedName("Descricao")
    private String descricao;

    @SerializedName("Palestras")
    private List<Palestra> palestras;


    public Categoria(){}

    public Categoria(Parcel source){
        codigo = source.readInt();
        descricao = source.readString();
        palestras = new ArrayList<>();
        source.readList(palestras, Palestra.class.getClassLoader());
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigo);
        dest.writeString(descricao);
        dest.writeList(palestras);
    }


    public int getCodigo() {return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<Palestra> getPalestras() { return palestras; }

    public void setPalestras(List<Palestra> palestras) { this.palestras = palestras; }


}
