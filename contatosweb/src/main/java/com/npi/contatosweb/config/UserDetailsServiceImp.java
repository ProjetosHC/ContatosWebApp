package com.npi.contatosweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.npi.contatosweb.entity.Usuario;
import com.npi.contatosweb.repository.UsuarioRepositorio;

public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio uRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = uRepo.getUserByUserName(username);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(usuario);

        return customUserDetails;
    }
    
}
