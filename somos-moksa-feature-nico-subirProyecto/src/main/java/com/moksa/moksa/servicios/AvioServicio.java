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
import com.moksa.moksa.entidades.Tela;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.repositorio.AvioRepositorio;

@Service
public class AvioServicio {

	@Autowired
	AvioRepositorio avioRepo;

	public void registroAvio(String nombreAvio, String tipoAvio, Float precio, String proveedor, Integer cantidadCom)
			throws ErrorServicio, ParseException {

		validar(nombreAvio, tipoAvio, precio, proveedor, cantidadCom);

		Avio avio = new Avio();

		avio.setFechaAltaA(new Date());
		avio.setNombreAvio(nombreAvio.toUpperCase());
		avio.setProveedor(proveedor.toUpperCase());
		avio.setCantidadCom(cantidadCom);
		avio.setPrecio(precio);
		avio.setTipoAvio(tipoAvio.toUpperCase());
		avio.setStockA(stockAvio(cantidadCom, nombreAvio));
		avio.setPrecioAnt(precioAvAnt(nombreAvio));
		avio.setFechaFactAnt(fechaUltimaCompraA(nombreAvio));
		avio.setInflacionA(inflacionAvio(avio));
		avio.setEstadoA(true);

		avioRepo.save(avio);

	}

	public Float precioAvAnt(String nombreAvio) {

		List<Avio> avioL = avioRepo.buscarAvioxNombre(nombreAvio);

		Integer precioA = 0;

		for (Avio avio : avioL) {

			if (avio.getFechaAltaA().before(new Date())) {
				avio.setPrecioAnt(avio.getPrecio() / avio.getCantidadCom());
			}
			return avio.getPrecioAnt();
		}
		return (float) precioA;
	}

	// Control de Stock
	public Integer stockAvio(Integer cantidadCom, String nombreAvio) throws ErrorServicio {

		List<Avio> listaAvio = avioRepo.buscarAvioxNombre(nombreAvio);

		Integer stockA = 0;

		for (Avio avio : listaAvio) {

			stockA = stockA + avio.getCantidadCom();

			if (avio.getFechaAltaA().before(Date.from(Instant.now()))) {
				avio.setEstadoA(false);
			}
			System.out.println(stockA);
		}

		stockA = stockA + cantidadCom;

		return stockA;
	}

	public Date fechaUltimaCompraA(String nombreAvio) {

		List<Avio> avioL = avioRepo.buscarAvioxNombre(nombreAvio);

		for (Avio avio : avioL) {

			if (avio.getFechaAltaA().before(new Date())) {
				avio.setFechaFactAnt(avio.getFechaAltaA());
				avio.setPrecioAnt(avio.getPrecio() / avio.getCantidadCom());
			}
			return avio.getFechaAltaA();
		}
		return new Date();
	}

	public Float inflacionAvio(Avio aviof) {

		SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd");

		String fechaUlti = d1.format(aviof.getFechaFactAnt());
		String fechaActual = d2.format(new Date());

		LocalDate fuc = LocalDate.parse(fechaUlti, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate fac = LocalDate.parse(fechaActual, DateTimeFormatter.ISO_LOCAL_DATE);

		long dif = ChronoUnit.DAYS.between(fuc, fac);

		Float precioUnit = (aviof.getPrecio() / aviof.getCantidadCom());

		Float variacion = ((aviof.getPrecioAnt() / precioUnit) - 1) * -100;

		if (dif != 0) {
			float inflacion = (variacion / dif) * 30;
			return inflacion;
		} else {
			return variacion;
		}
	}

	@Transactional // Este metodo modifica avio
	public void modificarAvio(String id, String nombreAvio, String tipoAvio, Float precio, String proveedor,
			Integer cantComMod) throws ErrorServicio {

		Avio avioM = avioRepo.findById(id).get();

		avioM.setFechaAltaA(Date.from(Instant.now()));
		avioM.setProveedor(proveedor.toUpperCase());
		avioM.setNombreAvio(nombreAvio.toUpperCase());
		Integer stockAnt = avioM.getStockA() - avioM.getCantidadCom();
		avioM.setCantidadCom(cantComMod);
		avioM.setPrecio(precio);
		avioM.setStockA(stockAnt + cantComMod);

		avioRepo.save(avioM);
	}

	public Avio GetOneId(String id) {

		Avio avioM = avioRepo.findById(id).get();

		return avioM;

	}

	public void eliminarAvio(String id) {

		avioRepo.deleteById(id);
	}

	public void validar(String nombreAvio, String tipoAvio, Float precio, String proveedor, Integer cantidadCom)
			throws ErrorServicio {

		if (nombreAvio.isEmpty() || nombreAvio == null) {
			throw new ErrorServicio("El nombre del avio no puede estar vacio o nulo");
		} else {
			nombreAvio = nombreAvio.toUpperCase();
		}

		if (tipoAvio.isEmpty() || tipoAvio == null) {
			throw new ErrorServicio("El tipo de avio no puede estar vacio o nulo");
		} else {
			tipoAvio = tipoAvio.toUpperCase();
		}

		if (precio == null || precio < 1) {
			throw new ErrorServicio("El precio del avio no puede ser nulo o menor que 1");
		}

		if (proveedor.isEmpty() || proveedor == null) {
			throw new ErrorServicio("El proveedor no puede estar vacio o nulo");
		} else {
			proveedor = proveedor.toUpperCase();
		}

		if (cantidadCom == null || cantidadCom < 1) {
			throw new ErrorServicio("La cantidad comprada no puede ser nula o menor que 1");
		}

	}

	public List listaAvios() {

		return avioRepo.ordenDateAvio();
	}

	public List listaAviosxNmbre(String nombreAvio) {

		return avioRepo.buscarAvioxNombre(nombreAvio);
	}

}
