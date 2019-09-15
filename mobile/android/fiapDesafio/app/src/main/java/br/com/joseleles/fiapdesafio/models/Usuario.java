package br.com.joseleles.fiapdesafio.models;

public class Usuario {
    private int Codigo;
    private String Nome;
    private String Email;
    private String Empresa;
    private String Cargo;

    public Usuario(){

    }

    public int getCodigo() {return Codigo;}

    public void setCodigo(int codigo) {Codigo = codigo;}

    public String getNome() {return Nome;}

    public void setNome(String nome) {Nome = nome;}

    public String getEmail() {return Email;}

    public void setEmail(String email) {Email = email;}

    public String getEmpresa() {return Empresa;}

    public void setEmpresa(String empresa) {Empresa = empresa;}

    public String getCargo() {return Cargo;}

    public void setCargo(String cargo) {Cargo = cargo;}
}
