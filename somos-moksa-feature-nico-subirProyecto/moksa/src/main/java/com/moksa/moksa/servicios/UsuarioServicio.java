package com.moksa.moksa.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.moksa.moksa.entidades.Usuario;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.repositorio.UsuarioRepositorio;

import enumeraciones.Rol;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Service
public class UsuarioServicio implements UserDetailsService  {

	@Autowired (required=true)
	private UsuarioRepositorio usuarioRepositorio;

	public void validar (String nombre, String apellido, Integer dni, String email, String contrasenia, MultipartFile archivo) throws ErrorServicio {
		
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("El nombre del usuario no puede ser null");
		}

		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio("El apellido del usuario no puede ser null");
		}

		if (dni == null) {
			throw new ErrorServicio("El DNI del usuario no puede ser null");
		}

		if (email == null || email.isEmpty()) {
			throw new ErrorServicio("El email del usuario no puede ser null");
		}
		if (contrasenia == null || contrasenia.isEmpty()) {
			throw new ErrorServicio("La contraseña del usuario no puede ser null");
		}
		
		//if (archivo == null || archivo.isEmpty()) {
			//throw new ErrorServicio("El archivo del usuario no puede ser null");
		//}
		
		}
	
	
	@Transactional 
	public Usuario crearUsuario(String nombre, String apellido, Integer dni, String email, String contrasenia,  MultipartFile archivo) throws ErrorServicio {
		
		validar (nombre, apellido, dni, email, contrasenia, archivo);
		
		Usuario usuario = new Usuario();
		
	usuario.setFechaAlta(new Date());
	usuario.setNombre(nombre);
	usuario.setApellido(apellido);
	usuario.setDni(dni);
	usuario.setMail(email);
	String encriptada = new BCryptPasswordEncoder().encode(contrasenia);
    usuario.setContrasenia(encriptada);
    usuario.setEstado(false);
    usuario.setRol(Rol.USUARIO);
    
	usuarioRepositorio.save(usuario);
	
	return usuario;
	
		
	}
	
	@Transactional
	public void modificarUsuario(String id,String nombre, String apellido, Integer dni, String mail, String contrasenia, MultipartFile archivo)
			throws ErrorServicio {

		validar (nombre, apellido, dni, mail, contrasenia, archivo);

		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

		if (respuesta.isPresent()) {

			Usuario usuario = respuesta.get();

			usuario.setId(id);
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setDni(dni);
			usuario.setMail(mail);
		
			
			
			usuarioRepositorio.save(usuario);

		} else {
			throw new ErrorServicio("No se encontro el usuario solicitado");
		}
	}
	
	@Transactional
	public void eliminarUsuario(String id) throws ErrorServicio {
		Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
	
		if (respuesta.isPresent()) {
			
			Usuario usuario = respuesta.get();
			
			usuarioRepositorio.delete(usuario);
			
		}else {
			throw new ErrorServicio("El Usuario no existe");
			
		}
		
	}
	
	public List<Usuario> listarUsuario(){
		
		return usuarioRepositorio.findAll();
	}
	
	private Usuario validarUsuario(Usuario usuario, String contrasenia) throws ErrorServicio {
		
		if(usuario.getContrasenia().equals(contrasenia)) {
			
		}else {
			
			throw new ErrorServicio("No Existe el Usuario.");
				
		}
		return usuario;
	}
	
	@Transactional 
	public Usuario findUserByEmail(String email, String contrasenia) throws ErrorServicio {
		
		Optional<Usuario> respuesta = usuarioRepositorio.buscarPorEmail(email);
		if(respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			return validarUsuario(usuario,contrasenia); 
	}else {
		
		throw new ErrorServicio("El email del usuario no existe. Primero debe registrarse");
	}
		
	}


	@Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarXmail(mail);
        
        if (usuario !=null){
            
            List <GrantedAuthority> permisos = new ArrayList<>();
            
            //Creo una lista de permisos! 
            GrantedAuthority p1 = new SimpleGrantedAuthority ("ROLE_"+ usuario.getRol());
            permisos.add(p1);
            
            
           // Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            User user = new User(usuario.getMail(), usuario.getContrasenia(), permisos);
            System.out.println(user);
            return user;
        }else{
         return null;
        }
	}
	
	
}

