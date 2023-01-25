package com.moksa.moksa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moksa.moksa.servicios.UsuarioServicio;


@Controller
@RequestMapping("/")
public class UsuarioControlador {
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	
//	@GetMapping("/usuario")
//	public String mostrarUsuario () {
//		return "registro.html"; 
//	}
	
	
	@GetMapping("/registro")
	public String registro () {
		
		return "registro.html";
	}
	
	@PostMapping("/registrarUsuario")
	public String registrarUsuario( @RequestParam String Nombre, @RequestParam String Apellido, @RequestParam Integer dni, @RequestParam String mail, @RequestParam String contrasenia) throws Exception {
	
		try {
		
		usuarioServicio.CrearUsuario(Nombre, Apellido, dni, mail, contrasenia, null);
		
		return "logueo.html";
		}catch (Exception e){
			e.getMessage();
			return "registro.html";
		}
	}
	
	 @GetMapping("/logueo")
	    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
	        if (error != null) {
	            model.put("error", "Usuario o clave incorrectos");
	        }
	        if (logout != null) {
	            model.put("logout", "Ha salido correctamente.");
	        }
	        return "logueo.html";
	    }
	
	

}
