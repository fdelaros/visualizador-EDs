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
			if(Math.random() > 0.55)
				break;
			currNode = currNode.next;
		}
		return currNode;
	}

	public ArrayList<Edge<Object>> getNeighbors(LinkedList list, String findID){
		Node currNode = list.head;
		Node previous = null;

		ArrayList<Edge<Object>> edgesList = new ArrayList<Edge<Object>>();
		boolean added = false;
		while (currNode != null) {
			if(currNode.data.equals(findID) && !added) {
				if(previous != null) {
					Edge<Object> toPreviousNode = new Edge<Object>(previous, currNode);
					edgesList.add(toPreviousNode);
					added = true;
				}
			}
			previous = currNode;
			currNode = currNode.next;
		}
		
		Node aux = list.head;
		while(aux != null) {
			if(aux.next != null && Math.random() > 0.85) {
				Edge<Object> toNextNode = new Edge<Object>(aux, aux.next);
				edgesList.add(toNextNode);
			}
			aux = aux.next;
		}

		return edgesList;
	}

	public Edge<Object> findEdge(LinkedList list, String start, String end){
		Edge<Object> edge = new Edge<Object>();
		Node currNode = list.head;

		while (currNode != null) {
			if(currNode.data.equals(start) && currNode.next.data.equals(end)) {
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

		int i = 0;
		while (currNode != null) {
			if(i % 8 == 0) nodeSet.add(currNode);
			currNode = currNode.next;
			i++;
		}
		return nodeSet;
	}

	public Boolean addNode(LinkedList list, String data) {
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
				if(Math.random()> 0.7) {
					new_node.next = last.next.next;
					last.next = new_node;
					break;
				}
			}
		}

		return true;
	}
	
	public Boolean delete(LinkedList list, String data) {
		// Create a new node with given data
		Node delete = getNode(list, data);

		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getPath(LinkedList list){
		Node currNode = list.head;

		ArrayList nodesList = new ArrayList();
		while (currNode != null && currNode.next != null) {
			if(Math.random() > 0.85) {
				nodesList.add(currNode);
				nodesList.add(currNode.next);
			}
			currNode = currNode.next;
		}

		return nodesList;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list = list.createList();
		list.addNode(list, "jjj");
		list.printList(list);

	}
}
