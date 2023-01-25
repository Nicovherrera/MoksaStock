package com.moksa.moksa.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Articulo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    private String nombre;
    private Double precio;
    private Float consumo;
    private Float costoPrenda;
    private Boolean estado;
    private Float costoAvios;

    @ManyToOne
    private Tela tela;

    @ManyToMany
    private List<Avio> avio;

    @OneToOne
    private Corte corte;

    @OneToOne
    private Confeccion confeccion;

    @OneToOne
    private Foto archivo;

    public List<Avio> getAvio() {
        return avio;
    }

    public void setAvio(List<Avio> avio) {
        this.avio = avio;
    }

    public Foto getArchivo() {
        return archivo;
    }

    public void setArchivo(Foto archivo) {
        this.archivo = archivo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Float getConsumo() {
        return consumo;
    }

    public void setConsumo(Float consumo) {
        this.consumo = consumo;
    }

    public Float getCostoPrenda() {
        return costoPrenda;
    }

    public void setCostoPrenda(Float costoPrenda) {
        this.costoPrenda = costoPrenda;
    }

    public Tela getTela() {
        return tela;
    }

    public void setTela(Tela tela) {
        this.tela = tela;
    }

    public Corte getCorte() {
        return corte;
    }

    public void setCorte(Corte corte) {
        this.corte = corte;
    }

    public Confeccion getConfeccion() {
        return confeccion;
    }

    public void setConfeccion(Confeccion confeccion) {
        this.confeccion = confeccion;
    }

    public Foto getFoto() {
        return archivo;
    }

    public void setFoto(Foto archivo) {
        this.archivo = archivo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Float getCostoAvios() {
        return costoAvios;
    }

    public void setCostoAvios(Float costoAvios) {
        this.costoAvios = costoAvios;
    }

    @Override
    public String toString() {
        return "Articulo [id=" + id + ", fechaAlta=" + fechaAlta + ", nombre=" + nombre + ", precio=" + precio
                + ", consumo=" + consumo + ", consumoTela=" + costoPrenda + ", tela=" + tela + ", avio=" + avio
                + ", corte=" + corte + ", confeccion=" + confeccion + ", foto=" + archivo + "]";
    }

}
