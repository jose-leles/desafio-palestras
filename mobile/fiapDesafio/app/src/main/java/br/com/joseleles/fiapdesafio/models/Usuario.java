package br.com.joseleles.fiapdesafio.models;

public class Usuario {
    private int codigo;
    private String nome;
    private String email;
    private String empresa;
    private String cargo;

    public Usuario(){

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
}
