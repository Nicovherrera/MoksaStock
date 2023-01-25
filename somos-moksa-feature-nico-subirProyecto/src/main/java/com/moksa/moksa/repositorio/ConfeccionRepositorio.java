package com.moksa.moksa.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moksa.moksa.entidades.Confeccion;
import com.moksa.moksa.entidades.Corte;

@Repository
public interface ConfeccionRepositorio extends JpaRepository <Confeccion, String> {

	@Query("SELECT f FROM Confeccion f WHERE f.estadoCcion=1 ORDER BY f.fechaAltaCcion DESC")
	public List <Confeccion> ordenDateConfeccion ();
	
	@Query("SELECT f FROM Confeccion f WHERE f.estadoCcion=1 AND f.nombreProveedroCcion= :nombreProveedroCcion")
	public Confeccion ultimaConfeccion (@Param("nombreProveedroCcion") String nombreProveedroCcion);
	
	@Query("SELECT f FROM Confeccion f WHERE f.id= :id")
    public Confeccion buscarConfeccionxId (@Param("id") String Id);
	
	@Query("SELECT f FROM Confeccion f WHERE f.nombreProveedroCcion= :nombreProveedroCcion ORDER BY f.fechaAltaCcion DESC")
    public List<Confeccion> buscarCcionxNombre (@Param("nombreProveedroCcion") String nombreProveedroCcion);
}
