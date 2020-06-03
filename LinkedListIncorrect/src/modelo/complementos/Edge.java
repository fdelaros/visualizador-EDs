package modelo.complementos;

/**
 * Clase requerida para retornar los arcos
 */
public class Edge<K>{
	public K start;
	public K end;
	
	public Edge(K inicio, K fin) {
		start = inicio;
		end = fin;
	}
	public Edge() {
		
	}
}