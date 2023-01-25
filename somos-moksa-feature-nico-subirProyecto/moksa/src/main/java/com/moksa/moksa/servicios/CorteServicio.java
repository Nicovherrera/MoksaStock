package com.moksa.moksa.servicios;

import java.text.ParseException;
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

import com.moksa.moksa.entidades.Avio;
import com.moksa.moksa.entidades.Corte;
import com.moksa.moksa.entidades.Tela;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.repositorio.CorteRepositorio;

@Service
public class CorteServicio {
	
		@Autowired
		CorteRepositorio corteRepo;
	
		public void registroCorte (Date fecha, String nombreProveedroCte, Double precio) throws ErrorServicio {
		
		validar (nombreProveedroCte, precio);
		
		Corte corte = new Corte();
		
		corte.setFechaAltaCt(fecha);
		corte.setFechaCompAnteC(fechaUltimaCompraC(nombreProveedroCte, fecha));
		corte.setNombreProveedroCte(nombreProveedroCte.toUpperCase());
		corte.setPrecio(precio);
		corte.setPrecioCompAntC(precioCorteAnt(nombreProveedroCte));
		corte.setInflacionC(inflacionCorte(corte));
		corte.setEstadoCte(ultimaCarga(nombreProveedroCte));
		
		corteRepo.save(corte);
		
		
	}
		public Double precioCorteAnt (String nombreProveedroCte) {
			
		    List<Corte> corteL = corteRepo.buscarCortexNombre(nombreProveedroCte);
			
		    Double precioA=0.0;
		    
		    for (Corte corte : corteL) {
	            
		    	if (corte.getFechaAltaCt().before(new Date())) {
		           corte.setPrecioCompAntC(corte.getPrecio());
		          }return corte.getPrecioCompAntC();
		         }
		    return precioA;
		}
		
		public Date fechaUltimaCompraC (String nombreProveedroCte, Date fecha) {
			
			List<Corte> corteL = corteRepo.buscarCortexNombre(nombreProveedroCte);
			
		    for (Corte corte : corteL) {
	            
		    	if (corte.getFechaAltaCt().before(fecha)) {
		           corte.setFechaCompAnteC(corte.getFechaAltaCt());
		           corte.setPrecioCompAntC(corte.getPrecio());
		          }return corte.getFechaCompAnteC();
		         }
			return new Date();
		}
		
		public Double inflacionCorte(Corte corte){
			
			SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd");
			
			String fechaUlti=d1.format(corte.getFechaCompAnteC());
			String fechaActual=d2.format(corte.getFechaAltaCt());
			
			LocalDate fuc = LocalDate.parse(fechaUlti, DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate fac = LocalDate.parse(fechaActual, DateTimeFormatter.ISO_LOCAL_DATE);
			
			long dif = ChronoUnit.DAYS.between(fuc, fac);
			
			Double variacion= ((corte.getPrecioCompAntC()/corte.getPrecio())-1)*-100;
			
			
			
			if (dif!=0 && dif>0) {
			Double inflacion=((variacion/dif)*30);
			return inflacion;
			}else if (dif<=0){	
				return variacion-100;
			}else {
				return variacion;
			}
		}
		
		
	
		@Transactional //Este metodo modifica el corte
		public void modificarCorte (String id, String proveedor, Double precio) throws ErrorServicio {
		
		Corte corteM = corteRepo.findById(id).get();
			
		    corteM.setFechaAltaCt(Date.from(Instant.now()));
		    corteM.setNombreProveedroCte(proveedor.toUpperCase());
		    corteM.setPrecio(precio);
		    corteM.setEstadoCte(true);
			
			corteRepo.save(corteM);
		}
	
		public Corte GetOneIdC (String id) {
		
		Corte corteM = corteRepo.findById(id).get();
		
		return corteM;
		
	}
		
		@Transactional
		public void eliminarCorte (String id) {
			
			Corte corteEliminado=corteRepo.buscarCortexId(id);
			
			List<Corte>corteEstado=corteRepo.buscarCortexNombre(corteEliminado.getNombreProveedroCte());
			
			if(corteEstado.size()>1){
			Corte corteCambiado = corteEstado.get(1);
			corteCambiado.setEstadoCte(true);
			}
			corteRepo.deleteById(id);
		}
	
		public void validar (String nombreProveedroCte, Double precio)throws ErrorServicio {
		
		if (nombreProveedroCte.isEmpty() || nombreProveedroCte == null) {
			throw new ErrorServicio ("El nombre del avio no puede estar vacio o nulo");
			}
		
		if (precio == null || precio<1) {
			throw new ErrorServicio ("El preio del avio no puede ser nulo o menor que 1");
			}
		
	}
	
		public List listaCortes () {
		
		return corteRepo.ordenDateCorte();
	}
		public List listaCortesxNombre (String nombreProveedroCte) {
			
			return corteRepo.buscarCortexNombre(nombreProveedroCte);
		}
		
		public Boolean ultimaCarga (String nombreProveedroCte) throws ErrorServicio {
			
			List <Corte> listaCorte = corteRepo.buscarCortexNombre(nombreProveedroCte);
			
			Boolean estadoC=true;
			
			for (Corte corteL : listaCorte) {
	            
	          if (corteL.getFechaAltaCt().before(Date.from(Instant.now()))) {
	          corteL.setEstadoCte(false);
	          }
	         }
			return estadoC;
		}
		

}
