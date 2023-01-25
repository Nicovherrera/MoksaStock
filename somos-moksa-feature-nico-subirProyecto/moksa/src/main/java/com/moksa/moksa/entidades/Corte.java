package com.moksa.moksa.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;



@Entity
public class Corte {
	
	@Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAltaCt;
	private Date fechaCompAnteC;
	private String nombreProveedroCte;
	private Double precio;
	private Double precioCompAntC;
	private Double inflacionC;
	private Boolean estadoCte;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFechaAltaCt() {
		return fechaAltaCt;
	}
	public void setFechaAltaCt(Date fechaAltaCt) {
		this.fechaAltaCt = fechaAltaCt;
	}
	public String getNombreProveedroCte() {
		return nombreProveedroCte;
	}
	public void setNombreProveedroCte(String nombreProveedroCte) {
		this.nombreProveedroCte = nombreProveedroCte;
	}
	public Date getFechaCompAnteC() {
		return fechaCompAnteC;
	}
	public void setFechaCompAnteC(Date fechaCompAnteC) {
		this.fechaCompAnteC = fechaCompAnteC;
	}
	public Double getPrecioCompAntC() {
		return precioCompAntC;
	}
	public void setPrecioCompAntC(Double precioCompAntC) {
		this.precioCompAntC = precioCompAntC;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Double getPrecio() {
		return precio;
	}
	public Double getInflacionC() {
		return inflacionC;
	}
	public void setInflacionC(Double inflacionC) {
		this.inflacionC = inflacionC;
	}
	public Boolean getEstadoCte() {
		return estadoCte;
	}
	public void setEstadoCte(Boolean estadoCte) {
		this.estadoCte = estadoCte;
	}
	@Override
	public String toString() {
		return "Corte [id=" + id + ", fechaAltaCt=" + fechaAltaCt + ", nombreProveedroCte=" + nombreProveedroCte
				+ ", precio=" + precio + ", estadoCte=" + estadoCte + "]";
	}
	
	
	
	

}
