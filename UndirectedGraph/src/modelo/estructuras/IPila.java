package modelo.estructuras;

import java.util.Iterator;

public interface IPila<T> extends Iterable<T> {

    public void push(T elem);

    public T pop();

    public int getSize();
    
    public boolean isEmpty();

    @Override
    public Iterator<T> iterator();
}
