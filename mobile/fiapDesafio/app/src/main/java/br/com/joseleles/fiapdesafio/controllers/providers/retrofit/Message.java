package br.com.joseleles.fiapdesafio.controllers.providers.retrofit;

public class Message {
    private String message;
    private boolean sucesso;

    public Message(String message, boolean sucesso){
        this.message = message;
        this.sucesso = sucesso;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSucesso() {
        return sucesso;
    }
}
