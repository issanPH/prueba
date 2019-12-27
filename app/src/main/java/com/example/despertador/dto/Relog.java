package com.example.despertador.dto;

import java.io.Serializable;

public class Relog implements Serializable {
    private int id;
    private int hora;
    private int minutos;
    private String AMPM;
    private String estado;

    public Relog() {
    }

    public Relog(int id, int hora, int minutos, String AMPM) {
        this.id = id;
        this.hora = hora;
        this.minutos = minutos;
        this.AMPM = AMPM;
    }

    public Relog(int id, int hora, int minutos, String aMpM, String estado) {
        this.id = id;
        this.hora = hora;
        this.minutos = minutos;
        this.AMPM =aMpM;
        this.estado =estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAMPM() {
        return AMPM;
    }

    public void setAMPM(String AMPM) {
        this.AMPM = AMPM;
    }

    @Override
    public String toString() {
        return  hora +
                ":" + minutos +
                "  " + AMPM;
    }
}
