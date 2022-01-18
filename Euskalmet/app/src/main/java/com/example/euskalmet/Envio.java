package com.example.euskalmet;

public class Envio {

    String usuario;
    Boolean login;

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public Envio(String usuario) {
        super();
        this.usuario = usuario;
    }

    public Envio() {
        // TODO Auto-generated constructor stub
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
