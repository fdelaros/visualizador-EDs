package modelo.complementos;

/**
 * Clase requerida para retornar los arcos
 * Se entiende como arco a la relación entre un par de nodos para los grafos.
 * Se entiende como conexión a la relación entre un par de nodos para los árboles y listas encadenadas.
 */
public class Edge<K>{
	public K start;
	public K end;
	
	/**
	 * Constructor del arco/conexión
	 * @param inicio Objeto inicial de la relación, es un nodo (implementación del estudiante que contiene el método toString para las etiquetas) o String
	 * @param fin Objeto final de la relación, es un nodo (implementación del estudiante que contiene el método toString para las etiquetas) o String
	 */
	public Edge(K inicio, K fin) {
		start = inicio;
		end = fin;
	}
	public Edge() {
		
	}
}