package model.data_structures;

import java.util.ArrayList;

public interface IStandardMethods<K> {
	
	void createStructure();
	
	ArrayList<K> getNodeList();
	
	ArrayList<Edge<K>> getEdgesList();
	
	Object findNode(String findTag);
	
	Edge<K> findEdge(String startNode, String endNode);
	
	ArrayList<Edge<K>> getNeighbors(String idVertex);
	
	ArrayList<Edge<K>> getPath(String startNode, String endNode);
	
	ArrayList<K> startingNodeSet();
	
	Boolean isLineal();
}
