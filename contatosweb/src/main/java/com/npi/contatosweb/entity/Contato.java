package com.npi.contatosweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CONTATOS")
public class Contato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;

    @NotBlank
    @Size(min = 3, max = 30)
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 12)
    private String telefone;

    private String imagem;

    @Column(length = 300)
    private String sobre;

    @ManyToOne
    private Usuario usuario;

    // GETTERS N SETTERS
    public Long getcId() {
        return cId;
    }
    public void setcId(Long cId) {
        this.cId = cId;
    }

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

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSobre() {
        return sobre;
    }
    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
