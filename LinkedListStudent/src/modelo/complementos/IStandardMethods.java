package modelo.complementos;

import java.util.ArrayList;

/**
 * Interfaz para la implementación de los métodos requeridos en el visualizador
 * Es necesario que haya un objeto estático de la estructura de datos, el cual se usa para llamar las funcionalidades implementadas en los métodos
 * definidos en esta interface.
 * Se entiende como arco a la relación entre un par de nodos para los grafos.
 * Se entiende como conexión a la relación entre un par de nodos para los árboles y listas encadenadas.
 * @author Diana Marcela Gutiérrez
 *Los nodos de la estructura deben implementar un método toString() que retorne el valor que aparecerá en la etiqueta (única) que representa dicho nodo.
 * @param <K>
 */
public interface IStandardMethods<K> {

	void createStructure();

	/**
	 * Nodos en la estructura
	 * @return Lista con objetos tipos nodo (implementación del estudiante que contiene el método toString para las etiquetas) o Strings
	 */
	ArrayList<K> getNodeList();

	/**
	 * Arcos/conexiones en la estructura
	 * @return Lista con objetos tipo Edge (dada al estudiante)
	 * @return null si es lista encadenada
	 */
	ArrayList<Edge<K>> getEdgesList();

	/**
	 * Búsqueda de nodo
	 * @param findTag etiqueta asociada al nodo que se va a buscar
	 * @return Objeto tipo nodo o String correspondiente al resultado de la búsqueda
	 */
	Object findNode(String findTag);

	/**
	 * Búsqueda de arco/conexión
	 * @param startNode etiqueta asociada al nodo inicial
	 * @param endNode etiqueta asociada al nodo final
	 * @return Objeto tipo Edge correspondiente al resultado de la búsqueda
	 */
	Edge<K> findEdge(String startNode, String endNode);

	/**
	 * Búsqueda de adyacentes
	 * @param idVertex etiqueta asociada al nodo de búsqueda
	 * @return Lista de objetos tipo Edge con los resultados de la búsqueda
	 */
	ArrayList<Edge<K>> getNeighbors(String idVertex);
	
	/**
	 * Conjunto de arcos
	 * @return Lista de objetos tipo Edge con los arcos/conexiones de interés
	 */
	ArrayList<Edge<K>> showEdgesSet(); 

	/**
	 * Conjunto de nodos
	 * @return Lista con objetos tipo nodo (implementación del estudiante que contiene el método toString para las etiquetas) o Strings
	 */
	ArrayList<K> showNodeSet();

	/**
	 * Tipo de representación para la visualización de la estructura
	 * @return true para listas encadenadas y árboles
	 * @return false para grafos
	 */
	Boolean isLineal();

	/**
	 * Adición de un nuevo nodo
	 * @param tag etiqueta asociada al nodo que se va a agregar
	 */
	void addNode(String tag);

	/**
	 * Eliminación de un nodo 
	 * @param tag etiqueta asociada al nodo a eliminar
	 */
	void deleteNode(String tag);

	ArrayList<K> getPath();

	/**
	 * Tipo de estructura del estudiante
	 * @return 0 para árbol
	 * @return 1 para lista encadenada 
	 * @return 2 para grafo dirigido
	 * @return 3 para grafo no dirigido
	 */
	int structureType();

	/**
	 * Adición de un arco a la estructura
	 * No implementar si es un árbol o lista encadenada
	 * @param startNode etiqueta asociada al nodo inicial
	 * @param endNode etiqueta asociada al nodo final
	 */
	void addEdge(String startNode, String endNode);
}

