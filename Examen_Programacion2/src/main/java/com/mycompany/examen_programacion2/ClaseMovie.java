/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.examen_programacion2;

import java.util.Calendar;

public class ClaseMovie extends RentItem{
    private Calendar fechaEstreno;
    
    public ClaseMovie(String codigo, String nombre, double precioB){
        super(codigo,nombre,precioB);
        this.fechaEstreno= Calendar.getInstance();
    }
    
    public Calendar GetFechaEstreno(){
        return fechaEstreno;
    }
    
    public void setFechaEstreno(Calendar fechaEstreno){
        this.fechaEstreno = fechaEstreno;
    }
    
    public String getEstado(){
        Calendar hace3Meses = Calendar.getInstance();
        hace3Meses.add(Calendar.MONTH, -3);
    
        if(fechaEstreno.after(hace3Meses)){
            return "ESTRENO";
        }   
        else {
            return "NORMAL";
        }
    }
    
    @Override
    public double pagoRenta(int dias){
        double Total = getPrecioBase() * dias;
        String estado = getEstado();
        int diasAdicionales;
        
        if(estado.equals("ESTRENO") && dias > 2){
            diasAdicionales = (dias - 2);
            Total += (50 * diasAdicionales);
            
        }else if(estado.equals("NORMAL") && dias > 5){
            diasAdicionales = (dias - 5);
            Total += (30 * diasAdicionales);
        }
        return Total;
    }
    
    @Override
    public String toString(){
        return super.toString() + " Estado: " + getEstado() + " – Movie";
    }
}



    
