package com.npi.contatosweb.helper;

public class Mensagem {
    
    private String conteudo;
    private String tipo;

    public Mensagem(String conteudo, String tipo) {
        super();
        this.conteudo = conteudo;
        this.tipo = tipo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }    
    
}
