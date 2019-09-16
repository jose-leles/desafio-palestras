package br.com.joseleles.fiapdesafio.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private int codigo;
    private String nome;
    private String email;
    private String empresa;
    private String cargo;

    public Usuario(){

    }

    public Usuario(Parcel source){
        this.codigo = source.readInt();
        this.nome = source.readString();
        this.cargo = source.readString();
        this.empresa = source.readString();
        this.email = source.readString();
    }

    public int getCodigo() {return codigo;}

    public void setCodigo(int codigo) {this.codigo = codigo;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getEmpresa() {return empresa;}

    public void setEmpresa(String empresa) {this.empresa = empresa;}

    public String getCargo() {return cargo;}

    public void setCargo(String cargo) {this.cargo = cargo;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigo);
        dest.writeString(nome);
        dest.writeString(cargo);
        dest.writeString(email);
        dest.writeString(empresa);
    }

    public static Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new Usuario(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
        }
    };
}
