package modelo.estructuras;

public interface ITablaHash<K , V> {

	public void put(K key, V value);

	public V get( K key );

	public V delete( K key );

	public Iterable<K> keys();

}
