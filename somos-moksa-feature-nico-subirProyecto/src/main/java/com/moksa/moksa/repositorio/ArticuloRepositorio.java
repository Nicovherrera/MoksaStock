package com.moksa.moksa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moksa.moksa.entidades.Articulo;

public interface ArticuloRepositorio extends JpaRepository <Articulo, String> {

}
