package modelo.estructuras;

import java.util.ArrayList;

import modelo.complementos.Edge;

public class BinaryTree
{
	private ArrayList<Edge<Object>> arcos;
	private ArrayList<NodoArbol> vertices;
	private ArrayList<NodoArbol> camino = new ArrayList<NodoArbol>();
	int size;

	NodoArbol aux = null;

	class NodoArbol
	{
		//miembros de acceso
		NodoArbol nodoizquierdo;
		int datos;
		NodoArbol nododerecho;

		//iniciar dato y hacer de este nodo un nodo hoja
		public NodoArbol(int datosNodo)
		{
			datos = datosNodo;
			nodoizquierdo = nododerecho = null; //el nodo no tiene hijos
		}

		//buscar punto de insercion e inserter nodo nuevo
		public NodoArbol insertar(int valorInsertar){

			//insertar en subarbol izquierdo
			if(valorInsertar < datos) {
				//insertar en subarbol izquierdo
				if(nodoizquierdo == null) {
					nodoizquierdo = new NodoArbol(valorInsertar);
					aux = nodoizquierdo;
				}
				else    //continua recorriendo subarbol izquierdo
					nodoizquierdo.insertar(valorInsertar);
			}

			//insertar nodo derecho
			else if(valorInsertar > datos) {
				//insertar nuevo nodoArbol
				if(nododerecho == null) {
					nododerecho = new NodoArbol(valorInsertar);
					aux = nododerecho;
				}
				else {
					nododerecho.insertar(valorInsertar);
				}
			}

			return aux;
		} 

		public String toString() {
			return Integer.toString(datos);
		}

		public NodoArbol deleteNode() {
			if(this == raiz) {
				return null;
			}
			else if(this.nododerecho == null && this.nodoizquierdo == null) {
				NodoArbol parent = getParent(this);
				if(parent != null) {
					if(parent.nododerecho == this) parent.nododerecho = null;
					else if(parent.nodoizquierdo == this) parent.nodoizquierdo = null; 			

					vertices.remove(this);
				}
				return null;
			}
			else if(this.nodoizquierdo == null){
				NodoArbol parent = getParent(this);
				parent.nododerecho = this.nododerecho;
				return parent.nodoizquierdo;
			}
			else if(this.nododerecho == null) {
				NodoArbol parent = getParent(this);
				parent.nodoizquierdo = this.nodoizquierdo;
				return parent.nodoizquierdo;
			}
			else {
				NodoArbol replacement = this.nododerecho.nodoizquierdo;
				this.nododerecho = this.nododerecho.deleteNode();
				replacement.nodoizquierdo = this.nodoizquierdo;
				replacement.nododerecho = this.nododerecho;
				return replacement;
			}

		}

	}

	private NodoArbol raiz;

	//construir un arbol vacio
	public BinaryTree(int size)
	{
		raiz = null;
		arcos = new ArrayList<Edge<Object>>();
		vertices  = new ArrayList<NodoArbol>();
		this.size = size;
	}

	//insertar un nuevo ndo en el arbol de busqueda binaria
	public void insertarNodo(int valorInsertar)
	{
		if(raiz == null) {
			raiz = new NodoArbol(valorInsertar);
			vertices.add(raiz);
		}//crea nodo raiz
		else if(!vertices.contains(raiz.insertar(valorInsertar))){
			vertices.add(raiz.insertar(valorInsertar)); //llama al metodo insertar   
		}

	}

	// EMPIEZA EL RECORRIDO EN PREORDEN
	public synchronized void recorridoPreorden()
	{
		ayudantePreorden(raiz);
	}
	//meoto recursivo para recorrido en preorden

	private void ayudantePreorden(NodoArbol nodo)
	{
		if(nodo == null)
			return;
		camino.add(nodo);
		ayudantePreorden(nodo.nododerecho);   //recorre subarbol izquierdo

	}


	//EMPEZAR RECORRIDO INORDEN
	public synchronized void recorridoInorden()
	{
		ayudanteInorden(raiz);
	}

	//meoto recursivo para recorrido inorden
	private void ayudanteInorden( NodoArbol nodo)
	{
		if(nodo == null)
			return;

		ayudanteInorden(nodo.nodoizquierdo);
		//System.out.print(nodo.datos + " ");
		ayudanteInorden(nodo.nododerecho);
	}

