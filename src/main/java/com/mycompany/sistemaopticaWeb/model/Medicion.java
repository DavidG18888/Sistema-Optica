/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistemaoptica.model;

import java.sql.Date;

public class Medicion {

    private int idMedicion;
    private int idCliente;
    private Date fecha;
    private Double esfera;
    private Double cilindro;
    private Integer eje;
    private Double adicion;
    private String observaciones;

    public Medicion() {
    }

    public int getIdMedicion() {
        return idMedicion;
    }

    public void setIdMedicion(int idMedicion) {
        this.idMedicion = idMedicion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getEsfera() {
        return esfera;
    }

    public void setEsfera(Double esfera) {
        this.esfera = esfera;
    }

    public Double getCilindro() {
        return cilindro;
    }

    public void setCilindro(Double cilindro) {
        this.cilindro = cilindro;
    }

    public Integer getEje() {
        return eje;
    }

    public void setEje(Integer eje) {
        this.eje = eje;
    }

    public Double getAdicion() {
        return adicion;
    }

    public void setAdicion(Double adicion) {
        this.adicion = adicion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
