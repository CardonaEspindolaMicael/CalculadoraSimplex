/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

import com.mycompany.dictionary.ArbolBinarioBusqueda;
import com.mycompany.dictionary.IArbolBusqueda;

/**
 *
 * @author user
 */
public class BaseDeDatos  {
     private IArbolBusqueda<String,String> arbolPrueba;
    
    public BaseDeDatos(IArbolBusqueda<String,String> arbol){
      arbolPrueba=arbol;
      arbolPrueba.insertar("Blue", "Adjetivo: Azul");
      arbolPrueba.insertar("Orange", "Adjetivo: Naranja");
      arbolPrueba.insertar("Red", "Adjetivo: Rojo");
      arbolPrueba.insertar("Gray", "Adjetivo: Plomo");
      arbolPrueba.insertar("Green", "Adjetivo: Verde");
      arbolPrueba.insertar("Yellow", "Adjetivo: Amarillo");
      arbolPrueba.insertar("Shoes", "Nombre: Zapatos");
      arbolPrueba.insertar("Pants", "Nombre: Pantalones");
      arbolPrueba.insertar("Rhythm", "Saludo: Hola");
      arbolPrueba.insertar("Threat", "Verbo: Amenaza o Amenazar");
      arbolPrueba.insertar("Brunt", "Sustantiv: La peor parte de algo ");
      arbolPrueba.insertar("Slumpy", "Slang: Estar enojado por no dormir");
      arbolPrueba.insertar("Thread", "Sustantivo: Hilo");
    }
    
    
}
