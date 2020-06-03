package modelo.estructuras;


public interface IArregloFlexible <T extends Comparable<T>> extends Iterable<T>, IList<T> {

	void addAtK( int k , T obj );

	void deleteAtK( int k );

	T[] darElementos();
	
	int darTamanoMax();

	public void set(int i, T obj);

}
