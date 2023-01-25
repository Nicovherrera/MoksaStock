package com.moksa.moksa.entidades;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Tela {
	
	@Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	private Date fechaCompAnt;
	private String nombreTela;
	private Double cantidadCom;
	private Double precioT;
	private String proveedor;
	private Double precioCompraAnterior;
	@Column(name ="IflacionT")
	private Double inflacion;
	
	
	private Double stockT;
	private Boolean estadoT;
	
	public Double getInflacion() {
		return inflacion;
	}
	public void setInflacion(Double inflacion) {
		this.inflacion = inflacion;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaCompAnt() {
		return fechaCompAnt;
	}
	public void setFechaCompAnt(Date fechaCompAnt) {
		this.fechaCompAnt = fechaCompAnt;
	}
	public String getNombreTela() {
		return nombreTela;
	}
	public void setNombreTela(String nombreTela) {
		this.nombreTela = nombreTela;
	}
	public Double getCantidadCom() {
		return cantidadCom;
	}
	public void setCantidadCom(Double cantidadCom) {
		this.cantidadCom = cantidadCom;
	}
	public Double getPrecioT() {
		return precioT;
	}
	public void setPrecioT(Double precioT) {
		this.precioT = precioT;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proovedor) {
		this.proveedor = proovedor;
	}
	public Double getPrecioCompraAnterior() {
		return precioCompraAnterior;
	}
	public void setPrecioCompraAnterior(Double f) {
		this.precioCompraAnterior = f;
	}
	public Double getStockT() {
		return stockT;
	}
	public void setStockT(Double integer) {
		this.stockT = integer;
	}
	public Boolean getEstadoT() {
		return estadoT;
	}
	public void setEstadoT(Boolean estadoT) {
		this.estadoT = estadoT;
	}
	@Override
	public String toString() {
		return "Tela [id=" + id + ", fechaAlta=" + fechaAlta + ", nombreTela=" + nombreTela
				+ ", cantidadCom=" + cantidadCom + ", precioT=" + precioT + ", proovedor=" + proveedor + ", precioCompraAnterior="
				+ precioCompraAnterior + ", stockT=" + stockT + ", estadoT=" + estadoT + ", fechaCompAnt"+ fechaCompAnt + "]";
	}
	
	


}
