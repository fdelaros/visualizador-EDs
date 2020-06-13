package modelo.complementos;

import java.util.ArrayList;

import modelo.estructuras.Digraph;

public class StandardMethods implements IStandardMethods<Object>{

	int size = 100;
//	int size = 0;
	@SuppressWarnings("rawtypes")
	private static Digraph graph;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createStructure() {
//		int graphSize = size;
		// Correccion: usar atributo size como tamaNo de la estructura
		// Si size es 0, el grafo debe iniciarse con una capacidad diferente de 0
		if ( size == 0)
			graph = new Digraph();
		else
			graph = new Digraph(size);
		for (int i = 0; i < size; i++) {
			graph.addVertex(i, "nodo numero " + i);
		}

		for(int i = 0; i < size; i++) {
			int numEdges = (int) Math.floor(Math.random() * 4);
			for (int j = 0; j < numEdges; j++) {
				int destination = (int) Math.floor(Math.random() * size);
				double weight = Math.floor(Math.random() * 20);
				if(graph.findEdge(i, destination) == null) {
					graph.addEdge(i, destination, weight);
				}
				// Correccion: agregar arco en direccion opuesta en caso de grafo No dirigido
				// Inicio Bloque 
				if(structureType() == 3)
				{
					if(graph.findEdge(destination, i) == null) {
						graph.addEdge(destination, i, weight);
					}					
				}
				// Fin Bloque 
			}
		}
	}


	@SuppressWarnings("unchecked")
	public Object findNode(String findTag) {
		return graph.getNode(Integer.parseInt(findTag));
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getNodeList() {
		return graph.getNodes();
	}


	// Correccion: Usar el tipo generico K
	@SuppressWarnings({ "unchecked"})
	public ArrayList<Edge<Object>> getEdgesList() {
		return graph.getEdges();
	}


	// Correccion: Usar el tipo generico K
	@SuppressWarnings("unchecked")
	public ArrayList<Edge<Object>> getNeighbors(String idVertex) {
		// Correccion instruccion
		/*
		if(structureType() == 3) return graph.neighborsForUndirected(Integer.parseInt(idVertex));
		else return graph.getNeighbors(Integer.parseInt(idVertex));
		*/
		// Correccion valor de retorno
		return graph.getNeighbors(Integer.parseInt(idVertex));
	}


	// Correccion tipo de nodo en la respuesta
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object> showNodeSet() {
		return graph.startingNodeSet();
	}


	@Override
	public Boolean isLineal() {
		return false;
	}

	
	@SuppressWarnings("unchecked")
	public Edge<Object> findEdge(String startNode, String endNode) {
		return graph.findEdge(Integer.parseInt(startNode), Integer.parseInt(endNode));
	}


	@SuppressWarnings("unchecked")
	@Override
	public void addNode(String tag) {
		graph.addVertex(Integer.parseInt(tag), "nodo número " + Integer.parseInt(tag));	
	}


	@SuppressWarnings("unchecked")
	public void deleteNode(String tag) {
		graph.deleteNode(Integer.parseInt(tag));
	}


	// Correccion tipo de nodo en la respuesta
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object> getPath() {
		return graph.getPath();
	}


	/*
	 * Tipo 2: Grafo Dirigido
	 * Tipo 3: Grafo No Dirigido
	 * @see modelo.complementos.IStandardMethods#structureType()
	 */
	public int structureType() {
		return 2;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void addEdge(String startNode, String endNode) {
		//Correccion: agregar arco en direccion startNode > endNode si No existe (condicional agregado)
		if(graph.findEdge(Integer.parseInt(startNode), Integer.parseInt(endNode)) == null) {
			graph.addEdge(Integer.parseInt(startNode), Integer.parseInt(endNode), 0.5);
		}
		
		//Correccion: agregar arco en direccion opuesta en caso de grafo No dirigido (si no existe)
		// Inicio Bloque
		if(structureType() == 3)
		{
			if(graph.findEdge(Integer.parseInt(endNode), Integer.parseInt(startNode)) == null) {
				graph.addEdge(Integer.parseInt(endNode), Integer.parseInt(startNode), 0.5);
			}					
		}
		// Fin Bloque
	}


	// Correccion tipo de nodo en la respuesta
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Edge<Object>> showEdgesSet() {
		return graph.showEdgesSet();
	}

}
