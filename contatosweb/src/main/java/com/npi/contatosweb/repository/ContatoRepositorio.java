package com.npi.contatosweb.repository;

// import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.npi.contatosweb.entity.Contato;

@Repository
public interface ContatoRepositorio extends JpaRepository<Contato, Long> {
    
    @Query("from Contato as c where c.usuario.id = :usuarioId")
    public Page<Contato> findContactsByUser(@Param("usuarioId") Long usuarioId, Pageable pageable);
}
