/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.dictionary;

import java.util.List;

/**
 *
 * @author user
 */
public interface IArbolBusqueda <K extends Comparable<K>,V> {
    
 void insertar(K clave,V valor);  
 V eliminar(K clave);  
 int altura();
 V buscarPalabra(K clave);
 void editarPalabra(K claveAEditar,V nuevoValor);
 boolean existeLaPalabra(K clave);
 List<K> recorrido();
 void vaciar();
}
