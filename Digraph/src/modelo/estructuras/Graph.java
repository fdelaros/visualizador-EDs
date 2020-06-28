package modelo.estructuras;

import java.util.Iterator;

public class Graph<K,V> implements IGraph<K, V>{
	// -----------------------------------------------------------------
	// Atributos.
	// -----------------------------------------------------------------

	private int nVertices;
	private int nArcos;
	private LinearProbingHash<K,Vertice> HT;
	private BFSPaths bfsPath;
	private DijkstraSP dijkstraPaths;

	// -----------------------------------------------------------------
	// Metodos.
	// -----------------------------------------------------------------

	public Graph() {
		this.nVertices = 0;
		this.nArcos = 0;
		HT = new LinearProbingHash<K,Vertice>();
	}

	public Graph(int V) {
		if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.nVertices = V;
		this.nArcos = 0;
		HT = new LinearProbingHash<K,Vertice>(V);
	}

	public int V() {
		return nVertices;
	}

	public int E() {
		return nArcos;
	}

	@Override
	public Boolean addVertex(K idVertex, V infoVertex) {
		Vertice nuevoNodo = new Vertice( infoVertex );
		HT.put(idVertex, nuevoNodo);
		nVertices++;
		
		return true;
	}

	@Override
	public Boolean addEdge(K idVertexIni, K idVertexFin, Double infoArc) {
		//Se obtienen los vertices de inicio y fin del arco y se crea el arco
		Vertice inicio = HT.get(idVertexIni);
		Vertice fin = HT.get(idVertexFin);
		Arco nuevo = new Arco(infoArc,idVertexIni,idVertexFin);

		// se agrega el arco al nodo de inicio.
		LinkedList<Arco> lista1 = inicio.adyacentes;
		lista1.add(nuevo);
		inicio.adyacentes = lista1;

		// se agrega el arco al otro nodo.
		LinkedList<Arco> lista2 = fin.adyacentes;
		lista2.add(nuevo);
		fin.adyacentes = lista2;

		nArcos++;
		return true;
	}

	@Override
	public V getInfoVertex(K idVertex) {
		return HT.get(idVertex) != null ? HT.get(idVertex).item : null;
	}

	@Override
	public void setInfoVertex(K idVertex, V infoVertex) {
		HT.get(idVertex).item = infoVertex;
	}

	@Override
	public Double getInfoArc(K idVertexIni, K idVertexFin) {
		LinkedList<Arco> lista = HT.get(idVertexIni).adyacentes;

		Double retorno = null;

		for(Arco a: lista) {
			if( a.darOtro( idVertexIni ).equals(idVertexFin) ) {
				retorno = a.info;
			}
		}
		return retorno;
	}

	@Override
	public void setInfoArc(K idVertexIni, K idVertexFin, Double infoArc) {

		LinkedList<Arco> lista =HT.get(idVertexIni).adyacentes;

		for(Arco a: lista) {
			if(idVertexFin==a.fin || idVertexFin==a.inicio) 
				a.info = infoArc;
		}
	}

	@Override
	public Iterable<K> adj(K idVertex) {
		Queue<K> respuesta = new Queue<K>();
		LinkedList<Arco> arrayArcos = HT.get(idVertex).adyacentes;
		for( Arco arco : arrayArcos ) {
			respuesta.enqueue( arco.darOtro( idVertex ) );
		}
		return respuesta;
	}

	@Override
	public Iterator<K> iterator() {
		return HT.keys().iterator();
	}

	// -----------------------------------------------------------------
	// Queries
	// -----------------------------------------------------------------

	public boolean vHasPathTo( K s, K v ) {
		if( bfsPath == null || !bfsPath.s.equals(s)) bfsPath = new BFSPaths( s );

		return bfsPath.hasPathTo(v);
	}

	public Iterable<K> pathFromTo( K s, K v ) {
		if( bfsPath == null || !bfsPath.s.equals(s)) bfsPath = new BFSPaths( s );

		return bfsPath.pathTo(v);
	}

	public double shortDistFromTo( K s, K v) {
		if( dijkstraPaths == null || !dijkstraPaths.s.equals(s)) dijkstraPaths = new DijkstraSP( s );

		return dijkstraPaths.distTo(v);
	}

	public boolean vHasSPTo( K s , K v ) {
		if( dijkstraPaths == null || !dijkstraPaths.s.equals(s)) dijkstraPaths = new DijkstraSP( s );

		return dijkstraPaths.hasPathTo(v);
	}

	public Iterable<Arco> shortestPathFromTo( K s, K v ){
		if( dijkstraPaths == null || !dijkstraPaths.s.equals(s)) dijkstraPaths = new DijkstraSP( s );

		return dijkstraPaths.pathTo(v);
	}

