package com.mycompany.dictionary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class ArbolMViasBusqueda<K extends Comparable<K>,V> implements 
IArbolBusqueda<K,V> {
    protected NodoMVias<K, V> raiz;
    protected final int orden;

    protected static final int POSICION_NO_VALIDA = -1;
    protected static final int ORDEN_MINIMO = 3;

    public ArbolMViasBusqueda() {
        this.orden = ArbolMViasBusqueda.ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int orden)  {//llevar al paquete exepciones
        if (orden < ArbolMViasBusqueda.ORDEN_MINIMO) {
            
        }
        this.orden = orden;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("clave invalidad, no se aceptan claves nulas");
        }
        if (valorAsociado == null) {
            throw new IllegalArgumentException("valor invalido, no se aceptan valores nulos");
        }
        if (this.raiz==null) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
            return;
        }

        NodoMVias<K, V> nodoActual = this.raiz;
        do {
            int posicionDeClave = this.obtenerPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClave != ArbolMViasBusqueda.POSICION_NO_VALIDA) {
                nodoActual.setValor(posicionDeClave, valorAsociado);
                nodoActual = NodoMVias.nodoVacio();
            } else if (nodoActual.esHoja()) {
 
                if (nodoActual.estanClavesLlenas()) {
                    int posicionPorDondeBajar = obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    NodoMVias<K, V> nuevoNodo = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoNodo);
                } else {
                    insertarClaveYValor(nodoActual, claveAInsertar, valorAsociado);   
                }
                nodoActual = NodoMVias.nodoVacio();
            } else {
                int posicionPorDondeBajar = obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                if (nodoActual.esHijoVacio(posicionPorDondeBajar)) {
                    NodoMVias<K, V> nuevoNodo = new NodoMVias<>(this.orden, claveAInsertar, valorAsociado);
                    nodoActual.setHijo(posicionPorDondeBajar, nuevoNodo);
                    break;
                } else {
                    nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                }
            }
        } while (!NodoMVias.esNodoVacio(nodoActual));

    }

    protected int obtenerPosicionDeClave(NodoMVias<K, V> nodoActual, K claveABuscar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveEnTurno = nodoActual.getClave(i);
            if (claveABuscar.compareTo(claveEnTurno) == 0) {
                return i;
            }
        }

        return ArbolMViasBusqueda.POSICION_NO_VALIDA;
    }

    public void insertarClaveYValor(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAsociado) {
        int i = 0;
        while (i < nodoActual.nroDeClavesNoVacias() && (claveAInsertar.compareTo(nodoActual.getClave(i)) > 0)) {
            i++;
        }
        if (i == nodoActual.nroDeClavesNoVacias()) {
            nodoActual.setClave(i,claveAInsertar);
            nodoActual.setValor(i, valorAsociado);
        } else {
            for (int j = nodoActual.nroDeClavesNoVacias(); j > i; j--) {
                nodoActual.setClave(j,nodoActual.getClave(j - 1));
                nodoActual.setValor(j, nodoActual.getValor(j - 1));
            }
            nodoActual.setClave(i,claveAInsertar);
            nodoActual.setValor(i, valorAsociado);
        }

    }


    protected int obtenerPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveABuscar) {
        int i=0;
       // boolean llegoAlFinal=false;
            while(i<nodoActual.nroDeClavesNoVacias()){
                K claveActual=nodoActual.getClave(i);
                    if(claveActual.compareTo(claveABuscar)<0){
                        i++;
                    }else{
                        break;
                    }       
            }
            if(nodoActual.getClave(nodoActual.nroDeClavesNoVacias()-1).compareTo(claveABuscar)<0){
                return nodoActual.nroDeClavesNoVacias();
            }
            return i;
    }

    @Override
    public V eliminar(K claveAEliminar) {
        if (claveAEliminar == null) {
            throw new IllegalArgumentException("clave invalidad, no se aceptan claves nulas");
        }
        V valorAsociado = buscarPalabra(claveAEliminar);
        if (valorAsociado == null) {
             throw new IllegalArgumentException("clave invalidad, no se aceptan valores nulos");
        }

        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAsociado;
    }

    private NodoMVias<K, V> eliminar(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        for (int i = 0; i < nodoActual.nroDeClavesNoVacias(); i++) {
            K claveEnTurno = nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveEnTurno) == 0) {
                if (nodoActual.esHoja()) {
                    eliminarClaveDePosicion(nodoActual, i); 
                    if (nodoActual.nroDeClavesNoVacias()!= 0) {
                        return NodoMVias.nodoVacio();
                    } else {
                        return nodoActual;
                    }
                } else {
                    K claveDeReemplazo;
                    if (hayHijosNoVaciosMasAdelante(nodoActual, i)) {
                        claveDeReemplazo = buscarSucesorEnInOrden(nodoActual, claveAEliminar); 
                    } else {
                        claveDeReemplazo = buscarPredecesorEnInOrden(nodoActual, claveAEliminar);
                    }

                    V valorDeReemplazo = buscarPalabra(claveDeReemplazo);
                    nodoActual = eliminar(nodoActual, claveDeReemplazo);
                    nodoActual.setValor(i, valorDeReemplazo);
                    return nodoActual;
                }
            }
            if (claveAEliminar.compareTo(claveEnTurno) < 0) {
                NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijo(i, supuestoNuevoHijo);
                return nodoActual;
            }
        }
        NodoMVias<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()), claveAEliminar);
        nodoActual.setHijo(nodoActual.nroDeClavesNoVacias(), supuestoNuevoHijo);
        return nodoActual;
    }

    protected void eliminarClaveYValor(NodoMVias<K, V> nodoActual, int i) {
        //el nodo es hoja 
        int cantDeClavesNoVacias = nodoActual.nroDeClavesNoVacias();
        nodoActual.setClave(i,(K)NodoMVias.datoVacio());
        nodoActual.setValor(i, (V)NodoMVias.datoVacio());

        if (i+1 < cantDeClavesNoVacias) {
            for (int j = i; j < cantDeClavesNoVacias-1; j++) {
                nodoActual.setClave(j, nodoActual.getClave(j+1));
                nodoActual.setValor(j, nodoActual.getValor(j+1));
            }
            if (cantDeClavesNoVacias > 0) {
                nodoActual.setClave(cantDeClavesNoVacias-1, (K)NodoMVias.datoVacio());
                nodoActual.setValor(cantDeClavesNoVacias-1, (V)NodoMVias.datoVacio());
            }
        }
    }
    
    public void eliminarClaveDePosicion(NodoMVias<K, V> nodoActual, int posicion) { 
        int posicionDeLaClaveAEliminar = this.obtenerPosicionDeClave(nodoActual, nodoActual.getClave(posicion));
        if (nodoActual.estanClavesLlenas()) {
            for (int i = posicionDeLaClaveAEliminar; i < nodoActual.nroDeClavesNoVacias() - 1; i++) {
                nodoActual.setClave(i,nodoActual.getClave(i + 1));
            }
            nodoActual.setClave( nodoActual.nroDeHijosQuePuedeTener() - 2,null);
        } else {
            for (int i = posicionDeLaClaveAEliminar; i < nodoActual.nroDeClavesNoVacias(); i++) {
                nodoActual.setClave( i,nodoActual.getClave(i + 1));
            }
        }

    }

    public boolean hayHijosNoVaciosMasAdelante(NodoMVias<K, V> nodoActual, int i) {
        for (int j = i + 1; j < nodoActual.nroDeClavesNoVacias(); j++) {
            if (!nodoActual.esHijoVacio(j)) {
                return true;
            }
        }
        return false;
    }

    public K buscarSucesorEnInOrden(NodoMVias<K,V>nodoActual,K claveABuscar){
        int posicion=this.obtenerPosicionPorDondeBajar(nodoActual, claveABuscar)+1;
        K claveDeRetorno=(K)NodoMVias.datoVacio();
        NodoMVias<K,V>nodoAuxiliar=nodoActual.getHijo(posicion);
        while(!NodoMVias.esNodoVacio(nodoAuxiliar)){
            claveDeRetorno=nodoAuxiliar.getClave(nodoAuxiliar.nroDeClavesNoVacias()-1);
            nodoAuxiliar=nodoAuxiliar.getHijo(0);
        }
           return claveDeRetorno;      
    }

