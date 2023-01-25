package com.moksa.moksa.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moksa.moksa.entidades.Avio;
import com.moksa.moksa.entidades.Tela;

@Repository
public interface AvioRepositorio extends JpaRepository <Avio, String> {

	@Query("SELECT a FROM Avio a WHERE a.estadoA=1 ORDER BY a.fechaAltaA DESC")
	public List <Avio> ordenDateAvio ();
	
	@Query("SELECT a FROM Avio a WHERE a.nombreAvio= :nombreAvio ORDER BY a.fechaAltaA DESC")
    public List<Avio> buscarAvioxNombre (@Param("nombreAvio") String nombreAvio);
	
	@Query("SELECT a FROM Avio a WHERE a.id= :id")
    public Avio buscarxId (@Param("id") String id);
}
