package modelo.estructuras;

import java.util.Iterator;

public interface ICola<T> extends Iterable<T>{

	public void enqueue(T elem);

	public T dequeue();
    
	public int getSize();
    
	public boolean isEmpty();

    @Override
    public Iterator<T> iterator();
}
