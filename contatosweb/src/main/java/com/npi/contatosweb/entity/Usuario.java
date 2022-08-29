package com.npi.contatosweb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USUARIOS")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Campo obrigatório!")
    @Size(min = 3, max = 30, message = "Digite entre 3 a 30 caracteres")
    private String nome;
    
    @NotBlank(message = "Campo obrigatório!")
    @Column(unique = true)
    private String email;
    
    @NotBlank(message = "Campo obrigatório!")
    private String senha;
    
    private String imagemUrl;

    private String role;
    
    private boolean ativo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<Contato> contatos = new ArrayList<>();

    // CONSTRUCTOR
    public Usuario() {
        super();
    }

    // GETTERS N SETTERS
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Contato> getContatos() {
        return contatos;
    }
    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    @Override
    public String toString() {
        return "Usuario [ativo=" + ativo + ", contatos=" + contatos + ", email=" + email + ", imagemUrl=" + imagemUrl
                + ", nome=" + nome + ", senha=" + senha + ", id=" + id + "]";
    }
    
}
