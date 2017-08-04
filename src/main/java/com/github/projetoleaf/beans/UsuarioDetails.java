package com.github.projetoleaf.beans;

import java.io.Serializable;

public class UsuarioDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String cpf;
    private String remoteAddress;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

}
