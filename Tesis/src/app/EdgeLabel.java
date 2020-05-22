package app;

import org.jgrapht.graph.DefaultEdge;

public class EdgeLabel extends DefaultEdge{
	private static final long serialVersionUID = 1L;
	private String label;
	
	public EdgeLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
