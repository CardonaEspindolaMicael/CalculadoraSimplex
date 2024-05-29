/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.dictionary;

import com.mycompany.dictionary.ArbolBinarioBusqueda;
import com.mycompany.dictionary.IArbolBusqueda;

/**
 *
 * @author user
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        IArbolBusqueda<String,String> arbolPrueba= new ArbolesAVL<>();
        arbolPrueba.insertar("Blue", "Azul");
        arbolPrueba.insertar("Orange", "Naranja");
        arbolPrueba.insertar("Red", "Rojo");
        arbolPrueba.insertar("Gray", "Plomo");
        arbolPrueba.insertar("Green", "Verde");
        arbolPrueba.insertar("Yellow", "Amarillo");
        arbolPrueba.insertar("Shoes", "Zapatos");
        arbolPrueba.insertar("Pants", "Pantalones");
        arbolPrueba.insertar("Rhythm", "Hola");
        
        
        System.out.println("List of words: "+arbolPrueba.recorrido());
        System.out.println("Su traduccion al español es: "+arbolPrueba.buscarPalabra("Rhythm"));
        arbolPrueba.editarPalabra("Rhythm","Ritmo");
        System.out.println("Su traduccion al español es: "+arbolPrueba.buscarPalabra("Rhythm"));
        
        System.out.println("Eliminado; "+arbolPrueba.eliminar("Rhythm"));
        System.out.println("List of words: "+arbolPrueba.recorrido());
    }
    
}
