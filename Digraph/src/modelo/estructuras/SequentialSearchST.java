package modelo.estructuras;

public class SequentialSearchST<K, V> {

	// -----------------------------------------------------------------
	// Atributos.
	// -----------------------------------------------------------------

	private int n;           // number of key-value pairs
	private Nodo primero;      // the linked list of key-value pairs

	// -----------------------------------------------------------------
	// Constructor.
	// -----------------------------------------------------------------

	/**
	 * Initializes an empty symbol table.
	 */
	public SequentialSearchST() {
	}

	// -----------------------------------------------------------------
	// Metodos.
	// -----------------------------------------------------------------

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns true if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns true if this symbol table contains the specified key.
	 *
	 * @param  key the key
	 * @return {@code true} if this symbol table contains {@code key};
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(K key) {
		if (key == null) 
			throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	}

	/**
	 * Returns the value associated with the given key in this symbol table.
	 *
	 * @param  key the key
	 * @return the value associated with the given key if the key is in the symbol table
	 *     and {@code null} if the key is not in the symbol table
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public V get(K key) {
		if (key == null) 
			throw new IllegalArgumentException("argument to get() is null"); 
		for (Nodo x = primero; x != null; x = x.siguiente) {
			if (key.equals(x.key))
				return x.val;
		}
		return null;
	}

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is {@code null}.
	 *
	 * @param  key the key
	 * @param  val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(K key, V val) {
		if (key == null) 
			throw new IllegalArgumentException("first argument to put() is null"); 
		if (val == null) {
			delete(key);
			return;
		}

		for (Nodo x = primero; x != null; x = x.siguiente) {
			if (key.equals(x.key)) {
				x.val = val;
				return;
			}
		}
		primero = new Nodo(key, val, primero);
		n++;
	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(K key) {
		if (key == null) 
			throw new IllegalArgumentException("argument to delete() is null"); 
		primero = delete(primero, key);
	}

	/**
	 *  delete key in linked list beginning at Node x
	 *   warning: function call stack too large if table is large
	 */
	private Nodo delete(Nodo x, K key) {
		if (x == null) return null;
		if (key.equals(x.key)) {
			n--;
			return x.siguiente;
		}
		x.siguiente = delete(x.siguiente, key);
		return x;
	}

	/**
	 * Returns all keys in the symbol table as an {@code Iterable}.
	 * To iterate over all of the keys in the symbol table named {@code st},
	 * use the foreach notation: {@code for (Key key : st.keys())}.
	 *
	 * @return all keys in the symbol table
	 */
	public Iterable<K> keys()  {
		Queue<K> queue = new Queue<K>();
		for (Nodo x = primero; x != null; x = x.siguiente)
			queue.enqueue(x.key);
		return queue;
	}

	// -----------------------------------------------------------------
	// Subclases
	// -----------------------------------------------------------------

	/**
	 * Clase privada que representa los nodos de la cola.
	 */
	private class Nodo {
		private K key;
		private V val;
		private Nodo siguiente;

		public Nodo(K key, V val, Nodo next)  {
			this.key  = key;
			this.val  = val;
			this.siguiente = next;
		}
	}
}