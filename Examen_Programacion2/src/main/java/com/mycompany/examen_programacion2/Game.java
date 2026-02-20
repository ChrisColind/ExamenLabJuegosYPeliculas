/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.examen_programacion2;
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author gpopo
 */
public class Game extends RentItem implements MenuActions {
    
    private ArrayList<String> gameSpecs;
    private final int renta = 20;
    private Calendar fechaPublicacion;
    private javax.swing.ImageIcon imagen;
    
    public Game(String codigo,String nombre, double precioB){
        super(codigo, nombre, precioB);
        gameSpecs = new ArrayList<>();
        fechaPublicacion = Calendar.getInstance();
    }
    
    public void setFechaPublicacion(int year, int mes, int dia){
        fechaPublicacion.set(year, mes - 1, dia);
    }
    
    public void addEspecificacion(String Spec){
        gameSpecs.add(Spec);
    }
    
    @Override
    public void setImagen(javax.swing.ImageIcon imagen){
        this.imagen = imagen;
    }
    
    private void listEspecificaciones(int indice){
        if(indice >= gameSpecs.size()) return;
        System.out.println(gameSpecs.get(indice));
        listEspecificaciones(indice + 1);
    }
    
    public void listEspecificaciones(){
        listEspecificaciones(0);
    }
    
    @Override
    public String toString(){
        return super.toString() + "Fecha: " + fechaPublicacion.get(Calendar.DAY_OF_MONTH) 
                + "/" + (fechaPublicacion.get(Calendar.MONTH)+1) + "/" 
                + fechaPublicacion.get(Calendar.YEAR) +
                " - PS3 Game";
    }
    

    @Override
    public void submenu() {
        System.out.println("1. Actualizar fecha de publicacion");
        System.out.println("2. Agregar especificacion");
        System.out.println("3. Ver especificaciones");
    }

    @Override
    public void ejecutarOpcion(int opcion) {
        switch(opcion){
            case 1:
                //gui
                break;
            case 2:   
                //gui
                break;
            case 3: 
                listEspecificaciones();
                break;              
        }
    }

    @Override
    public double pagoRenta(int dias) {
        return dias*renta;       
    }
    
}
