package model.data_structures;

import java.util.Iterator;

public class LinearProbingHash<K , V> implements Iterable<K>, ITablaHash< K, V >  {

	//private static final double FACTOR_CARGA_MAX = 0.75;
	private static final int CAPACIDAD_INICIAL = 17; 

	private int N;
	private int M;
	private K[] keys;
	private V[] values;

	/**
	 * Crea una tabla de hash con capacidad inicial de 17 posiciones.
	 */
	public LinearProbingHash( ) {
		this(CAPACIDAD_INICIAL);
	}

	/**
	 * Crea una tabla de hash con capacidad inicial de m posiciones.
	 * @param m
	 */
	@SuppressWarnings("unchecked")
	public LinearProbingHash( int m ) {

		N = 0;
		M = m;

		values = (V[]) new Object[M];
		keys = (K[]) new Object[M];
	}

	/**
	 * Agrega una dupla (K, V) a la tabla. Si la llave K existe, se reemplaza su valor V asociado. V no puede ser null.
	 * @param key Llave
	 * @param value Valor asociado
	 */
	public void put( K key, V value) {
		if (key == null) 
			throw new IllegalArgumentException("first argument to put() is null");
		if (value == null) {
			delete(key);
			return;
		}
		//Duplicar tamaño
		/*if( ( (double)M / (N+1) ) >= FACTOR_CARGA_MAX ) 
			rehash( 2*M );
*/
		if( N >= M/2 )
			rehash( 2*M );
		int i;
		for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if ( keys[i].equals(key) )  {
				values[i] = value; 
				return;
			}
		}
		keys[i] = key;
		values[i] = value;
		N++;
	}

	/**
	 * Obtener el valor V asociado a la llave K. V no puede ser null.
	 * @param key Llave del valor que se quiere encontrar.
	 */
	@Override
	public V get( K key) {
		if (key == null) 
			throw new IllegalArgumentException("argument to get() is null");

		for ( int i = hash(key); keys[i] != null; i = (i + 1) % M ) {
			if (keys[i].equals(key)) 
				return values[i];
		}
		return null;
	}

	public V delete( K key) {
		if (key == null) 
			throw new IllegalArgumentException("argument to delete() is null");

		if (!contains(key)) 
			return null;

		int i = hash(key);
		while (!key.equals(keys[i])) {
			i = (i + 1) % M;
		}

		V resp = values[i];

		keys[i] = null;
		values[i] = null;
		i = (i + 1) % M;
		while (keys[i] != null){
			K   keyToRedo = keys[i];
			V valToRedo = values[i];
			keys[i] = null;
			values[i] = null;
			N--;
			put(keyToRedo, valToRedo);
			i = (i + 1) % M;
		}

		N--;

		return resp;
		//if ( N > 0 N == M/8 ) rehash(M/2);
	}

	@Override
	public Iterable<K> keys() {
		Queue<K> queue = new Queue<K>();
		for (int i = 0; i < M; i++)
			if (keys[i] != null) queue.enqueue(keys[i]);
		return (Iterable<K>) queue;
	}

	private int hash( K key ) {  
		return ( key.hashCode() & 0x7fffffff ) % M; 
	}

	private void rehash( int nuevoTam ) {
		LinearProbingHash<K, V> t = new LinearProbingHash<K, V>( nuevoTam );
		for (int i = 0; i < M; i++)
			if (keys[i] != null)
				t.put(keys[i], values[i]);
		keys = t.keys;
		values = t.values;
		M    = t.M;
	}

	public int giveM() {
		return M;
	}

	public int size() {
		return N;
	}

	private boolean contains( K key ) {

		for ( int i = hash(key); keys[i] != null; i = (i + 1) % M ) {
			if (keys[i].equals(key)) 
				return true;
		}
		return false;
	}

	public K[] getKeys() {
		return keys;
	}

	public int nextPrime(int input){
		int counter;
		input++;   
		while(true){
			counter = 0;
			for(int i = 2; i <= Math.sqrt(input); i ++){
				if(input % i == 0)  counter++;
			}
			if(counter == 0)
				return input;
			else{
				input++;
				continue;
			}
		}
	}

	
	/**
	 * Clase privada que implementa el Iterator.
	 */
	@SuppressWarnings("hiding")
	private class Iterador<K> implements Iterator<K> {
		int actual = 0;
		public boolean hasNext() { 
			int i = actual ;
			return i <= M &&  keys.length>actual+1;
		}
		@SuppressWarnings("unchecked")
		public K next(){ 
			actual++;
			while(keys[actual] != null) {
				actual++;
			}
			return (K) keys[actual];
		}
		public void remove()  { /*no hace nada*/ }
	}

	@Override
	public Iterator<K> iterator() {
		return new Iterador<K>();
	}


}
