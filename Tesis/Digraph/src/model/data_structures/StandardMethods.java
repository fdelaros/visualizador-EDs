package model.data_structures;

import java.util.ArrayList;

public class StandardMethods implements IStandardMethods<Object>{

	@SuppressWarnings("rawtypes")
	private static Digraph graph = new Digraph();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createStructure() {
		int graphSize = 100;
		graph = new Digraph(graphSize);
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


	@Override
	public ArrayList<Edge<Object>> getPath(String startNode, String endNode) {
		// TODO Auto-generated method stub
		return null;
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

}
