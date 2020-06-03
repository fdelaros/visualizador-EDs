package modelo.complementos;

import java.util.ArrayList;

import modelo.estructuras.Digraph;

public class StandardMethods implements IStandardMethods<Object>{

	int size = 100;
	@SuppressWarnings("rawtypes")
	private static Digraph graph;

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
				if(graph.findEdge(Integer.toString(i), Integer.toString(destination)) == null) {
					graph.addEdge(i, destination, weight);
				}
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
		if(structureType() == 3) return graph.neighborsForUndirected(Integer.parseInt(idVertex));
		else return graph.getNeighbors(Integer.parseInt(idVertex));
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
		graph.addVertex(Integer.parseInt(tag), "nodo número " + Integer.parseInt(tag));	
	}


	@SuppressWarnings("unchecked")
	public void deleteNode(String tag) {
		graph.deleteNode(Integer.parseInt(tag));
	}


	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object> getPath() {
		return graph.getPath();
	}


	public int structureType() {
		return 3;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void addEdge(String startNode, String endNode) {
		graph.addEdge(Integer.parseInt(startNode), Integer.parseInt(endNode), 0.5);
	}


	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Edge<Object>> showEdgesSet() {
		return graph.showEdgesSet();
	}

}
