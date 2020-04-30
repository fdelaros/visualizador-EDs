package model.data_structures;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>, IList<T> {

	private Node primero;
	private int N;

	/**
	 * Añade un item al comienzo de la lista.
	 * @param item Objeto que se quiere agregar
	 */
	public void add( T item ) {
		Node nuevo = new Node();
		nuevo.item = item;
		nuevo.next = primero;
		primero = nuevo;
		N++;
	}

	/**
	 * Retorna el número de elementos en la lista.
	 * @return
	 */
	public int size() {
		return N;
	}

	public boolean isEmpty() {
		return N == 0;
	}

	/**
	 * Retorna un iterador de la lista;
	 */
	@Override
	public Iterator<T> iterator() {
		return new ListIterator();
	}

	private class Node {
		T item;
		Node next;
	}

	private class ListIterator implements Iterator<T>{
		private Node actual = primero;

		public boolean hasNext() {
			return actual != null;
		}
		public T next() {
			T item = actual.item;
			actual = actual.next;
			return item;
		}
		public void remove() {
			//No se implementa
		}
	}

	public T get(T elem) {
		T actual = primero.item;
		while( actual != null ) {
			if( actual.equals( elem ) ) {
				return actual;
			}
			actual = primero.next.item;
		}
		return null;
	}

	public void cambiarUltimo(T item) {
		Node nuevo = new Node();
		nuevo.item = item;

		Node actual = primero;
		Node siguiente = primero.next;
		while(siguiente!=null) {
			actual = actual.next;
			siguiente = actual.next;
		}
		actual.next = nuevo;
		N++;
	}

	@Override
	public T get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
