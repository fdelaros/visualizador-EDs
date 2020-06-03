package modelo.estructuras;

import java.util.Iterator;


public class Stack<T> implements IPila<T> {
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Primer nodo de la pila.
	 */
	private Nodo primerNodo;

	/**
	 * Tamanio de la pila.
	 */
	private int size;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Construye una pila vacía.
	 */
	public Stack(){
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

		/*Nodo actual = primerNodo;
		int longitud = 0;
		while( actual != null ) {
			longitud++;
			actual = actual.next;
		}
		return longitud;*/

		return size;

	}

	/**
	 * Pone el objeto pasado por parámetro en la pila.
	 * @param t Objeto que se quiere añadir a la pila.
	 */
	public void push(T obj) {
		Nodo nuevoNodo = new Nodo( obj );
		if( primerNodo == null ) {
			primerNodo = nuevoNodo;
		} else {
			nuevoNodo.next = primerNodo;
			primerNodo = nuevoNodo;
		}
		size++;
	}

	/**
	 * Retorna y elimina primer elemento de la pila.
	 * @return primer elemento en pila.
	 */
	public T pop() {
		Nodo pop = primerNodo;
		primerNodo = primerNodo.next;
		size--;
		return pop.item;
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
	 * Clase nodo.
	 */
	private class Nodo {
		private T item;
		private Nodo next;

		public Nodo(T obj) {
			item = obj;
		}

	}
}
