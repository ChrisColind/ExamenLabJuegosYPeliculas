package com.mycompany.examen_programacion2;

import javax.swing.ImageIcon;


public abstract class RentItem {
    
    private String nombre;
    private String codigo;
    private double precioB;
    private int copiasD;
    private ImageIcon imagen; 
    
    public RentItem(String codigo,String nombre, double precioB){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioB = precioB;
        this.copiasD = 0;
    }
    
    public abstract double pagoRenta(int dias);
    
    public String getCodigo(){
        return codigo;
    }

    public String getNombre(){
        return nombre;
    }

    public double getPrecioBaseRenta(){
        return precioB;
    }

    public int getCopiasDisponibles(){
        return copiasD;
    }

    public ImageIcon getImagen(){
        return imagen;
    }
    
    
    public void setCopiasDisponibles(int copiasD){
        this.copiasD=copiasD;
    }
    
    public void setImagen(ImageIcon imagen){
        this.imagen=imagen;
    }
    
    
    
    @Override
    public String toString(){
        return "Codigo: "+codigo+", Nombre: "+nombre+", Precio Base: "+precioB+", Copias: "+copiasD;
    }
    
}
