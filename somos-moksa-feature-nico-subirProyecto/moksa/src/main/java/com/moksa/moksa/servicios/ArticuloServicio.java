package com.moksa.moksa.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
public class ArticuloServicio {

	@Autowired
	private ArticuloRepositorio articuloRepo;

	@Autowired
	private TelaRepositorio telaRepo;

	@Autowired
	private AvioRepositorio avioRepo;

	@Autowired
	private CorteRepositorio corteRepo;

	@Autowired
	private ConfeccionRepositorio confeRepo;

	@Autowired
	FotoServicio fotoServicio;

	public void cargaArticulo(String nombrePrenda, Double precio, String telaId, List<String> avioId, String corteId,
			String confeId, Float consumo, MultipartFile archivo) throws ErrorServicio {

		Tela telaArt = telaRepo.buscarTelaxId(telaId);
		List<Avio> avioL = new ArrayList();
		Float costoAvio = (float) 0;
		for (String id : avioId) {
			Avio av = avioRepo.buscarxId(id);
			avioL.add(av);
			costoAvio += av.getPrecio() / av.getCantidadCom();

		}

		Corte corteArt = corteRepo.buscarCortexId(corteId);
		Confeccion confeArt = confeRepo.buscarConfeccionxId(confeId);

		validar(nombrePrenda);

		Articulo arti = new Articulo();

		arti.setFechaAlta(new Date());
		arti.setNombre(nombrePrenda.toUpperCase());
		arti.setPrecio(precio);
		arti.setTela(telaArt);
		arti.setAvio(avioL);
		arti.setCorte(corteArt);
		arti.setConfeccion(confeArt);
		arti.setConsumo(consumo);
		arti.setCostoPrenda(costoPrenda(arti, costoAvio));
		Foto foto = fotoServicio.guardarFoto(archivo);

		arti.setFoto(foto);
		arti.setEstado(true);
		arti.setCostoAvios(costoAvio);

		articuloRepo.save(arti);

	}

	public Float inflacionCompras(Articulo arti) {

		Float cantDias = (float) arti.getFechaAlta().compareTo(new Date());

		return cantDias;

	}

	public Float costoPrenda(Articulo arti, Float costoAvio) {

		Float costoPrenda = (float) (arti.getCorte().getPrecio() + costoAvio + arti.getConfeccion().getPrecio()
				+ ((arti.getTela().getPrecioT() / arti.getTela().getCantidadCom())) * arti.getConsumo());

		return costoPrenda;
	}

	public void validar(String nombre) throws ErrorServicio {

				if (nombre.isEmpty() || nombre == null) {
			throw new ErrorServicio("El nombre no puede ser nulo o estar vacío");
		}

	}

	@Transactional(readOnly = true)
	public Articulo getOne(String id) {
		return articuloRepo.getOne(id);
	}

	@Transactional
	public void eliminarPrenda(String id) {

		articuloRepo.deleteById(id);

	}

	public Articulo modicarPrenda(String id, String nombrePrenda, Double precio, String telaArt, String corteArt,
			String confeArt, Float consumo, MultipartFile archivo) throws ErrorServicio {

		Articulo prenda = articuloRepo.findById(id).get();

		prenda.setNombre(nombrePrenda.toUpperCase());
		prenda.setPrecio(precio);
		prenda.setTela(actuliazarTela(telaArt));
		prenda.setCorte(actuliazarCorte(corteArt));
		prenda.setConfeccion(actuliazarConf(confeArt));
		prenda.setConsumo(consumo);

		if (archivo != null) {
			Foto foto = fotoServicio.guardarFoto(archivo);
			prenda.setFoto(foto);
		}

		return articuloRepo.save(prenda);
	}

	public Tela actuliazarTela(String telaArt) {

		Tela telaPrenda = telaRepo.buscarTelaxId(telaArt);
		if (telaPrenda.getEstadoT() != true) {

			telaPrenda = telaRepo.ultimaTelaCargada(telaPrenda.getNombreTela());
		}

		return telaPrenda;
	}

	public Corte actuliazarCorte(String corteArt) {

		Corte cortePrenda = corteRepo.buscarCortexId(corteArt);
		if (cortePrenda.getEstadoCte() != true) {
			cortePrenda = corteRepo.ultimoCorte(cortePrenda.getNombreProveedroCte());
		}

		return cortePrenda;
	}

	public Confeccion actuliazarConf(String confeArt) {

		Confeccion confPrenda = confeRepo.buscarConfeccionxId(confeArt);
		confeRepo.buscarCcionxNombre(confPrenda.getNombreProveedroCcion());
		if (confPrenda.getEstadoCcion() != true) {
			confPrenda = confeRepo.ultimaConfeccion(confPrenda.getNombreProveedroCcion());
		}

		return confPrenda;
	}

}