	// -----------------------------------------------------------------
	// Clases para Queries
	// -----------------------------------------------------------------

	class BFSPaths{
		LinearProbingHash<K, Boolean> marked;
		LinearProbingHash<K, K> edgeTo;
		final K s;

		public BFSPaths( K source ) {
			s = source;
			marked = new LinearProbingHash<K, Boolean>();
			edgeTo = new LinearProbingHash<K, K>();

			for( K k : HT.keys() ) 
				marked.put( k , false);

			bfs( s );
		}

		private void bfs( K s ) {
			Queue<K> queue = new Queue<K>();
			marked.put(s, true);
			queue.enqueue( s );
			while( !queue.isEmpty() ) {
				K v = queue.dequeue();
				for( K w : adj(v) ) {
					if( !marked.get(w)) {
						edgeTo.put(w, v);
						marked.put(w, true);
						queue.enqueue(w);
					}
				}
			}
		}

		public boolean hasPathTo( K v ) {
			return marked.get(v);
		}

		public Iterable<K> pathTo( K v ){
			if( !hasPathTo(v) ) return null;
			Stack<K> path = new Stack<K>();
			for( K x = v ; !x.equals(s) ; x = edgeTo.get(x) )
				path.push( x );
			path.push( s );
			return path;
		}
	}

	class DijkstraSP {
		LinearProbingHash<K, Arco> edgeTo;
		LinearProbingHash<K, Double> distTo;
		MinPriorityQueue<K, Double> pq;
		final K s; 

		public DijkstraSP( K source ) {
			s = source;
			edgeTo = new LinearProbingHash<K,Arco>();
			distTo = new LinearProbingHash<K,Double>();
			pq = new MinPriorityQueue<K,Double>();

			for( K k : HT.keys() ) {
				distTo.put( k , Double.POSITIVE_INFINITY);
			}			


			distTo.put( source , 0.0 );
			pq.agregar( source, 0.0 );

			while( !pq.esVacia() ) {
				relax( pq.delMin() );
			}
		}

		public double distTo( K v) {
			return distTo.get(v);
		}

		public boolean hasPathTo( K v ) {
			return dijkstraPaths.distTo.get(v) < Double.POSITIVE_INFINITY;
		}
		private void relax( K v ) {
			for( Arco a : HT.get( v ).adyacentes ) {
				K w = a.darOtro( v );
				if( distTo.get(w) > distTo.get(v) + a.info ) {
					distTo.put( w , distTo.get(v) + a.info);
					edgeTo.put( w , a);
					if( pq.contiene(w) ) 
						pq.cambiarPrioridad( w, distTo.get(w) );
					else 
						pq.agregar( w, distTo.get(w) );
				}
			}
		}

		public Iterable<Arco> pathTo( K to ){
			if( hasPathTo(to) ) {
				Stack<Arco> path = new Stack<Arco>();
				Arco a = edgeTo.get(to);
				while( a != null ) {
					K from = a.darOtro( to );

					path.push( a );

					to = from;
					a = edgeTo.get( from );
				}
				return path;
			} else {
				return null;
			}
		}

	}

	/*private LinearProbingHash<K, Boolean> marked;
	private int count;

	public void depthFirstSearch( K s ) {
		marked = new LinearProbingHash<K, Boolean>();
		dfs(s);
	}
	private void dfs(K v) {
		marked.put(v, true);
		count++;
		for( K w : adj(v) ) {
			if( !marked.get(w) ) {
				dfs(v);
			}
		}
	}*/

	// -----------------------------------------------------------------
	// Subclases
	// -----------------------------------------------------------------
	/**
	 * Clase privada que representa los vertices (nodos) del grafo.
	 */
	public class Vertice{
		private V item;
		private LinkedList<Arco> adyacentes;

		public Vertice( V pItem ) {
			item = pItem;
			adyacentes = new LinkedList<Arco>();
		}

	}

	/**
	 * Clase privada que representa los arcos del grafo.
	 */
	public class Arco implements Comparable<Arco>{
		private K inicio;
		private K fin;
		private Double info;

		public Arco( Double pInfo, K pInicio, K pFin ) {
			info = pInfo;
			inicio = pInicio;
			fin = pFin;
		}

		@Override
		public int compareTo(Arco o) {
			return info.compareTo( o.info );
		}

		public K darOtro(K act) {
			return inicio.equals(act) ? fin : inicio;
		}

		public double darPeso() {
			return info;
		}

		public K darInicio() {
			return inicio;
		}

		public K darFin() {
			return fin;
		}
	}



}
