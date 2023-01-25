package com.moksa.moksa.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.moksa.moksa.entidades.Avio;
import com.moksa.moksa.entidades.Tela;

public interface TelaRepositorio extends JpaRepository <Tela, String>{

	@Query("SELECT t FROM Tela t WHERE t.estadoT=1 ORDER BY t.fechaAlta DESC")
	 public List <Tela> ordenDate ();
	
	@Query("SELECT t FROM Tela t WHERE t.estadoT=1 AND t.nombreTela= :nombreTela")
	 public  Tela ultimaTelaCargada (@Param("nombreTela") String nombreTela);
	
	@Query("SELECT t FROM Tela t WHERE t.id= :id")
    public Tela buscarTelaxId (@Param("id") String Id);
	
	@Query("SELECT t FROM Tela t WHERE t.nombreTela= :nombreTela ORDER BY t.fechaAlta DESC")
    public List<Tela> buscarTelaxNombre (@Param("nombreTela") String nombreTela);
	
	@Query("SELECT t FROM Tela t WHERE t.nombreTela= :nombreTela ORDER BY t.fechaAlta DESC")
    public Tela buscarXnombre (@Param("nombreTela") String nombreTela);
	
}
