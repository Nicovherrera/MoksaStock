package com.moksa.moksa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moksa.moksa.entidades.Foto;

@Repository
public interface FotoRepositorio extends JpaRepository <Foto, String>{

}