public K buscarPredecesorEnInOrden(NodoMVias<K,V>nodoActual,K claveABuscar){
     K claveDeRetorno=(K)NodoMVias.datoVacio();
     int posicion=this.obtenerPosicionPorDondeBajar(nodoActual, claveABuscar);
     NodoMVias<K,V>nodoAuxiliar=nodoActual.getHijo(posicion);
        while(!NodoMVias.esNodoVacio(nodoAuxiliar)){
            claveDeRetorno=nodoAuxiliar.getClave(0);
            nodoAuxiliar=nodoAuxiliar.getHijo(0);
        }
        return claveDeRetorno;
    }
    

	@Override
	   public V buscarPalabra(K claveABuscar) {
        Queue<NodoMVias<K,V>>colaDeNodos= new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while(!colaDeNodos.isEmpty()){
            NodoMVias<K,V> nodoActual= colaDeNodos.poll();
            for(int i=0; i<nodoActual.nroDeClavesNoVacias(); i++){
               if(claveABuscar.compareTo(nodoActual.getClave(i))==0){
                   return nodoActual.getValor(i);
               } 
               if(!nodoActual.esHijoVacio(i)){
                   colaDeNodos.offer(nodoActual.getHijo(i));
               }
               
            }
          if(!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())){
               colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
          }
        }
       return null;
    }
        
    @Override
    public List<K> recorrido() {
		// TODO Auto-generated method stub
                List<K> recorrido= new ArrayList<>();
		return recorrido;
	}

    @Override
    public void editarPalabra(K claveAEditar, V nuevoValor) {
            Queue<NodoMVias<K,V>>colaDeNodos= new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        
        while(!colaDeNodos.isEmpty()){
            NodoMVias<K,V> nodoActual= colaDeNodos.poll();
            for(int i=0; i<nodoActual.nroDeClavesNoVacias(); i++){
               if(claveAEditar.compareTo(nodoActual.getClave(i))==0){
                   nodoActual.setValor(i,nuevoValor);
               } 
               if(!nodoActual.esHijoVacio(i)){
                   colaDeNodos.offer(nodoActual.getHijo(i));
               }
               
            }
          if(!nodoActual.esHijoVacio(nodoActual.nroDeClavesNoVacias())){
               colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroDeClavesNoVacias()));
          }
        }
    }

    @Override
    public int altura() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }
    

    @Override
    public boolean existeLaPalabra(K clave) {
     return buscarPalabra(clave)!=null;
    }
    
    }
        
 








 






    
    
    


    


    


	
	
   

   

   

       


