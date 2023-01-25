package com.moksa.moksa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moksa.moksa.entidades.Articulo;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.servicios.ArticuloServicio;

@Controller
@RequestMapping("/foto")
public class FotoControlador {

		@Autowired
	    ArticuloServicio articuloS;
	    
	    @GetMapping("/articuloRegistro/{id}")
	    public ResponseEntity<byte[]> fotoArticulo (@PathVariable String id) throws ErrorServicio{
	        
	        Articulo arti = articuloS.getOne(id);
	        if(arti.getFoto()==null){
	            throw new ErrorServicio("El usuario no tiene una foto asignada");
	        }
	        byte[] foto = arti.getFoto().getContenido();
	        
	        HttpHeaders headers=new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_JPEG);
	        
	        return new ResponseEntity<>(foto, headers, HttpStatus.OK);
	    }
	
}
