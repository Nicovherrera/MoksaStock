package com.moksa.moksa.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Avio {
	
	@Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAltaA;
	private Date fechaFactAnt;
	private String nombreAvio;
	private String tipoAvio;
	private Integer cantidadCom;
	private Float precio;
	private Float precioAnt;
	private Float inflacionA;
	
	
	private String proveedor;
	private Integer stockA;
	private Boolean estadoA;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFechaAltaA() {
		return fechaAltaA;
	}
	public void setFechaAltaA(Date fechaAltaA) {
		this.fechaAltaA = fechaAltaA;
	}
	public Date getFechaFactAnt() {
		return fechaFactAnt;
	}
	public void setFechaFactAnt(Date fechaFactA) {
		this.fechaFactAnt = fechaFactA;
	}
	public String getNombreAvio() {
		return nombreAvio;
	}
	public void setNombreAvio(String nombreAvio) {
		this.nombreAvio = nombreAvio;
	}
	public String getTipoAvio() {
		return tipoAvio;
	}
	public void setTipoAvio(String tipoAvio) {
		this.tipoAvio = tipoAvio;
	}
	public Integer getCantidadCom() {
		return cantidadCom;
	}
	public void setCantidadCom(Integer cantidadCom) {
		this.cantidadCom = cantidadCom;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public Float getPrecioAnt() {
		return precioAnt;
	}
	public void setPrecioAnt(Float precioAnt) {
		this.precioAnt = precioAnt;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	public Integer getStockA() {
		return stockA;
	}
	public void setStockA(Integer stockA) {
		this.stockA = stockA;
	}
	public Boolean getEstadoA() {
		return estadoA;
	}
	public void setEstadoA(Boolean estadoA) {
		this.estadoA = estadoA;
	}
	public Float getInflacionA() {
		return inflacionA;
	}
	public void setInflacionA(Float inflacionA) {
		this.inflacionA = inflacionA;
	}
	@Override
	public String toString() {
		return "Avio [id=" + id + ", fechaAltaA=" + fechaAltaA + ", nombreAvio="
				+ nombreAvio + ", tipoAvio=" + tipoAvio + ", precio=" + precio
				+ ", proveedor=" + proveedor + ", estadoA=" + estadoA + "]";
	}
	
	

}
