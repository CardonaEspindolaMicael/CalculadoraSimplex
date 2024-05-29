/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class ArbolBinarioBusqueda <K extends Comparable<K>,V> implements IArbolBusqueda<K,V>{
    protected NodoBinario<K,V> raiz;
    
    
    
    
    public boolean esArbolVacio(){
        return NodoBinario.esNodoVacio(this.raiz);
    }
    
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        	if (claveAInsertar==null) {
			throw new IllegalArgumentException("La clave no puede ser nula");	
			}
		if (valorAInsertar==null) {
			throw new IllegalArgumentException("El valor no puede ser nulo");
		}
		
		if (this.esArbolVacio()) {
			this.raiz= new NodoBinario<>(claveAInsertar,valorAInsertar);
			return;
		}
		insertar(this.raiz,claveAInsertar, valorAInsertar);

    }
    @Override
    	public int altura() {
		// TODO Auto-generated method stub
		
		return altura(this.raiz);
	}

	protected int altura(NodoBinario<K, V> nodoActual) {
		// TODO Auto-generated method stub
		if(NodoBinario.esNodoVacio(nodoActual)) {
		return 0;
		}
		int alturaPorIzquierda=altura(nodoActual.getHijoIzquierdo());
		int alturaPorDerecha= altura(nodoActual.getHijoDerecho());
		if(alturaPorIzquierda>alturaPorDerecha) {
			return alturaPorIzquierda+1;
		}
		return alturaPorDerecha+1;
	}
     private void insertar(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
     if(NodoBinario.esNodoVacio(nodoActual)){
         return;
     }
         K claveActual = nodoActual.getClave();
         if (claveAInsertar.compareTo(claveActual) > 0) {
             if (NodoBinario.esNodoVacio(nodoActual.getHijoDerecho())) {
                 NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
                 nodoActual.setHijoDerecho(nuevoNodo);
                 return;
             }
             insertar(nodoActual.getHijoDerecho(), claveAInsertar, valorAInsertar);
         }

         if (claveAInsertar.compareTo(claveActual) < 0) {
             if (NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())) {
                 NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAInsertar);
                 nodoActual.setHijoIzquierdo(nuevoNodo);
                 return;
             }
             insertar(nodoActual.getHijoIzquierdo(), claveAInsertar, valorAInsertar);
         }
          
    
     }

@Override
  public V eliminar(K claveAEliminar) {
        V valorRetorno=this.buscarPalabra(claveAEliminar);
        if(valorRetorno==null){
          return null;
        }
        this.raiz=eliminar(raiz,claveAEliminar);
        return valorRetorno;
    }
    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual,K claveAEliminar){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return (NodoBinario<K, V> )NodoBinario.nodoVacio();
        }
            K claveActual=nodoActual.getClave();
                if(claveAEliminar.compareTo(claveActual)<0){
                    NodoBinario<K, V> izquierdo=eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
                    nodoActual.setHijoIzquierdo(izquierdo);
                    return nodoActual;
                }
                if(claveAEliminar.compareTo(claveActual)>0){
                   NodoBinario<K, V> derecho=eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
                   nodoActual.setHijoDerecho(derecho);
                   return nodoActual;
                }
             /// SI SE LLEGA A ESTE PUNTO SE ENCONTRO LA CLAVE A ELIMINAR
             ///YA QUE LA CLAVE A ELIMINAR NO ES MENOR NI MAYOR ,SINO IGUAL
            // # caso 1 el nodo a eliminar es una hoja
            if(nodoActual.esHoja()){
                return (NodoBinario<K, V> )NodoBinario.nodoVacio();
            }
            //# CASO 2 LA CLAVE A ELIMINAR ES UN NODO INCOMPLETO
            if(nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()){
                return nodoActual.getHijoIzquierdo();
            }
            if(!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()){
                return nodoActual.getHijoDerecho();
            }
            // # CASO 3 LA CLAVE A ELIMINAR ES UN NODO COMPLETO 
            // HAY QUE BUSCAR SU NODO SUCESOR
            NodoBinario<K, V> nodoSucesor=cambiar(nodoActual.getHijoDerecho());
            NodoBinario<K, V> posibleNuevo=eliminar(nodoActual.getHijoDerecho(),nodoSucesor.getClave());
            nodoActual.setHijoDerecho(posibleNuevo);
            nodoSucesor.setHijoDerecho(nodoActual.getHijoDerecho());
            nodoSucesor.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
            nodoActual.setHijoDerecho((NodoBinario<K, V> )NodoBinario.nodoVacio());
            nodoActual.setHijoIzquierdo((NodoBinario<K, V> )NodoBinario.nodoVacio());
        
        return nodoSucesor;
    
    }
    public NodoBinario<K, V>  cambiar(NodoBinario<K, V> nodoActual){ 
         NodoBinario<K, V> anterior=(NodoBinario<K, V>) NodoBinario.nodoVacio();
         if(NodoBinario.esNodoVacio(nodoActual)){
             return (NodoBinario<K, V> )NodoBinario.nodoVacio();
         }
         while(!NodoBinario.esNodoVacio(nodoActual)){
             anterior=nodoActual;
             nodoActual=nodoActual.getHijoIzquierdo();
         }
         return anterior;
    }

    @Override
    public V buscarPalabra(K clave) {
    return buscarPalabra(this.raiz,clave);
    }
    private V buscarPalabra(NodoBinario<K, V> nodoActual, K claveABuscar) {
    if(NodoBinario.esNodoVacio(nodoActual)){
        return null;
    }
    K claveActual=nodoActual.getClave();
    V posibleValor=nodoActual.getValor();
    if(claveABuscar.compareTo(claveActual)==0){
    return posibleValor;
    }
    if(claveABuscar.compareTo(claveActual)>0){
        posibleValor=buscarPalabra(nodoActual.getHijoDerecho(), claveABuscar);
    }
    if(claveABuscar.compareTo(claveActual)<0){
        posibleValor=buscarPalabra(nodoActual.getHijoIzquierdo(), claveABuscar);
    }
    
    return posibleValor;
    }

    

    @Override
    public void editarPalabra(K claveABuscar,V nuevoValor) {
     if(!existeLaPalabra(claveABuscar)){
         throw new IllegalArgumentException(" La palabra no se encuentra en la base");
     }
     editarPalabra(this.raiz,claveABuscar,nuevoValor);
    }
    private void editarPalabra(NodoBinario<K, V> nodoActual, K claveAEditar,V nuevoValor) {
    if(NodoBinario.esNodoVacio(nodoActual)){
        return ;
    }
    K claveActual=nodoActual.getClave();
    if(claveAEditar.compareTo(claveActual)==0){
    nodoActual.setValor(nuevoValor);
    }
    if(claveAEditar.compareTo(claveActual)>0){
        editarPalabra(nodoActual.getHijoDerecho(), claveAEditar,nuevoValor);
    }
    if(claveAEditar.compareTo(claveActual)<0){
        editarPalabra(nodoActual.getHijoIzquierdo(), claveAEditar,nuevoValor);
    }
    
    }

    @Override
    public boolean existeLaPalabra(K clave) {
        return buscarPalabra(clave)!=null;
    }

    @Override
    public List<K> recorrido() {
    List<K> recorrido= new ArrayList<>();
    recorrido(this.raiz,recorrido);
    return recorrido;

    }

    private void recorrido(NodoBinario<K, V> nodoActual, List<K> recorrido) {
    if(NodoBinario.esNodoVacio(nodoActual)){
        return;
    }
        recorrido(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorrido(nodoActual.getHijoDerecho(), recorrido);
    }

    @Override
    public void vaciar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }






   
    
}
