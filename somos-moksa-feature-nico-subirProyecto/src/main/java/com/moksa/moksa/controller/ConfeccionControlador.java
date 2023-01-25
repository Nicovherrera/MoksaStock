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
import com.moksa.moksa.entidades.Confeccion;
import com.moksa.moksa.entidades.Corte;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.servicios.AvioServicio;
import com.moksa.moksa.servicios.ConfeccionServicio;

@PreAuthorize("hasAnyRole('ADMIN' , 'USUARIO')")
@Controller
@RequestMapping("/")
public class ConfeccionControlador {
	
	@Autowired
	ConfeccionServicio servicioConf;
	
	
	@GetMapping("/confeccion")
	public String confeccion (ModelMap mapaCcion) {
		
		 List <Confeccion> listaC = servicioConf.listaConf();
		
		 mapaCcion.addAttribute("listConf",listaC);
		 
		return "confeccion.html"; 
	}
	
	@PostMapping("/cargarConfeccion")
	public String registroConfeccion (ModelMap mapaA, String nombreProveedroCcion, Double precio) throws ErrorServicio {
		
		servicioConf.registroConfeccion(nombreProveedroCcion, precio);
		
		return "redirect:/confeccion#lista";
	}
	
	@GetMapping("/eliminarConfeccion/{id}")
	public String eliminarConfeccion (@PathVariable String id) {
		
		servicioConf.eliminarConfeccion(id);
		
		return "redirect:/confeccion#lista";
	}
	
	@GetMapping("/modificarConfeccion/{id}")
	public String modificarConf (@PathVariable String id, ModelMap mapaC ) {
		
		mapaC.addAttribute("mapaC", servicioConf.getOneId(id));
		
		return "confModificar.html";
	}
	
	@PostMapping("/ConfModificado")
	public String confModificado (@RequestParam String id,@RequestParam String nombreProveedroCcion, @RequestParam Double precio) throws ErrorServicio {
	
		servicioConf.modificarConf(id, nombreProveedroCcion, precio);
		
		return "redirect:/confeccion#lista";
	}
	
	@GetMapping("/confeccion/{nombreProveedroCcion}")//METODO PARA MOSTRAR EL HISTORIAL DE PRECIOS DE UN SOLO PROVEEDOR
	public String listaCcionPorNombre(ModelMap mapaCcion, @PathVariable String nombreProveedroCcion) {

		List<Confeccion> ListaDeCcionxNombre = servicioConf.listaCcionxNombre(nombreProveedroCcion);
		
		mapaCcion.addAttribute("nombreCcion", ListaDeCcionxNombre);
		
		return "nombreCcion.html";
				
	}

}
