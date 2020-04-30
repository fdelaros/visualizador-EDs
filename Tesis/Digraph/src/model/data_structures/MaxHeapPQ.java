package model.data_structures;

import java.util.Comparator;

public class MaxHeapPQ <T extends Comparable<T>>{

	private T[] elementos;

	private int tamanio;

	private Comparator<T> comparador;

	/**
	 * Crea un objeto de la clase con una capacidad maÌ�xima de elementos pero vaciÌ�a
	 * @param max Capacidad mÃ¡xima de elementos
	 */
	@SuppressWarnings("unchecked")
	public MaxHeapPQ( int max ) {

		tamanio = 0;
		elementos = (T[]) new Comparable[ max + 1 ];
		comparador = null;

	}

	/**
	 * 
	 * @param max
	 */
	@SuppressWarnings("unchecked")
	public MaxHeapPQ( int max , Comparator<T> comp) {

		tamanio = 0;
		elementos = (T[]) new Comparable[ max + 1 ];
		comparador = comp;

	}

	/**
	 * Retorna nuÌ�mero de elementos presentes en la cola de prioridad.
	 */
	public int darNumElementos() {
		return tamanio;
	}

	/**
	 * Agrega un elemento a la cola. Se genera Exception en el caso que se sobrepase el tamanÌƒo maÌ�ximo de la cola
	 */
	public void agregar(T elemento) throws Exception {

		if( tamanio == elementos.length - 1 ) {
			//resize( (elementos.length * 2) );
			throw new Exception( "Imposible agregar. Se ha superado el tamaÃ±o mÃ¡ximo de la cola. Tamano = " + tamanio + ". Max = " + (elementos.length - 1));
		}

		elementos[++tamanio] = elemento;

		if( comparador != null) swim( tamanio, comparador);
		else swim( tamanio );

	}

	/**
	 * Saca/atiende el elemento maÌ�ximo en la cola y lo retorna; null en caso de cola vaciÌ�a
	 * @return Elemento de mÃ¡xima prioridad de la cola.
	 */
	public T max() {

		T max = elementos[1];
		exch(1, tamanio--);
		elementos[tamanio+1] = null;

		if( comparador != null) sink( 1, comparador);
		else sink( 1 ); 

		return max;

	}

	/**
	 * Retorna si la cola estaÌ� vaciÌ�a o no.
	 * @return True si la cola estÃ¡ vacÃ­a, false de lo contrario.
	 */
	public boolean esVacia() {
		return tamanio == 0;
	}

	/**
	 * Retorna la capacidad maÌ�xima de la cola.
	 * @return tamaÃ±o mÃ¡ximo de la cola.
	 */
	public int tamanoMax() {
		return elementos.length - 1;
	}

	/**
	 * 
	 * @param k
	 */
	private void swim( int k ) {

		while (k > 1 && elementos[k/2].compareTo( elementos[k] ) < 0)
		{
			exch(k/2, k);
			k = k/2;
		}

	}

	/**
	 * 
	 * @param k
	 */
	private void swim( int k , Comparator<T> comp ) {

		while (k > 1 && comp.compare(elementos[k/2], elementos[k]) < 0)
		{
			exch(k/2, k);
			k = k/2;
		}

	}

	/**
	 * 
	 * @param k
	 */
	private void sink( int k ) {

		while (2*k <= tamanio)
		{
			int j = 2*k;

			if ( j < tamanio && elementos[j].compareTo(elementos[j+1]) < 0 )
				j++;

			if ( elementos[k].compareTo(elementos[j]) < 0 ) {
				exch(k, j);
				k = j;
			} else {
				break;
			}

		}

	}

	/**
	 * 
	 * @param k
	 */
	private void sink( int k, Comparator<T> comp ) {

		while (2*k <= tamanio)
		{
			int j = 2*k;

			if ( j < tamanio && comp.compare(elementos[j], elementos[j+1]) < 0 )
				j++;

			if ( comp.compare(elementos[k], elementos[j]) < 0 ) {
				exch(k, j);
				k = j;
			} else {
				break;
			}

		}

	}

	/**
	 * 
	 * @param i
	 * @param j
	 */
	private void exch(int i, int j) {
		T t = elementos[i];
		elementos[i] = elementos[j];
		elementos[j] = t; 
	}

	/**
	 * 
	 * @param newMax
	 */
	@SuppressWarnings("unused")
	private void resize( int newMax) {

	}



}
