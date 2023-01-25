package com.moksa.moksa.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;



@Entity
public class Confeccion {
	
	@Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAltaCcion;
	private Date fechaAltaAntCcion;
	private String nombreProveedroCcion;
	private Double precio;
	private Double precioCompAntCcion;
	private Double inflacionCcion;
	private Boolean estadoCcion;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFechaAltaCcion() {
		return fechaAltaCcion;
	}
	public void setFechaAltaCcion(Date fechaAltaCcion) {
		this.fechaAltaCcion = fechaAltaCcion;
	}
	public String getNombreProveedroCcion() {
		return nombreProveedroCcion;
	}
	public void setNombreProveedroCcion(String nombreProveedroCcion) {
		this.nombreProveedroCcion = nombreProveedroCcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public Double getInflacionCcion() {
		return inflacionCcion;
	}
	public void setInflacionCcion(Double inflacion) {
		this.inflacionCcion = inflacion;
	}
	public Date getFechaAltaAntCcion() {
		return fechaAltaAntCcion;
	}
	public void setFechaAltaAntCcion(Date fechaAltaAntCcion) {
		this.fechaAltaAntCcion = fechaAltaAntCcion;
	}
	public Double getPrecioCompAntCcion() {
		return precioCompAntCcion;
	}
	public void setPrecioCompAntCcion(Double precioCompAntCcion) {
		this.precioCompAntCcion = precioCompAntCcion;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Boolean getEstadoCcion() {
		return estadoCcion;
	}
	public void setEstadoCcion(Boolean estadoCcion) {
		this.estadoCcion = estadoCcion;
	}
	@Override
	public String toString() {
		return "Confeccion [id=" + id + ", fechaAltaCcion=" + fechaAltaCcion + ", nombreProveedroCcion="
				+ nombreProveedroCcion + ", precio=" + precio + ", estadoCcion=" + estadoCcion + "]";
	}
	
	

}
