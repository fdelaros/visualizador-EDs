package model.data_structures;

import java.util.concurrent.SynchronousQueue;

public class MinPriorityQueue< K, P extends Comparable<P> > {

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------	

	private static final int MAX_DEFAULT = 5000;

	/**
	 * Prioridad de cada llave
	 */
	private LinearProbingHash<K, P> prioridades;

	/**
	 * Llaves ordenadas en un heap por criterio de sus prioridades P en prioridades
	 */
	private K[] pq;

	/**
	 * Posición de cada llave
	 */
	private LinearProbingHash<K, Integer> posiciones;

	/**
	 * Cantidad de elementos en el PQ
	 */
	private int tamanio;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Crea un objeto de la clase con una capacidad maÌ�xima de elementos pero vaciÌ�a
	 * @param max Capacidad mÃ¡xima de elementos
	 */
	@SuppressWarnings("unchecked")
	public MinPriorityQueue(  ) {
		tamanio = 0;
		//elementos = (P[]) new Comparable[ MAX_DEFAULT + 1 ];

		pq = (K[]) new Object[ MAX_DEFAULT + 1 ];
		posiciones = new LinearProbingHash<K,Integer>();
		prioridades = new LinearProbingHash<K, P>();
	}

	/**
	 * Crea un objeto de la clase con una capacidad maÌ�xima de elementos pero vaciÌ�a
	 * @param max Capacidad mÃ¡xima de elementos
	 */
	@SuppressWarnings("unchecked")
	public MinPriorityQueue( int max ) {
		tamanio = 0;
		//elementos = (P[]) new Comparable[ max + 1 ];
		pq = (K[]) new Object[ max + 1 ];

		pq = (K[]) new Object[ MAX_DEFAULT + 1 ];
		posiciones = new LinearProbingHash<K,Integer>();
		prioridades = new LinearProbingHash<K, P>();
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Retorna nuÌ�mero de elementos presentes en la cola de prioridad.
	 */
	public int size() {
		return tamanio;
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
		return pq.length - 1;
	}

	/**
	 * 
	 */
	public boolean contiene( K k ) {
		boolean retorno = false;
		if(posiciones.get(k) != null) {
			if (posiciones.get(k) != 0) {
				retorno = true;
			}
		}
		return retorno;
		//		return posiciones.get(k) != 0 && posiciones.get(k) != null;
	}

	/**
	 * Agrega un elemento a la cola. Se genera Exception en el caso que se sobrepase el tamanÌƒo maÌ�ximo de la cola
	 */
	public void agregar(K key , P elemento) {
		if( tamanio == pq.length - 1 ) {
			resize( (pq.length * 2) );
		}
		//elementos[++tamanio] = elemento;
		pq[++tamanio] = key;
		prioridades.put(key, elemento);
		posiciones.put(key, tamanio);

		swim( tamanio );
	}

	/**
	 * Saca/atiende el elemento maÌ�ximo en la cola y lo retorna; null en caso de cola vaciÌ�a
	 * @return Elemento de mÃ¡xima prioridad de la cola.
	 */
	public K delMin() {
		K min = pq[1];
		exch(1, tamanio--);

		//elementos[tamanio+1] = null;

		posiciones.delete( min );
		prioridades.delete( min );
		pq[ tamanio+1 ] = null;

		sink( 1 ); 

		return min;
	}

	/**
	 * Retorna la llave del elemento con menor prioridad
	 */
	public K minKey() {
		return pq[1];
	}

	/**
	 * Retorna la menor prioridad
	 */
	public P minPriority() {
		return prioridades.get( pq[1] );
	}

	/**
	 * Cambia la prioridad de la llave k a p
	 * @param k Llave a la que se le desea cambiar su prioridad
	 * @param p Nueva prioridad
	 */
	public void cambiarPrioridad( K k, P p) {
		if( contiene(k) ) {
			prioridades.put( k , p );
			swim( posiciones.get(k) );
			sink( posiciones.get(k) );
		}

	}

	public void deleteKey( K k ) {

		if( contiene(k) ) {
			int pos = posiciones.get(k);
			exch( pos, tamanio-- );

			//elementos[tamanio+1] = null;

			posiciones.delete( k );
			prioridades.delete( k );
			pq[ tamanio+1 ] = null;

			swim(pos);
			swim(pos);
		}

	}

	// -----------------------------------------------------------------
	// Métodos auxiliares
	// -----------------------------------------------------------------

	/**
	 * 
	 * @param k
	 */
	private void swim( int k ) {

		while (k > 1 && prioridades.get(pq[k/2]).compareTo( prioridades.get(pq[k]) ) > 0)
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

			if(pq[j]!=null) {
				if(pq[j+1]!=null) {
					if ( j < tamanio && prioridades.get(pq[j]).compareTo( prioridades.get(pq[j+1]) ) > 0 )
						j++;
				} 
				if ( prioridades.get(pq[k]).compareTo( prioridades.get(pq[j]) ) > 0 ) {
					exch(k, j);
					k = j;
				} else {
					break;
				}
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
		//P t = elementos[i];
		//elementos[i] = elementos[j];
		//elementos[j] = t; 

		K kSwap = pq[i];
		pq[i] = pq[j];
		pq[j] = kSwap;
		posiciones.put(pq[i], i);
		posiciones.put(pq[j], j);
	}

	/**
	 * 
	 * @param newMax
	 */
	@SuppressWarnings({ "unchecked" })
	private void resize( int newMax) {
		//P [ ] copiaElem = elementos;
		K [ ] copiaPQ = pq;
		//elementos = (P [ ]) new Comparable[ newMax ];
		pq = (K [ ]) new Object[ newMax ];

		for ( int i = 0; i < tamanio; i++)
		{
			//elementos[i] = copiaElem[i];
			pq[i] = copiaPQ[i];
		}
	}

}
