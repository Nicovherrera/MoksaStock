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

import com.moksa.moksa.entidades.Tela;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.servicios.TelaServicio;

@PreAuthorize("hasAnyRole('ADMIN' , 'USUARIO')")
@Controller
@RequestMapping ("/")
public class TelaControlador {

	@Autowired
	private TelaServicio telaServicio;
	
	@GetMapping("/tela") //Muestra la vista del formulario para cargar la compra de la tela y un listado de las telas ultimas telas compradas
	public String cargaTela(ModelMap mapaT) {

		List<Tela> stocKT = telaServicio.listaTela();
		
		mapaT.addAttribute("stockT", stocKT );

		return "tela.html"; 
				
	}
	
	@GetMapping("/tela/{nombreTela}") //Muestra un listado de telas agrupadas por nombre.
	public String listaTelaPorNombre(ModelMap mapaT, @PathVariable String nombreTela) {

		List<Tela> ListaDeTelasxNombre = telaServicio.listarTelaPorNombre(nombreTela);
		
		mapaT.addAttribute("nombreTelas", ListaDeTelasxNombre);
		
		return "nombreTelas.html";
				
	}
	
	@PostMapping("/cargarTela")//Este es el metodo para cargar el registro de una compra de tela
	public String registroTela (ModelMap mapa , @RequestParam String fecha, @RequestParam String nombreTela, Double cantidadCom, @RequestParam Double precioT, @RequestParam String proveedor, Double rinde) throws ErrorServicio, ParseException {
		
		try {
			SimpleDateFormat fechaFormateada = new SimpleDateFormat("yyyy-MM-dd");
			Date fechaCompra = fechaFormateada.parse(fecha);
			
			telaServicio.registroTela(fechaCompra, nombreTela, cantidadCom, precioT, proveedor, rinde);
			
			return "redirect:/tela#carga";
		
		}catch (ErrorServicio e) {
			
			mapa.put("errorT", e.getMessage());
			
			return "tela.html";
			}
	}
	
	@GetMapping ("/modificarTela/{id}")
	public String modificarTela (ModelMap telaM, @PathVariable String id) {
		
		telaM.put("telaM", telaServicio.GetOneId(id));
		
		return "telaModificar.html";
	}
	
	@PostMapping ("/telaModificada")
	public String telaModificada (@RequestParam String id, @RequestParam String proveedor, @RequestParam String nombreTela, @RequestParam Double cantidadCom, @RequestParam Double precioT, @RequestParam Double rinde) throws ErrorServicio {
		
		telaServicio.modificarTela(id, proveedor, nombreTela, cantidadCom, precioT, rinde);
		
		return "redirect:/tela#lista";
	}
	
	@GetMapping("/eliminarTela/{id}")
	public String eliminarTela(@PathVariable String id) {
		
		telaServicio.eliminarTela(id);
		
	return "redirect:/tela#lista";
	}
	
	
}
