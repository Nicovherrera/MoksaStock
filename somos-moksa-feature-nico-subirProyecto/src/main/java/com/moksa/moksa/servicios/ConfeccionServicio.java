package com.moksa.moksa.servicios;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.moksa.moksa.entidades.Confeccion;
import com.moksa.moksa.entidades.Corte;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.repositorio.ConfeccionRepositorio;
import com.moksa.moksa.repositorio.CorteRepositorio;

@Service
public class ConfeccionServicio {
	
	@Autowired
	ConfeccionRepositorio confeccionRepo;
	
	public void registroConfeccion (String nombreProveedroCcion, Double precio) throws ErrorServicio {
		
		validarC (nombreProveedroCcion, precio);
		
		Confeccion conf = new Confeccion();
		
		conf.setFechaAltaCcion(Date.from(Instant.now()));
		conf.setFechaAltaAntCcion(fechaUltimaCompraCcion(nombreProveedroCcion));
		conf.setNombreProveedroCcion(nombreProveedroCcion.toUpperCase());
		conf.setPrecio(precio);
		conf.setPrecioCompAntCcion(precioCcionAnt(nombreProveedroCcion));
		conf.setInflacionCcion(inflacionCcion(conf));
		conf.setEstadoCcion(ultimaCargaC(nombreProveedroCcion));
		
		confeccionRepo.save(conf);
		
		
	}
	
	public Double precioCcionAnt (String nombreProveedroCcion) {
		
	    List<Confeccion> confeccionL = confeccionRepo.buscarCcionxNombre(nombreProveedroCcion);
		
	    Double precioA=0.0;
	    
	    for (Confeccion confeccion : confeccionL) {
            
	    	if (confeccion.getFechaAltaCcion().before(new Date())) {
	    		confeccion.setPrecioCompAntCcion(confeccion.getPrecio());
	          }return confeccion.getPrecioCompAntCcion();
	         }
	    return precioA;
	}
	
	public Date fechaUltimaCompraCcion (String nombreProveedroCcion) {
		
		List<Confeccion> confL = confeccionRepo.buscarCcionxNombre(nombreProveedroCcion);
		
	    for (Confeccion conf : confL) {
            
	    	if (conf.getFechaAltaCcion().before(new Date())) {
	           conf.setFechaAltaAntCcion(conf.getFechaAltaCcion());
	           conf.setPrecioCompAntCcion(conf.getPrecio());
	          }return conf.getFechaAltaAntCcion();
	         }
		return new Date();
	}
	
	public Double inflacionCcion(Confeccion conf){
		
		SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd");
		
		String fechaUlti=d1.format(conf.getFechaAltaAntCcion());
		String fechaActual=d2.format(new Date());
		
		LocalDate fuc = LocalDate.parse(fechaUlti, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate fac = LocalDate.parse(fechaActual, DateTimeFormatter.ISO_LOCAL_DATE);
		
		long dif = ChronoUnit.DAYS.between(fuc, fac);
		
		Double variacion= (1-(conf.getPrecioCompAntCcion()/conf.getPrecio()))*100;
		
		if (dif!=0) {
		Double inflacion=((variacion/dif)*30);
		return inflacion;
		}else {	
			return variacion;
		}
	}
	
	public void validarC (String nombreProveedroCcion, Double precio)throws ErrorServicio {
		
		if (nombreProveedroCcion.isEmpty() || nombreProveedroCcion == null) {
			throw new ErrorServicio ("El nombre del Proveedor no puede estar vacio o nulo");
			}
		
		if (precio == null || precio<1) {
			throw new ErrorServicio ("El preio de la confección no puede ser nulo o menor que 1");
			}
		
	}
	
	public List listaConf () {
		
		return confeccionRepo.ordenDateConfeccion();
	}
	
	public Confeccion getOneId (String id) {
		return confeccionRepo.findById(id).get();
	}
	
	@Transactional //Este metodo modifica la confeccion
	public void modificarConf (String id, String nombreProveedroCcion, Double precio) throws ErrorServicio {
	
	Confeccion confeccionM = confeccionRepo.findById(id).get();
		
	    confeccionM.setFechaAltaCcion(Date.from(Instant.now()));
	    confeccionM.setNombreProveedroCcion(nombreProveedroCcion.toUpperCase());
	    confeccionM.setPrecio(precio);
	    confeccionM.setEstadoCcion(true);
		
		confeccionRepo.save(confeccionM);
	}
	
	@Transactional
	public void eliminarConfeccion (String id) {
		
		confeccionRepo.deleteById(id);
	}
	
	public List listaCcionxNombre (String nombreProveedroCcion) {
		
		return confeccionRepo.buscarCcionxNombre(nombreProveedroCcion);
	}
	
	public Boolean ultimaCargaC (String nombreProveedroCcion) throws ErrorServicio {
		
		List <Confeccion> listaCcion = confeccionRepo.buscarCcionxNombre(nombreProveedroCcion);
		
		Boolean estadoC=true;
		
		for (Confeccion ccionL : listaCcion) {
            
          if (ccionL.getFechaAltaCcion().before(Date.from(Instant.now()))) {
          ccionL.setEstadoCcion(false);
          }
         }
		return estadoC;
	}
	
	
	

}
