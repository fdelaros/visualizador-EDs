package modelo.estructuras;

public interface IGraph<K,V>extends Iterable<K>{

	int V();

	int E();

	Boolean addVertex( K idVertex, V infoVertex);

	Boolean addEdge(K idVertexIni, K idVertexFin, Double infoArc );

	V getInfoVertex(K idVertex);

	void setInfoVertex(K idVertex, V infoVertex);

	Double getInfoArc(K idVertexIni, K idVertexFin);

	void setInfoArc(K idVertexIni, K idVertexFin, Double infoArc);

	Iterable <K> adj (K idVertex);
}
