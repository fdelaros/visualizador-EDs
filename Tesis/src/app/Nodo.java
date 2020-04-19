package app;

class Nodo<T> {
	T info;
	String name;
	
	public Nodo(String name) {
		this.name = name;
	}
	
	public void setInfo (T info) {
		this.info = info;
	}
}