package com.npi.contatosweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.npi.contatosweb.entity.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    
    @Query("select u from Usuario u where u.email = :email")
    public Usuario getUserByUserName(@Param("email") String email);

    Optional<Usuario> findByEmail(String email);
}
