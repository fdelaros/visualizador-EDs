package modelo.estructuras;

import java.util.ArrayList;

import modelo.complementos.Edge;

// Java program to implement 
// a Singly Linked List 
public class LinkedList {

	Node head; // head of list

	// Linked list Node.
	// This inner class is made static
	// so that main() can access it
	public class Node {

		String data;
		Node next;

		// Constructor
		Node(String d) {
			data = d;
			next = null;
		}

		public String toString() {
			return data;
		}
	}

	// Method to insert a new node
	public LinkedList insert(LinkedList list, String data) {
		// Create a new node with given data
		Node new_node = new Node(data);
		new_node.next = null;

		// If the Linked List is empty,
		// then make the new node as head
		if (list.head == null) {
			list.head = new_node;
		} else {
			// Else traverse till the last node
			// and insert the new_node there
			Node last = list.head;
			while (last.next != null) {
				last = last.next;
			}

			// Insert the new_node at last node
			last.next = new_node;
		}

		// Return the list by head
		return list;
	}

	// Method to print the LinkedList.
	public static void printList(LinkedList list) {
		Node currNode = list.head;

		System.out.print("LinkedList: ");

		// Traverse through the LinkedList
		while (currNode != null) {
			// Print the data at current node
			System.out.print(currNode.data + " ");

			// Go to next node
			currNode = currNode.next;
		}
	}

	public LinkedList createList() {
		LinkedList list = new LinkedList();
		int listSize = 100;
		for (int i = 0; i < listSize; i++) {
			list = insert(list, "nodo" + i);
		}

		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getNodes(LinkedList list) {

		ArrayList nodeList = new ArrayList();

		Node currNode = list.head;
		if (currNode != null) // Correccion: nueva instruccion
		    nodeList.add(currNode);

		while (currNode != null) {
			currNode = currNode.next;
			if (currNode != null)
				nodeList.add(currNode);
		}

		return nodeList;
	}

	public Node getNode(LinkedList list, String findID) {
		Node currNode = list.head;

		while (currNode != null) {
			if(currNode.data.equals(findID))
				break;
			currNode = currNode.next;
		}
		return currNode;
	}

	public ArrayList<Edge<Object>> getNeighbors(LinkedList list, String findID){
		Node currNode = list.head;

		ArrayList<Edge<Object>> edgesList = new ArrayList<Edge<Object>>();

		while (currNode != null) {
			if(currNode.data.equals(findID)) {
				if(currNode.next != null) {
					Edge<Object> toNextNode = new Edge<Object>(currNode, currNode.next);
					edgesList.add(toNextNode);
				}
				break;
			}
			currNode = currNode.next;
		}

		return edgesList;
	}

	public Edge<Object> findEdge(LinkedList list, String start, String end){
		Edge<Object> edge = null;
		Node currNode = list.head;

		while (currNode != null) {
//			if(currNode.data.equals(start) && currNode.next.data.equals(end)) {
			// Correccion: Instruccion corregida
			if(currNode.data.equals(start) && currNode.next != null && currNode.next.data.equals(end)) {  
				edge = new Edge<Object>(currNode, currNode.next);
				break;
			}
			currNode = currNode.next;
		}
		return edge;
	}

	public ArrayList<Node> getStartingNodeSet(LinkedList list){
		ArrayList<Node> nodeSet = new ArrayList<Node>();
		Node currNode = list.head;

		while (currNode != null) {
			double addNode = Math.random();
			if(addNode >= 0.8) nodeSet.add(currNode);
			currNode = currNode.next;
		}

		return nodeSet;
	}

	public Node addNode(LinkedList list, String data) {
		// Create a new node with given data
		Node new_node = new Node(data);
		new_node.next = null;

		// If the Linked List is empty,
		// then make the new node as head
		if (list.head == null) {
			list.head = new_node;
		} else {
			// Else traverse till the last node
			// and insert the new_node there
			Node last = list.head;
			while (last.next != null) {
				last = last.next;
			}

			// Insert the new_node at last node
			last.next = new_node;
		}

		// Return the node
		return new_node;
	}
	// Warning: revisar que pasa si el dato No se encuentra. Posible NullPointerException
	public Node delete(LinkedList list, String data) {
		// Create a new node with given data
		Node delete = getNode(list, data);
		if(list.head.data.equals(delete.data)) {
			list.head = delete.next;
		}
		else {
			Node previous = null;
			Node current = list.head;
			while(current != delete) {
				previous = current;
				current = current.next;
			}
			previous.next = current.next;
		}
		return delete;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getPath(LinkedList list){
		Node currNode = list.head;

		ArrayList nodesList = new ArrayList();
		int count = 0;
		while (count < 4) {
			nodesList.add(currNode);
			currNode = currNode.next;
			count ++;
		}

		return nodesList;
	}

	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list = list.createList();
		list.getPath(list);

	}
}
