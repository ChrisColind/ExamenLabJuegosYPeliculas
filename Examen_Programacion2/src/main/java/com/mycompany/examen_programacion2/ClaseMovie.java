/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.examen_programacion2;

import java.util.Calendar;

public class ClaseMovie extends RentItem {

    private Calendar fechaEstreno;
    private String estadoManual = null;

    public ClaseMovie(String codigo, String nombre, double precioB) {
        super(codigo, nombre, precioB);
        this.fechaEstreno = Calendar.getInstance();
    }

    public Calendar GetFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Calendar fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public void setEstadoManual(String estado) {
        this.estadoManual = estado;
    }

    public String getEstado() {
        if (estadoManual != null) return estadoManual;

        Calendar hace3Meses = Calendar.getInstance();
        hace3Meses.add(Calendar.MONTH, -3);
        return fechaEstreno.after(hace3Meses) ? "ESTRENO" : "NORMAL";
    }

    @Override
    public double pagoRenta(int dias) {
        double total = getPrecioBase() * dias;
        String estado = getEstado();

        if (estado.equals("ESTRENO") && dias > 2) {
            total += 50 * (dias - 2);
        } else if (estado.equals("NORMAL") && dias > 5) {
            total += 30 * (dias - 5);
        }
        return total;
    }

    @Override
    public String toString() {
        return super.toString() + " Estado: " + getEstado() + " - Movie";
    }
}