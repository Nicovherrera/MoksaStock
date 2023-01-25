package com.moksa.moksa.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moksa.moksa.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository <Usuario, String> {
	
	@Query(value = "SELECT a FROM Usuario a")
	List<Usuario> findAllusuarios();
	
	@Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
	Optional<Usuario> buscarPorEmail(@Param("mail") String mail);

	@Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
	Usuario buscarXmail(@Param("mail") String mail);

}
