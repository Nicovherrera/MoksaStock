package com.moksa.moksa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moksa.moksa.entidades.Avio;
import com.moksa.moksa.entidades.Corte;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.repositorio.CorteRepositorio;
import com.moksa.moksa.servicios.AvioServicio;
import com.moksa.moksa.servicios.CorteServicio;

@PreAuthorize("hasAnyRole('ADMIN' , 'USUARIO')")
@Controller
@RequestMapping("/")
public class CorteControlador {
	
	@Autowired
	CorteServicio corteServicio;
	
	@Autowired
	CorteRepositorio corteRepo;

	@GetMapping("/corte") // MUESTRA LA LISTA DE PROVEEDORES CARGADOS POR ULTIMA VEZ
	public String corte (ModelMap mapaC) {
		
		List <Corte> corteL = corteRepo.ordenDateCorte();
		
		mapaC.addAttribute("corteL", corteL);

		return "corte.html"; 
	}
	
	@PostMapping("/cargarCorte") // PARA CARGAR UN REGISTRO DE PROVEEDOR DE CORTE
	public String registroCorte (ModelMap mapaA , String nombreProveedroCte, Double precio ) throws ErrorServicio {
		
		corteServicio.registroCorte(nombreProveedroCte, precio);
		
		return "redirect:/corte#carga";
	}
	
	@GetMapping ("/modificarCorte/{id}") // MUESTR LOS DATOS DEL PROVEEDOR DE CORTE A MODIFICAR
	public String modificarCorte (ModelMap corteM, @PathVariable String id) {
		
		corteM.put("corteM", corteServicio.GetOneIdC(id));
		
		return "modificarCorte.html";
	}
	
	@PostMapping ("/corteModificado") // PARA MODIFICAR EL ULTIMO REGISTRO DE PROVEEDOR DE CORTE
	public String avioModificado (@RequestParam String id, @RequestParam String nombreProveedroCte, @RequestParam Double precio) throws ErrorServicio {
		
		corteServicio.modificarCorte(id, nombreProveedroCte, precio);
		
		return "redirect:/corte#lista";
	}
	
	@GetMapping("/eliminarCorte/{id}") // ELIMINAR EL ULTIMO REGISTRO DEL PRECIO DE CORTE
	public String eliminarTela(@PathVariable String id) {
		
		corteServicio.eliminarCorte(id);
		
	return "redirect:/corte#lista";
	}
	
	@GetMapping("/corte/{nombreProveedroCte}")//METODO PARA MOSTRAR EL HISTORIAL DE PRECIOS DE UN SOLO PROVEEDOR
	public String listaCortePorNombre(ModelMap mapaC, @PathVariable String nombreProveedroCte) {

		List<Corte> ListaDeCortexNombre = corteServicio.listaCortesxNombre(nombreProveedroCte);
		
		mapaC.addAttribute("nombreCte", ListaDeCortexNombre);
		
		return "nombreCorte.html";
				
	}

}
