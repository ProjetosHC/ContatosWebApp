package com.npi.contatosweb.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.npi.contatosweb.entity.Usuario;

public class CustomUserDetails implements UserDetails {

    private Usuario usuario;

    public CustomUserDetails(Usuario usuario) {
        super();
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(usuario.getRole());

        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        
        return true;
    }

    @Override
    public boolean isEnabled() {
        
        return true;
    }
    
}
