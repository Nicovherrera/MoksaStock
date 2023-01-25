package com.moksa.moksa.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.moksa.moksa.entidades.Tela;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.servicios.AvioServicio;

@PreAuthorize("hasAnyRole('ADMIN' , 'USUARIO')")
@Controller
@RequestMapping("/")
public class AvioControlador {
	
	@Autowired
	private AvioServicio avioS;
	
	@GetMapping("/avios")
	public String avio (ModelMap mapaA) {
		
		List<Avio> avioL = avioS.listaAvios();
		
		mapaA.addAttribute("avioL", avioL);
		
		return "avios.html"; 
	}
	
	@GetMapping("/avios/{nombreAvio}")
	public String listaAvioPorNombre(ModelMap mapaT, @PathVariable String nombreAvio) {

		List<Avio> ListaDeAviosxNombre = avioS.listaAviosxNmbre(nombreAvio);
		
		mapaT.addAttribute("nombreAvios", ListaDeAviosxNombre);
		
		return "nombreAvios.html";
				
	}
	
	@PostMapping("/cargarAvio")
	public String registroAvio (ModelMap mapaA , String fecha, String nombreAvio, String tipoAvio, Float precio, String proveedor, Integer cantidadCom ) throws ErrorServicio, ParseException {
		
		try {
			SimpleDateFormat fechaFormateada = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaCompra = fechaFormateada.parse(fecha);
			
			avioS.registroAvio(fechaCompra, nombreAvio, tipoAvio, precio, proveedor, cantidadCom);
	
			return "redirect:/avios#lista";
		}catch (ErrorServicio e) {
			System.out.println("Ocurrio un error gato");
			mapaA.put("errorA", " Ocurrio un error, sos medio pelotudo. Completa todos los campos obligatorios huevón ");
			return "redirect:/avios#carga";
		}
		
	}
	
	@GetMapping ("/modificarAvio/{id}")
	public String modificarAvio (ModelMap avioM, @PathVariable String id) {
		
		avioM.put("avioM", avioS.GetOneId(id));
		
		
		return "modificarAvio.html";
	}
	
	@PostMapping ("/avioModificado/{id}")
	public String avioModificado (@PathVariable String id, @RequestParam String proveedor, @RequestParam String nombreAvio, @RequestParam Float precio, @RequestParam String tipoAvio, @RequestParam Integer cantidadCom) throws ErrorServicio {
		
		avioS.modificarAvio(id, nombreAvio, tipoAvio, precio, proveedor, cantidadCom);
		
		return "redirect:/avios#lista";
	}
	
	@GetMapping("/eliminarAvio/{id}")
	public String eliminarAvio(@PathVariable String id) {
		
		avioS.eliminarAvio(id);
		
	return "redirect:/avios#lista";
	}

}
