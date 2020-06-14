package modelo.complementos;

import java.util.ArrayList;

import modelo.estructuras.Digraph;

public class StandardMethods implements IStandardMethods<Object>{

	int size = 100;
	@SuppressWarnings("rawtypes")
	private static Digraph graph = new Digraph();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createStructure() {
		graph = new Digraph();
		int graphSize = size;
		graph = new Digraph(graphSize);
		for (int i = 0; i < graphSize; i++) {
			graph.addVertex(i, "nodo numero " + i);
		}

		for(int i = 0; i < graphSize; i++) {
			int numEdges = (int) Math.floor(Math.random() * 4);
			for (int j = 0; j < numEdges; j++) {
				int destination = (int) Math.floor(Math.random() * graphSize);
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


	@SuppressWarnings({ "unchecked"})
	public ArrayList<Edge<Object>> getEdgesList() {
		return graph.getEdges();
	}


	@SuppressWarnings("unchecked")
	public ArrayList<Edge<Object>> getNeighbors(String idVertex) {
		return graph.getNeighbors(Integer.parseInt(idVertex));
	}


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
		return graph.findEdge(startNode, endNode);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void addNode(String tag) {
		graph.auxAddVertex(Integer.parseInt(tag), "nodo número " + Integer.parseInt(tag));	
	}

	public void deleteNode(String tag) {

	}


	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object> getPath() {
		return graph.getPath();
	}


	@Override
	public int structureType() {
		return 3;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean addEdge(String startNode, String endNode) {
		return graph.addEdgeDigraph(startNode, endNode);
	}


	@Override
	public ArrayList<Edge<Object>> showEdgesSet() {
		return null;
	}

}
