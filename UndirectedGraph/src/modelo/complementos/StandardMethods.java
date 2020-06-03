package modelo.complementos;

import java.util.ArrayList;

import modelo.estructuras.UndirectedGraph;

public class StandardMethods implements IStandardMethods<Object>{

	int size = 10;
	@SuppressWarnings("rawtypes")
	private static UndirectedGraph graph = new UndirectedGraph();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createStructure() {
		int graphSize = size;
		graph = new UndirectedGraph(graphSize);
		for (int i = 0; i < graphSize; i++) {
			graph.addVertex(i, "nodo numero " + i);
		}

		for(int i = 0; i < graphSize; i++) {
			int numEdges = (int) Math.floor(Math.random() * 4);
			for (int j = 0; j < numEdges; j++) {
				int destination = (int) Math.floor(Math.random() * graphSize);
				graph.addEdge(i, destination, 0.5);
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
	public ArrayList<Object> startingNodeSet() {
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

}
