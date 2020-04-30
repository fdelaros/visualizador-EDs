package model.data_structures;

import java.util.Iterator;

public class SeparateChainingHash<K , V>implements ITablaHash< K, V >  {

	// -----------------------------------------------------------------
	// Atributos.
	// -----------------------------------------------------------------

	public static final int INIT_CAPACITY = 7;

	private int N; // number of key-value pairs.
	private int M; // hash table size.
	private SequentialSearchST<K, V>[] st; // array of ST objects.

	// -----------------------------------------------------------------
	// Constructor.
	// -----------------------------------------------------------------

	/**
	 * Initializes an empty symbol table.
	 */
	public SeparateChainingHash() {
		this(INIT_CAPACITY);
	} 

	@SuppressWarnings("unchecked")
	public SeparateChainingHash(int m) {
		this.M = m;
		st = (SequentialSearchST<K, V>[]) new SequentialSearchST[m];
		for (int i = 0; i < m; i++)
			st[i] = new SequentialSearchST<K, V>();
	}

	// -----------------------------------------------------------------
	// Metodos.
	// -----------------------------------------------------------------	

	private void resize(int chains) {
		SeparateChainingHash<K, V> temp = new SeparateChainingHash<K, V>(chains);
		for (int i = 0; i < M; i++) {
			for (K key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.M  = temp.M;
		this.N  = temp.N;
		this.st = temp.st;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % M;
	}

	public int size() {
		return N;
	} 

	public boolean isEmpty() {
		return size() == 0;
	}

	public boolean contains(K key) {
		if (key == null)
			throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	} 

	public void put(K key, V value) {
		if (key == null) 
			throw new IllegalArgumentException("first argument to put() is null");
		if (value == null) {
			delete(key);
			return;
		}

		if (N >= 10*M) 
			resize( 2*M );

		int i = hash(key);
		if (!st[i].contains(key)) 
			N++;
		st[i].put(key, value);
	}

	public V get(K key) {
		if (key == null) 
			throw new IllegalArgumentException("argument to get() is null");
		int i = hash(key);
		return st[i].get(key);
	}

	public V delete(K key) {
		if (key == null) 
			throw new IllegalArgumentException("argument to delete() is null");

		int i = hash(key);
		if (st[i].contains(key)) 
			N--;
		st[i].delete(key);

		// halve table size if average length of list <= 2
		if (M > INIT_CAPACITY && N <= 2*M) 
			resize(M/2);
		return st[i].get(key);
	}

	public SequentialSearchST<K, V>[] getSt() {
		return st;
	}

	public int getM() {
		return M;
	}

	public Iterable<K> keys() {
		Queue<K> queue = new Queue<K>();
        for (int i = 0; i < M; i++) {
            for (K key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
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
	@SuppressWarnings({ "unused", "hiding" })
	private class Iterador<K> implements Iterator<K> {
		
		int actual = -1;
		public boolean hasNext() { 
			return true;
		}

		public K next()    { 
			return null;
		}

		public void remove()  { /*no hace nada*/ }
	}
}
