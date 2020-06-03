package modelo.complementos;

import java.util.ArrayList;

/**
 * Interfaz para la implementación de los métodos requeridos en el visualizador
 * @author diana
 *Los nodos de la estructura deben implementar un método toString() que retorne el valor que aparecerá en la etiqueta que representa dicho nodo.
 * @param <K>
 */
public interface IStandardMethods<K> {

	void createStructure();

	ArrayList<K> getNodeList();

	/**
	 * Arcos en la estructura
	 * @return null si es lista encadenada
	 */
	ArrayList<Edge<K>> getEdgesList();

	Object findNode(String findTag);

	Edge<K> findEdge(String startNode, String endNode);

	ArrayList<Edge<K>> getNeighbors(String idVertex);
	
	ArrayList<Edge<K>> showEdgesSet(); 

	ArrayList<K> showNodeSet();

	Boolean isLineal();

	void addNode(String tag);

	void deleteNode(String tag);

	ArrayList<K> getPath();

	/**
	 * Tipo de estructura del estudiante
	 * @return 0 para árbol
	 * @return 1 para lista encadenada 
	 * @return 2 para grafo
	 */
	int structureType();

	/**
	 * Agregar un arco a la estructura entre dos nodos
	 * No implementar si es un árbol o lista encadenada
	 * @param startNode
	 * @param endNode
	 */
	void addEdge(String startNode, String endNode);
}

