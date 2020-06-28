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
		String datos;
		NodoArbol nododerecho;
		ArrayList<NodoArbol> subarbol;

		//iniciar dato y hacer de este nodo un nodo hoja
		public NodoArbol(String datosNodo)
		{
			datos = datosNodo;
			nodoizquierdo = nododerecho = null; //el nodo no tiene hijos
		}

		//buscar punto de insercion e inserter nodo nuevo
		public NodoArbol insertar(String valorInsertar){

			//insertar en subarbol izquierdo
			if(valorInsertar.compareTo(datos) < 0) {
				//insertar en subarbol izquierdo
				if(nodoizquierdo == null) {
					nodoizquierdo = new NodoArbol(valorInsertar);
					aux = nodoizquierdo;
				}
				else    //continua recorriendo subarbol izquierdo
					nodoizquierdo.insertar(valorInsertar);
			}

			//insertar nodo derecho
			else if(valorInsertar.compareTo(datos) > 0) {
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
			return datos;
		}

		public NodoArbol deleteNode(String tag) {
			if(datos.equals(tag)) {
				if(nodoizquierdo == null && nododerecho == null) {
					return null;
				}
				else if(nodoizquierdo == null) {
					return nododerecho;
				}
				else if(nododerecho == null) {
					return nodoizquierdo;
				}
				else {
					NodoArbol reemplazo = getMinor(nododerecho); 
					nododerecho = nododerecho.deleteNode(reemplazo.datos);
					reemplazo.nodoizquierdo = nodoizquierdo;
					reemplazo.nododerecho = nododerecho;
					if(this == raiz) {
						raiz = reemplazo;
					}
					return reemplazo;
				}
			}
			else if(datos.compareTo(tag) > 0) {
				nodoizquierdo = nodoizquierdo.deleteNode(tag);
				return this;
			}
			else {
				nododerecho = nododerecho.deleteNode(tag);
				return this;
			}

		}

		private NodoArbol getMinor(NodoArbol nodo) {
			subarbol = new ArrayList<BinaryTree.NodoArbol>();
			recorrerSubarbol(nodo);
			NodoArbol menor = nodo;
			for(NodoArbol current : subarbol) {
				// Instruccion corregida
				// if(current.datos.compareTo(menor.datos) < 1) menor = current;
				// Correccion: comparacion estricta entre Strings
				if(current.datos.compareTo(menor.datos) < 0) menor = current;
			}
			return menor;
		}

		private void recorrerSubarbol(NodoArbol nodo)
		{
			if( nodo == null )
				return;
			subarbol.add(nodo);
			recorrerSubarbol(nodo.nodoizquierdo);
			recorrerSubarbol(nodo.nododerecho);
			//System.out.print(nodo.datos + " ");
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
	public Boolean insertarNodo(String valorInsertar)
	{
		//si el nodo ya existe en el árbol no se agrega
		if(getNode(valorInsertar) != null) {
			return false;
		}
		if(raiz == null) {
			raiz = new NodoArbol(valorInsertar);
			vertices.add(raiz);
		}//crea nodo raiz
		else if(!inStructure(valorInsertar)){
			vertices.add(raiz.insertar(valorInsertar)); //llama al metodo insertar   
		}
		
		//verificar que el nodo se haya agregado
		if(getNode(valorInsertar) != null) return true;
		
		return false;

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
		ayudantePreorden(nodo.nodoizquierdo);   //recorre subarbol izquierdo

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
			String root = "nodo" + rootNode;
			insertarNodo(root);
			String add = "";
			for (int i = 0; i < size; i++) {
				int nodoAct = (int) Math.floor(Math.random()*size);
				add = "nodo" + nodoAct;
				if(!add.equals(root) && !inStructure(add)) 
					insertarNodo(add);
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

	public NodoArbol getNode(String id) {
		NodoArbol ret = null;
		for(NodoArbol a : vertices) {
			if(a.datos.equals(id)) {
				ret = a;
				break;
			}
		}
		return ret;
	}

	public boolean inStructure(String tag) {
		for (int i = 0; i < vertices.size(); i++) {
			if(vertices.get(i).datos.equals(tag))
				return true;
		}
		return false;
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
		parseEdges();
		return arcos;
	}

	public Boolean deleteNode(String tag) {
		// Correccion: instruccion corregida
		raiz = raiz.deleteNode(tag);   // actualiza la raiz si la raiz contiene el dato a eliminar
		vertices.remove(getNode(tag));
		parseEdges();
		
		//Verificar que se haya eliminado
		if(getNode(tag) == null)
			return true;
		
		return false;
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

	public ArrayList<Edge<Object>> getNeighbors(String tag){
		ArrayList<Edge<Object>> neighbors = new ArrayList<Edge<Object>>();
		for(Edge<Object> edge : arcos) {
			if(edge.start.toString().equals(tag)) {
				neighbors.add(edge);
			}
		}
		return neighbors;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getPath(){
		camino = new ArrayList<BinaryTree.NodoArbol>();
		recorridoPreorden();
		return camino;
	}

	public ArrayList<NodoArbol> showNodeSet(){
		ArrayList<NodoArbol> nodeSet = new ArrayList<BinaryTree.NodoArbol>();
		ArrayList<NodoArbol> nodesInStructure = getVertices();
		for (int i = 0; i < nodesInStructure.size(); i++) {
			if(i % 5 == 0) nodeSet.add(nodesInStructure.get(i));				
		}
		return nodeSet;
	}


	public static void main(String[] args) {
		BinaryTree bst = new BinaryTree(10);
		bst.fillTree();
		bst.parseEdges();
		System.out.println("\n");
		System.out.println("raiz=" + bst.raiz.datos);
		System.out.println("vertices:");
		ArrayList<NodoArbol> vert = bst.vertices;
		for(NodoArbol n : vert) System.out.println(n.datos);

		bst.deleteNode(bst.raiz.datos);
		System.out.println("raiz=" + bst.raiz.datos);
		vert = bst.vertices;
		for(NodoArbol n : vert) System.out.println(n.datos);
	}

}