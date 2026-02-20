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
    
    public Game(String codigo,String nombre, double precioB){
        super(codigo, nombre, precioB);
        gameSpecs = new ArrayList<>();
        fechaPublicacion = Calendar.getInstance();
    }
    
    setFechaPublicacion(int year, int mes, int dia)
    

    @Override
    public void submenu() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void ejecutarOpcion(int opcion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double pagoRenta(int dias) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
