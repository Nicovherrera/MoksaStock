package com.moksa.moksa.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moksa.moksa.entidades.Avio;
import com.moksa.moksa.entidades.Corte;

@Repository
public interface CorteRepositorio extends JpaRepository <Corte, String> {

	@Query("SELECT c FROM Corte c WHERE c.estadoCte=1 ORDER BY c.fechaAltaCt DESC")
	   public List <Corte> ordenDateCorte ();
	
	@Query("SELECT c FROM Corte c WHERE c.estadoCte=1 AND c.nombreProveedroCte= :nombreProveedroCte")
	   public Corte ultimoCorte (@Param("nombreProveedroCte") String nombreProveedroCte);
	
	@Query("SELECT c FROM Corte c WHERE c.nombreProveedroCte= :nombreProveedroCte ORDER BY c.fechaAltaCt DESC")
    public List<Corte> buscarCortexNombre (@Param("nombreProveedroCte") String nombreProveedroCte);
	
	@Query("SELECT c FROM Corte c WHERE c.id= :id")
    public Corte buscarCortexId (@Param("id") String Id);
}
