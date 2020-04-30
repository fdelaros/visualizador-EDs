package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;

public class ArregloFlexible <T extends Comparable<T>> implements IArregloFlexible<T>, Iterable<T> {

	private T[] elementos;

	private int tamanoMax;

	private int tamanoAct;

	@SuppressWarnings("unchecked")
	public ArregloFlexible( ) {
		elementos = (T[]) new Comparable[10];
		tamanoMax = 10;
		tamanoAct = 0;
	}


	@SuppressWarnings("unchecked")
	public ArregloFlexible (int max) {
		elementos = (T[]) new Comparable[max];
		tamanoMax = max;
		tamanoAct = 0;
	}

	public int size() {
		return tamanoAct;
	}

	public int darTamanoMax() {
		return tamanoMax;
	}


	public boolean isEmpty() {
		return tamanoAct == 0;
	}


	@SuppressWarnings("unchecked")
	public void add(T obj) {
		if ( tamanoAct == tamanoMax )
		{ // caso de arreglo lleno (aumentar tamanio)
			tamanoMax = 2 * tamanoMax;
			T [ ] copia = elementos;
			elementos = (T [ ]) new Comparable[tamanoMax];
			for ( int i = 0; i < tamanoAct; i++)
			{
				elementos[i] = copia[i];
			}
		}
		elementos[tamanoAct] = obj;
		tamanoAct++;
	}


	public void addInOrder( T obj ) {
		add(obj);

		for (int j = tamanoAct-1; j > 0 && elementos[j].compareTo(elementos[j-1]) < 0; j--) {
			T temp = elementos[j];
			elementos[j] = elementos[j-1];
			elementos[j-1] = temp;
		}
	}


	public void addAtK(int k, T obj) {
		add(obj);
		for (int j = tamanoAct-1; j > k; j--) {
			T temp = elementos[j];
			elementos[j] = elementos[j-1];
			elementos[j-1] = temp;
		}
	}


	public T[] darElementos() {
		return elementos;
	}


	public T get(int i) {
		return elementos[i];
	}


	public T get(T elem) {
		//  Completar
		return null;
	}

	public void set(int i, T obj){
		elementos[i] = obj;
	}

	public T remove(T elem) {
		//  Completar
		return null;
	}


	public void deleteAtK(int k) {
		for(int i = k + 1 ; i < tamanoAct ; i++) {
			elementos[i - 1] = elementos[i];
		}
		elementos[--tamanoAct] = null;
	}

	public T buscarBin( T comparar ) {

		int inicio = 0;
		int fin = tamanoAct - 1;

		while( inicio <= fin ) {
			int medio = (inicio + fin )/2;
			if( elementos[medio].compareTo( comparar ) == 0 )  {
				return elementos[medio];
			} else if( elementos[medio].compareTo( comparar ) > 0) {
				fin = medio - 1;
			} else {
				inicio = medio + 1;
			}
		}


		return null;

	}

	public T buscarBin( T comparar , Comparator<T> comp ) {

		int inicio = 0;
		int fin = tamanoAct - 1;

		while( inicio <= fin ) {
			int medio = (inicio + fin )/2;
			if( comp.compare( elementos[medio], comparar ) == 0 )  {
				return elementos[medio];
			} else if( comp.compare( elementos[medio], comparar ) > 0) {
				fin = medio - 1;
			} else {
				inicio = medio + 1;
			}
		}


		return null;

	}

	public Iterator<T> iterator() {
		return new Iterador();
	}

