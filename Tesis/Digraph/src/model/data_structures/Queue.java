package model.data_structures;

import java.util.Iterator;

public class Queue<T> implements ICola<T> {

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Primer nodo de la cola.
	 */
	private Nodo primerNodo;

	/**
	 * Último nodo de la cola.
	 */
	private Nodo ultimoNodo;

	/**
	 * Tamanio de la cola.
	 */
	private int size;

	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------	

	/**
	 * Construye una cola vacía.
	 */
	public Queue() {
		size = 0;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Revisa si la lista está vacía.
	 * @return True si está vacía, false de lo contrario.
	 */
	public boolean isEmpty() {
		return primerNodo == null;
	}

	/**
	 * Retorna el tamaño de la lista.
	 * @return size
	 */
	public int getSize() {
		/*int tamanio = 0;
		Nodo actual = primerNodo;

		while(actual != null) {
			tamanio++;
			actual = actual.next;
		}*/
		return size;
	}

	/**
	 * Pone el objeto pasado por parámetro en la cola.
	 * @param t Objeto que se quiere añadir a la cola.
	 */
	public void enqueue(T t) {
		Nodo nuevoNodo = new Nodo( t );
		if( primerNodo == null ) {
			primerNodo = nuevoNodo;
			ultimoNodo = nuevoNodo;
		} else {
			ultimoNodo.next = nuevoNodo;
			ultimoNodo = nuevoNodo;
		}
		size++;

	}

	/**
	 * Retorna y elimina primer elemento de la cola.
	 * @return primer elemento en cola.
	 */
	public T dequeue() {
		Nodo paraBorrar = primerNodo;
		primerNodo = paraBorrar.next;
		size--;
		return paraBorrar.item;
	}

	/**
	 * Retorna un iterador.
	 * @return iterator
	 */
	public Iterator<T> iterator() {
		return new Iterador();
	}

	// -----------------------------------------------------------------
	// Subclases
	// -----------------------------------------------------------------

	/**
	 * Clase privada que implementa el Iterator.
	 */
	private class Iterador implements Iterator<T> {
		private Nodo actual = primerNodo;
		public boolean hasNext() {  return actual != null ;  }
		public T next()    { 
			T devolver = actual.item;
			actual = actual.next;
			return devolver;  
		}
		public void remove()  { /*no hace nada*/ }
	}


	/**
	 * Clase privada que representa los nodos de la cola.
	 */
	private class Nodo {
		private T item;
		private Nodo next;

		public Nodo( T pItem ) {
			item = pItem;
		}
	}

}