	//EMPEZAR RECORRIDO PORORDEN
	public synchronized void recorridoPosorden()
	{
		ayudantePosorden(raiz);        
	}

	//meotod recursivo para recorrido posorden
	private void ayudantePosorden(NodoArbol nodo)
	{
		if( nodo == null )
			return;

		ayudantePosorden(nodo.nodoizquierdo);
		ayudantePosorden(nodo.nododerecho);
		System.out.print(nodo.datos + " ");
	}

	public void fillTree() {

		if(size > 0) {
			int rootNode = (int) Math.floor(Math.random()*size);
			insertarNodo(rootNode);
			for (int i = 0; i < size; i++) {
				int nodoAct = (int) Math.floor(Math.random()*size);
				if(nodoAct != rootNode) 
					insertarNodo(nodoAct);
			}
		}
	}

	public void parseEdges() {
		arcos = new ArrayList<Edge<Object>>();
		for (int i = 0; i < vertices.size(); i++) {
			NodoArbol izquierdo = vertices.get(i).nodoizquierdo ;
			NodoArbol derecho = vertices.get(i).nododerecho;
			if(izquierdo != null) {
				Edge<Object> nuevo = new Edge<Object>(vertices.get(i), izquierdo);
				//System.out.println(vertices.get(i).datos + " -> " +  izquierdo.datos);
				arcos.add(nuevo);
			}
			if(derecho != null) {
				Edge<Object> nuevo = new Edge<Object>(vertices.get(i), derecho);
				//System.out.println(vertices.get(i).datos + " -> " +  derecho.datos);
				arcos.add(nuevo);
			}
		}
	}

	public ArrayList<NodoArbol> showNodeSet(){
		ArrayList<NodoArbol> nodeSet = new ArrayList<BinaryTree.NodoArbol>();
		ArrayList<NodoArbol> nodesInStructure = getVertices();
		for (int i = 0; i < nodesInStructure.size(); i++) {
			if(i % 5 == 0) nodeSet.add(nodesInStructure.get(i));				
		}
		return nodeSet;
	}

	public NodoArbol getNode(String id) {
		NodoArbol ret = null;
		for(NodoArbol a : vertices) {
			if(Math.random() > 0.55) {
				ret = a;
				break;
			}
		}
		return ret;
	}

	public Edge<Object> getEdge(String start, String end){
		Edge<Object> ret = null;
		for(Edge<Object> edge : arcos) {
			if(edge.start.toString().equals(start) && edge.end.toString().equals(end)) {
				ret = edge;
				break;
			}
		}
		return ret;
	}

	public ArrayList<NodoArbol> getVertices(){
		return vertices;
	}

	public ArrayList<Edge<Object>> getEdges(){
		return arcos;
	}

	public void deleteNode(int deleteTag) {
		NodoArbol nodo = getNode(Integer.toString(deleteTag));
		nodo.deleteNode();
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getPath(){
		camino = new ArrayList<BinaryTree.NodoArbol>();
		recorridoPreorden();
		for (int i = 0; i < camino.size(); i++) {
			if(i % 4 == 1)
				camino.remove(i);
		}
		return camino;
	}

	public ArrayList<Edge<Object>> getNeighbors(int tag){
		ArrayList<Edge<Object>> neighbors = new ArrayList<Edge<Object>>();
		for(Edge<Object> edge : arcos) {
			if(edge.start.toString().equals(Integer.toString(tag)) || edge.end.toString().equals(Integer.toString(tag))) {
				neighbors.add(edge);
			}
		}
		return neighbors;
	}

	public NodoArbol getParent(NodoArbol son) {
		NodoArbol result = null;
		ArrayList<NodoArbol> verticesList = getVertices();
		for(NodoArbol current : verticesList) 
			if(current.nododerecho == son || current.nodoizquierdo == son) {
				result = current;
				break;
			}
		return result;
	}

	public void insertRoot(int num) {
		NodoArbol nuevo = new NodoArbol(num);
		if(raiz.nododerecho != null) nuevo.nododerecho = raiz.nododerecho;
		if(raiz.nodoizquierdo != null) nuevo.nodoizquierdo = raiz.nodoizquierdo;
		vertices.remove(raiz);
		vertices.add(nuevo);
		raiz = nuevo;
	}

	public static void main(String[] args) {
		BinaryTree bst = new BinaryTree(20);
		bst.fillTree();
	}


}

