package modelo.complementos;

import java.util.ArrayList;

import modelo.estructuras.LinkedList;

public class StandardMethods implements IStandardMethods<Object>{

	private static LinkedList list;

	public void createStructure() {
		list = new LinkedList();
		list = list.createList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList getNodeList() {
		return list.getNodes(list);
	}
	
	public Object findNode(String findTag) {
		return list.getNode(list, findTag);
	}

	public ArrayList<Edge<Object>> getEdgesList() {
		return null;
	}

	public ArrayList<Edge<Object>> getNeighbors(String idVertex) {
		return list.getNeighbors(list, idVertex);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList showNodeSet() {
		return list.getStartingNodeSet(list);
	}

	@Override
	public Boolean isLineal() {
		return true;
	}

	@Override
	public Edge<Object> findEdge(String startNode, String endNode) {
		return list.findEdge(list, startNode, endNode);
	}

	@Override
	public void addNode(String tag) {
		list.addNode(list, tag);
	}

	@Override
	public void deleteNode(String tag) {
		list.delete(list, tag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Object> getPath() {
		return list.getPath(list);
	}

	@Override
	public int structureType() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void addEdge(String startNode, String endNode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Edge<Object>> showEdgesSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
