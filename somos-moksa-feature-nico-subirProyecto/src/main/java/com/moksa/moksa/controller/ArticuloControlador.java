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
import org.springframework.web.multipart.MultipartFile;

import com.moksa.moksa.entidades.Articulo;
import com.moksa.moksa.entidades.Avio;
import com.moksa.moksa.entidades.Confeccion;
import com.moksa.moksa.entidades.Corte;
import com.moksa.moksa.entidades.Foto;
import com.moksa.moksa.entidades.Tela;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.repositorio.ArticuloRepositorio;
import com.moksa.moksa.repositorio.AvioRepositorio;
import com.moksa.moksa.repositorio.ConfeccionRepositorio;
import com.moksa.moksa.repositorio.CorteRepositorio;
import com.moksa.moksa.repositorio.TelaRepositorio;
import com.moksa.moksa.servicios.ArticuloServicio;

@PreAuthorize("hasAnyRole('ADMIN' , 'USUARIO')")
@Controller
@RequestMapping("/")
public class ArticuloControlador {
	
	@Autowired
	private AvioRepositorio avioRepo;
	
	@Autowired
	private CorteRepositorio corteRepo;
	
	@Autowired
	private ConfeccionRepositorio confeRepo;
	
	@Autowired
	private TelaRepositorio telaRepo;
	
	@Autowired
	private ArticuloServicio articuloS;
	
	@Autowired
	private ArticuloRepositorio articuloRepo;
	
	@GetMapping("/prendas")
	public String articuloRegistro (ModelMap modelo) {
		
		List <Confeccion> listaConf = confeRepo.ordenDateConfeccion();
		List <Corte> listaCte = corteRepo.ordenDateCorte();
		List <Tela> listaT = telaRepo.ordenDate();
		List<Avio> listaA = avioRepo.ordenDateAvio();
		List<Articulo>articuloL= articuloRepo.findAll();
	
		modelo.addAttribute("listaConf", listaConf);
		modelo.addAttribute("listaCte", listaCte);
		modelo.addAttribute("listaT", listaT);
		modelo.addAttribute("listaA", listaA);
		modelo.addAttribute("listaArt", articuloL);
		
		return "prendas.html";
	}
	
	@GetMapping("/articuloRegistro/{id}")
	public String eliminarArticulo (ModelMap modelo, @PathVariable String id) {
		
		articuloS.eliminarPrenda(id);
		
		return "redirect:/prendas#lista";
	}
	
	@PostMapping("/articuloRegistrado")
	public String articuloRegistrado (@RequestParam String nombrePrenda, @RequestParam Double precio, @RequestParam String telaId, @RequestParam List avioId, @RequestParam String corteId, @RequestParam String confId, @RequestParam Float consumo, MultipartFile archivo) throws ErrorServicio {
		
	
		
		
		if (archivo.getContentType().equalsIgnoreCase("application/octet-stream")) {
			
			archivo=null;
		}
		
		
		articuloS.cargaArticulo(nombrePrenda, precio, telaId, avioId, corteId, confId, consumo, archivo);
		
		
		return "redirect:/prendas";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle (ModelMap mapaArt, @PathVariable String id) {
		
		Articulo artSelect = articuloS.getOne(id);
		
		mapaArt.addAttribute("artSelect" , artSelect);
		
		mapaArt.put("lAvio", artSelect.getAvio());

		
		return "detalle.html";
	}
	
	@GetMapping("/modificarPrenda/{id}")
	public String modificarPrenda (ModelMap mapaArt, @PathVariable String id) throws ErrorServicio {
		
		Articulo artSelect = articuloS.getOne(id);
		
		mapaArt.addAttribute("pSelect" , artSelect);
		mapaArt.put("lAvio", artSelect.getAvio());
		
		Confeccion listaConf = confeRepo.ultimaConfeccion(artSelect.getConfeccion().getNombreProveedroCcion());
		Corte listaCte = corteRepo.ultimoCorte(artSelect.getCorte().getNombreProveedroCte());
		Tela ultimaT = telaRepo.ultimaTelaCargada(artSelect.getTela().getNombreTela());
		List<Avio> listaA = artSelect.getAvio();
		
	
		mapaArt.put("listaConf", listaConf);
		mapaArt.put("listaCte", listaCte);
		mapaArt.put("ultimaT", ultimaT);
		mapaArt.addAttribute("listaA", listaA);
		
		return "modificarPrendas.html";
	}
	
	@PostMapping("/prendaModificada/{id}")
	public String prendaModificada (ModelMap mapaArt, @PathVariable String id, String nombrePrenda, Double precio, String telaArt, String corteArt, String confeArt, Float consumo, MultipartFile archivo) throws ErrorServicio {
		
		System.out.println("Avios en controlador: ");
		
		articuloS.modicarPrenda(id, nombrePrenda, precio, telaArt, corteArt, confeArt, consumo, archivo);
		
		return "redirect:/prendas#lista";
	}
	

}
