package model.data_structures;

public interface IList<T> extends Iterable<T>{

	void add(T elem);

	int size();

	T get(T elem);

	T get(int i);
}

