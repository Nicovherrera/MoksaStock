package com.moksa.moksa.servicios;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moksa.moksa.entidades.Articulo;
import com.moksa.moksa.entidades.Tela;
import com.moksa.moksa.errores.ErrorServicio;
import com.moksa.moksa.repositorio.TelaRepositorio;

@Service
public class TelaServicio {

	@Autowired
	private TelaRepositorio telaRepo;

	@Transactional
	public void registroTela(String nombreTela, Double cantidadCom, Double precioT, String proveedor, Double rinde)
			throws ErrorServicio, ParseException {

//		DecimalFormat df = new DecimalFormat("0.00"); 
//		Double precioTF = df.format(precioT);

		validar(proveedor, nombreTela, cantidadCom, precioT, rinde);

		Tela telar = new Tela();

		telar.setFechaAlta(new Date());
		telar.setNombreTela(nombreTela.toUpperCase());
		telar.setCantidadCom(cantidadCom);
		telar.setPrecioT(precioT);
		telar.setProveedor(proveedor.toUpperCase());
		telar.setStockT(stockTela(cantidadCom, nombreTela));
		telar.setFechaCompAnt(fechaUltimaCompra(nombreTela));
		telar.setPrecioCompraAnterior(precioTAnt(nombreTela));
		telar.setInflacion(inflacionTela(telar));
		telar.setEstadoT(Boolean.TRUE);

		telaRepo.save(telar);

	}

	public Double precioTAnt(String nombreTela) {

		List<Tela> telaL = telaRepo.buscarTelaxNombre(nombreTela);

		Double rinde = 0.00;

		for (Tela tela : telaL) {

			if (tela.getFechaAlta().before(new Date())) {
				tela.setPrecioCompraAnterior(tela.getPrecioT() / tela.getCantidadCom());
			}
			return tela.getPrecioCompraAnterior();
		}
		return rinde;
	}

	public Date fechaUltimaCompra(String nombreTela) {

		List<Tela> telaL = telaRepo.buscarTelaxNombre(nombreTela);

		for (Tela tela : telaL) {

			if (tela.getFechaAlta().before(new Date())) {
				tela.setFechaCompAnt(tela.getFechaAlta());
				tela.setPrecioCompraAnterior(tela.getPrecioT() / tela.getCantidadCom());
			}
			return tela.getFechaCompAnt();
		}
		return new Date();
	}

	public Double inflacionTela(Tela telar) throws ParseException {

		SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd");

		String fechaUlti = d1.format(telar.getFechaCompAnt());
		String fechaActual = d2.format(new Date());

		LocalDate fuc = LocalDate.parse(fechaUlti, DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate fac = LocalDate.parse(fechaActual, DateTimeFormatter.ISO_LOCAL_DATE);

		long dif = ChronoUnit.DAYS.between(fuc, fac);

		Double precioUnit = (double) (telar.getPrecioT() / telar.getCantidadCom());

		Double variacion = (double) ((telar.getPrecioCompraAnterior() / precioUnit) - 1) * -100;

		if (dif != 0) {
			Double inflacion = (double) ((variacion / dif) * 30);
			return inflacion;
		} else {
			return variacion - 1;
		}
	}

	public List listaTela() {

		return telaRepo.ordenDate();
	}

	public void eliminarTela(String id) {

		telaRepo.deleteById(id);
	}

	public List listarTelaPorNombre(String nombreTela) {

		return telaRepo.buscarTelaxNombre(nombreTela);
	}

	public Double stockTela(Double cantidadCom, String nombre) throws ErrorServicio {

		List<Tela> listatela = telaRepo.buscarTelaxNombre(nombre);

		Double stockT = 0.00;

		for (Tela tela : listatela) {

			stockT = stockT + tela.getCantidadCom();

			if (tela.getFechaAlta().before(Date.from(Instant.now()))) {
				tela.setEstadoT(false);
			}
			System.out.println(stockT);
		}

		stockT = stockT + cantidadCom;

		return stockT;
	}

	@Transactional
	public void modificarTela(String id, String proveedor, String nombreTela, Double cantComMod, Double precioT,
			Double rinde) throws ErrorServicio {

		Tela telaE = telaRepo.findById(id).get();

		telaE.setFechaAlta(Date.from(Instant.now()));
		telaE.setProveedor(proveedor.toUpperCase());
		telaE.setNombreTela(nombreTela.toUpperCase());
		Double stockAnt = telaE.getStockT() - telaE.getCantidadCom();
		telaE.setCantidadCom(cantComMod);
		telaE.setPrecioT(precioT);
		telaE.setStockT(stockAnt + cantComMod);
		telaE.setPrecioCompraAnterior(rinde);

		telaRepo.save(telaE);
	}

	public Tela GetOneId(String id) {

		Tela telaE = telaRepo.findById(id).get();

		return telaE;

	}

	public void validar(String proveedor, String nombreTela, Double cantidadCom, Double precioT, Double rinde)
			throws ErrorServicio {

		if (nombreTela.isEmpty() || nombreTela == null) {
			throw new ErrorServicio(" El nombre de la tela no puede ser nulo o estar vacio ");
		}

		if (cantidadCom == null || cantidadCom < 1) {

			throw new ErrorServicio(" La cantidad comprada no puede ser nula o menor que 1 ");
		}

		if (precioT == null || precioT < 1) {
			throw new ErrorServicio(" El precio de la tela no puede ser nulo o menor que 1 ");
		}

		if (proveedor == null || proveedor.isEmpty()) {
			throw new ErrorServicio(" El nombre del proveedor no puede ser nulo o estar vacio ");
		} else {
			proveedor = proveedor.toUpperCase();
		}

		if (rinde == null || rinde < 1) {
			throw new ErrorServicio(" El rinde de la tela no puede ser nulo o menor que 1 ");
		}

	}

}