	/**
	 * Devuelve una copia de un arreglo organizado por shellsort.
	 * @param original Arreglo del cual se quiere obtener una copia ordenada.
	 */
	public static <T extends Comparable<T>> void shellSort( ArregloFlexible<T> a ) {

		int h = 1;
		while (h < a.size()/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...

		while (h >= 1)
		{  // h-sort the array.
			for (int i = h; i < a.size(); i++)
			{  // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
				for (int j = i; j >= h && a.get(j).compareTo( a.get(j-h) ) < 0; j -= h)
					a.exchange(j, j-h);
			}
			h = h/3; 
		}
	}

	/**
	 * Devuelve una copia de un arreglo organizado por shellsort.
	 * @param original Arreglo del cual se quiere obtener una copia ordenada.
	 */
	public static <T extends Comparable<T>> void shellSort( ArregloFlexible<T> a, Comparator<T> comp ) {

		int h = 1;
		while (h < a.size()/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...

		while (h >= 1)
		{  // h-sort the array.
			for (int i = h; i < a.size(); i++)
			{  // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
				for (int j = i; j >= h && comp.compare(a.get(j), a.get(j-h) ) < 0; j -= h)
					a.exchange(j, j-h);
			}
			h = h/3; 
		}
	}

	public static <T extends Comparable<T>> void quickSort( ArregloFlexible<T> arr ){
		//shuffle???
		quickSort( arr, 0, arr.size()-1);
	}

	/**
	 * @param arr Arreglo a organizar
	 * @param low Starting index
	 * @param high Ending index
	 */
	private static <T extends Comparable<T>> void quickSort( ArregloFlexible<T> arr, int low, int high){

		//System.out.println(high);
		//System.out.println(low);
		if (low < high)	{
			int pi = partition(arr, low, high);

			quickSort(arr, low, pi - 1);  // Before pi
			quickSort(arr, pi + 1, high); // After pi
		}

	}

	/**
	 *  Toma el ultimo elemento como pivot, ubica el pivot en la posicion correcta y ubica los menores a la
	 *  izquierda y los mayores a la derecha.
	 * @param arr Arreglo a organizar
	 * @param low Starting index
	 * @param high Ending index
	 * @return posici�n del pivot 
	 */
	private static <T extends Comparable<T>> int partition(ArregloFlexible<T> arr, int low, int high){
		T pivot = arr.get(high);
		int i = (low-1); // index of smaller element
		for (int j=low; j<high; j++) {
			// If current element is smaller than or equal to pivot
			if (arr.get(j).compareTo(pivot)<0) {
				i++;
				// swap arr[i] and arr[j]
				T temp = arr.get(i);
				arr.set(i, arr.get(j));
				arr.set(j, temp);
			}
		}
		// swap arr[i+1] and arr[high] (or pivot)
		T temp = arr.get(i+1);
		arr.set(i+1, arr.get(high));
		arr.set(high, temp);
		return i+1;
	} 

	public static <T extends Comparable<T>> void quickSort( ArregloFlexible<T> arr, Comparator<T> comp ){
		//shuffle???
		quickSort( arr, 0, arr.size()-1, comp);
	}

	/**
	 * @param arr Arreglo a organizar
	 * @param low Starting index
	 * @param high Ending index
	 */
	private static <T extends Comparable<T>> void quickSort( ArregloFlexible<T> arr, int low, int high, Comparator<T> comp){

		//System.out.println(high);
		//System.out.println(low);
		if (low < high)	{
			int pi = partition(arr, low, high, comp );

			quickSort(arr, low, pi - 1, comp);  // Before pi
			quickSort(arr, pi + 1, high, comp); // After pi
		}

	}

	/**
	 *  Toma el ultimo elemento como pivot, ubica el pivot en la posicion correcta y ubica los menores a la
	 *  izquierda y los mayores a la derecha.
	 * @param arr Arreglo a organizar
	 * @param low Starting index
	 * @param high Ending index
	 * @return posici�n del pivot 
	 */
	private static <T extends Comparable<T>> int partition(ArregloFlexible<T> arr, int low, int high, Comparator<T> comp){
		T pivot = arr.get(high);
		int i = (low-1); // index of smaller element
		for (int j=low; j<high; j++) {
			// If current element is smaller than or equal to pivot
			if ( comp.compare( arr.get(j) , pivot )  <0) {
				i++;
				// swap arr[i] and arr[j]
				T temp = arr.get(i);
				arr.set(i, arr.get(j));
				arr.set(j, temp);
			}
		}
		// swap arr[i+1] and arr[high] (or pivot)
		T temp = arr.get(i+1);
		arr.set(i+1, arr.get(high));
		arr.set(high, temp);
		return i+1;
	} 
	
	
	public static <T extends Comparable<T>> void mergeSort (ArregloFlexible<T> arr, int l, int r) 
	{ 
		if (l < r){ 
			int m = (l+r)/2; 

			mergeSort(arr, l, m ); 
			mergeSort(arr , m+1, r ); 

			merge(arr, l, m, r ); 
		} 
	} 
	
	private static <T extends Comparable<T>> void merge( ArregloFlexible<T> arr, int l, int m, int r ) { 
		int n1 = m - l + 1; 
		int n2 = r - m; 

		ArregloFlexible<T> L = new ArregloFlexible<T> (n1); 
		ArregloFlexible<T> R = new ArregloFlexible<T> (n2); 

		for (int i=0; i<n1; ++i) 
			L.addAtK(i, arr.get(l + i)); 
		for (int j=0; j<n2; ++j) 
			R.addAtK(j, arr.get(m + 1 + j)); 

		int i = 0, j = 0, k = l; 

		while (i < n1 && j < n2){ 
			if (0 < L.get(i).compareTo( R.get(j) ) ){ 
				arr.addAtK(k, L.get(i));
				i++; 
			} 
			else{ 
				arr.addAtK(k, R.get(j));
				j++; 
			} 
			k++; 
		} 

		while (i < n1){ 
			arr.addAtK(k, L.get(i));
			i++; 
			k++; 
		} 

		while (j < n2){ 
			arr.addAtK(k, R.get(j));
			j++; 
			k++; 
		} 
	}
	
	
	public static <T extends Comparable<T>> void mergeSort (ArregloFlexible<T> arr, int l, int r,Comparator<T> comp) 
	{ 
		if (l < r){ 
			int m = (l+r)/2; 

			mergeSort(arr, l, m, comp); 
			mergeSort(arr , m+1, r, comp); 

			merge(arr, l, m, r, comp); 
		} 
	} 
	
	private static <T extends Comparable<T>> void merge( ArregloFlexible<T> arr, int l, int m, int r, Comparator<T> comp) { 
		int n1 = m - l + 1; 
		int n2 = r - m; 

		ArregloFlexible<T> L = new ArregloFlexible<T> (n1); 
		ArregloFlexible<T> R = new ArregloFlexible<T> (n2); 

		for (int i=0; i<n1; ++i) 
			L.addAtK(i, arr.get(l + i)); 
		for (int j=0; j<n2; ++j) 
			R.addAtK(j, arr.get(m + 1 + j)); 

		int i = 0, j = 0, k = l; 

		while (i < n1 && j < n2){ 
			if (0<comp.compare(L.get(i), R.get(j)) ){ 
				arr.addAtK(k, L.get(i));
				i++; 
			} 
			else{ 
				arr.addAtK(k, R.get(j));
				j++; 
			} 
			k++; 
		} 

		while (i < n1){ 
			arr.addAtK(k, L.get(i));
			i++; 
			k++; 
		} 

		while (j < n2){ 
			arr.addAtK(k, R.get(j));
			j++; 
			k++; 
		} 
	}

	/**
	 * 
	 * @param pos1
	 * @param pos2
	 */
	private void exchange(int pos1, int pos2) {
		T temp = elementos[pos1];
		elementos[pos1] = elementos[pos2];
		elementos[pos2] = temp;
	}

	/**
	 * Devuelve un nuevo arreglo con los mismos objetos.
	 */
	@Override
	public ArregloFlexible<T> clone( ){
		ArregloFlexible<T> copia = new ArregloFlexible<T>( tamanoAct );

		for( int i = 0 ; i < tamanoAct ; i++ ) {
			copia.add( elementos[i] );
		}

		return copia;
	}


	// -----------------------------------------------------------------
	// Subclases
	// -----------------------------------------------------------------

	/**
	 * Clase privada que implementa el Iterator.
	 */
	private class Iterador implements Iterator<T> {
		int actual = -1;
		public boolean hasNext() {  return actual < tamanoAct - 1;  }
		public T next()    { 
			return elementos[++actual];
		}
		public void remove()  { /*no hace nada*/ }
	}
}
