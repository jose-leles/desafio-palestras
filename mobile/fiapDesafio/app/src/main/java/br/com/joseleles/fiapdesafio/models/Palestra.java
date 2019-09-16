package br.com.joseleles.fiapdesafio.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Palestra implements Parcelable {

    private int codigo;
    private int codigoTipoCategoria;
    private String imagem;
    private String titulo;
    private String palestrante;
    private String descricao;
    private String data;
    private String hora;
    private int qtdVagasDisponiveis;

    //when listed by a user, must have this atributes on it
    private boolean emailCadastrado;

    private String dataInscricao;

    private String horaInscricao;


    public Palestra(){}

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
        emailCadastrado= source.readInt() == 1;
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
        dest.writeInt(emailCadastrado?1:0);
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

    public void setImagem(String imagem) { this.imagem = imagem; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getPalestrante() { return palestrante; }

    public void setPalestrante(String palestrante) { this.palestrante = palestrante; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getHora() { return hora; }

    public void setHora(String hora) { this.hora = hora; }

    public int getQtdVagasDisponiveis() { return qtdVagasDisponiveis; }

    public void setQtdVagasDisponiveis(int qtdVagasDisponiveis) { this.qtdVagasDisponiveis = qtdVagasDisponiveis; }

    public boolean getEmailCadastrado() { return emailCadastrado; }

    public void setEmailCadastrado(boolean emailCadastrado) { this.emailCadastrado = emailCadastrado; }

    public String getDataInscricao() { return dataInscricao; }

    public void setDataInscricao(String dataInscricao) { this.dataInscricao = dataInscricao; }

    public String getHoraInscricao() { return horaInscricao; }

    public void setHoraInscricao(String horaInscricao) { this.horaInscricao = horaInscricao; }


}
