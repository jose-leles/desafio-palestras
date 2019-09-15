package br.com.joseleles.fiapdesafio.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Palestra implements Parcelable {

    @SerializedName("Codigo")
    private int codigo;

    @SerializedName("CodigoTipoCategoria")
    private int codigoTipoCategoria;

    @SerializedName("Imagem")
    private String imagem;

    @SerializedName("Titulo")
    private String titulo;

    @SerializedName("Palestrante")
    private String palestrante;

    @SerializedName("Descricao")
    private String descricao;

    @SerializedName("Data")
    private String data;

    @SerializedName("Hora")
    private String hora;

    @SerializedName("QtdVagasDisponiveis")
    private int qtdVagasDisponiveis;

    //when listed by a user, must have this atributes on it
    @SerializedName("EmailCadastrado")
    private String emailCadastrado;

    @SerializedName("DataInscricao")
    private String dataInscricao;

    @SerializedName("HoraInscricao")
    private String horaInscricao;


    public Palestra(Parcel source){
        codigo = source.readInt();
        codigoTipoCategoria= source.readInt();
        imagem= source.readString();
        titulo= source.readString();
        palestrante= source.readString();
        descricao= source.readString();
        data= source.readString();
        hora= source.readString();
        qtdVagasDisponiveis= source.readInt();
        emailCadastrado= source.readString();
        dataInscricao= source.readString();
        horaInscricao= source.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codigo);
        dest.writeInt(codigoTipoCategoria);
        dest.writeString(imagem);
        dest.writeString(titulo);
        dest.writeString(palestrante);
        dest.writeString(descricao);
        dest.writeString(data);
        dest.writeString(hora);
        dest.writeInt(qtdVagasDisponiveis);
        dest.writeString(emailCadastrado);
        dest.writeString(dataInscricao);
        dest.writeString(horaInscricao);
    }

    public static Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new Palestra(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
        }
    };


    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public int getCodigoTipoCategoria() { return codigoTipoCategoria; }

    public void setCodigoTipoCategoria(int codigoTipoCategoria) { this.codigoTipoCategoria = codigoTipoCategoria; }

    public String getImagem() { return imagem; }

    public void setImagem(String imagem) { imagem = imagem; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { titulo = titulo; }

    public String getPalestrante() { return palestrante; }

    public void setPalestrante(String palestrante) { palestrante = palestrante; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { descricao = descricao; }

    public String getData() { return data; }

    public void setData(String data) { data = data; }

    public String getHora() { return hora; }

    public void setHora(String hora) { hora = hora; }

    public int getQtdVagasDisponiveis() { return qtdVagasDisponiveis; }

    public void setQtdVagasDisponiveis(int qtdVagasDisponiveis) { qtdVagasDisponiveis = qtdVagasDisponiveis; }

    public String getEmailCadastrado() { return emailCadastrado; }

    public void setEmailCadastrado(String emailCadastrado) { emailCadastrado = emailCadastrado; }

    public String getDataInscricao() { return dataInscricao; }

    public void setDataInscricao(String dataInscricao) { dataInscricao = dataInscricao; }

    public String getHoraInscricao() { return horaInscricao; }

    public void setHoraInscricao(String horaInscricao) { horaInscricao = horaInscricao; }


}
