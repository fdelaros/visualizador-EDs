package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;

import student.tree.*;





/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs. Applet based on
 * JGraphAdapterDemo.
 *
 */
public class StructureAdapter extends JApplet
{
	private static final long serialVersionUID = 2202072534703043194L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1500, 900);
	private static JGraphXAdapter<String, DefaultEdge> jgxAdapter;
	//list with the nodes
	@SuppressWarnings("rawtypes")
	private static ArrayList<Nodo> nodos;
	//list with the edges
	@SuppressWarnings("rawtypes")
	private static ArrayList<Edge<Nodo>> edges; 
	//color to highlight nodes and edges
	private static final String HIGHLIGHT_COLOR = "#FFCC5C";

	// create a JGraphT graph
	private static ListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));
	@SuppressWarnings({ })
	private StandardMethods sm = new StandardMethods();
	private JFrame frame;

	/**
	 * An alternative starting point for this demo, to also allow running this applet as an
	 * application.
	 *
	 * @param args command line arguments
	 */

	//	@Override
	public void init()
	{
		StructureAdapter graph = new StructureAdapter();
		sm.createStructure();
		graph.createGraph();

		frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout());  // modificacion
		JScrollPane graphContainer = new JScrollPane(graph);
		frame.getContentPane().add(graphContainer, BorderLayout.CENTER);  // modificacion

		JPanel options = new JPanel();
		options.setPreferredSize(new Dimension(200, getHeight()));
		options.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));


		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));		
		p1.add(new JLabel("Buscar nodos"));
		JTextField tf1 = new JTextField(15);
		tf1.setPreferredSize(new Dimension(getWidth(),20));
		p1.add(tf1);
		JButton getNodeButton = new JButton("Buscar");
		getNodeButton.setAlignmentX(LEFT_ALIGNMENT);
		p1.add(getNodeButton);
		options.add(p1);

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));		
		p2.add(new JLabel("Buscar vecinos"));
		JTextField tf2 = new JTextField(15);
		tf2.setPreferredSize(new Dimension(getWidth(),20));
		p2.add(tf2);
		JButton getNeighbors = new JButton("Buscar");
		getNeighbors.setAlignmentX(LEFT_ALIGNMENT);
		p2.add(getNeighbors);
		options.add(p2);

		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.PAGE_AXIS));		
		p3.add(new JLabel("Agregar nodo"));
		JTextField tf3 = new JTextField(15);
		tf3.setPreferredSize(new Dimension(getWidth(),20));
		p3.add(tf3);
		JButton addNode = new JButton("Agregar");
		addNode.setAlignmentX(LEFT_ALIGNMENT);
		p3.add(addNode);
		options.add(p3);

		JPanel p5 = new JPanel();
		p5.setLayout(new BoxLayout(p5, BoxLayout.PAGE_AXIS));		
		p5.add(new JLabel("Eliminar nodo"));
		JTextField tf6 = new JTextField(15);
		tf6.setPreferredSize(new Dimension(getWidth(),20));
		p5.add(tf6);
		JButton delNode = new JButton("Eliminar");
		delNode.setAlignmentX(LEFT_ALIGNMENT);
		p5.add(delNode);
		options.add(p5);

		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.PAGE_AXIS));		
		p4.add(new JLabel("Buscar arcos"));
		JTextField tf5 = new JTextField(15);
		tf5.setPreferredSize(new Dimension(getWidth(),20));
		p4.add(tf5);
		JButton getEdges = new JButton("Buscar");
		getEdges.setAlignmentX(LEFT_ALIGNMENT);
		p4.add(getEdges);
		options.add(p4);

		JPanel p6 = new JPanel();
		p6.setLayout(new BoxLayout(p6, BoxLayout.PAGE_AXIS));		
		p6.add(new JLabel("Buscar camino"));
		JButton getPath = new JButton("Buscar");
		getEdges.setAlignmentX(LEFT_ALIGNMENT);
		p6.add(getPath);
		options.add(p6);

		frame.getContentPane().add(options, BorderLayout.EAST);  // modificacion

		ActionListener searchNode = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String node = tf1.getText();
				getNodeSet(node);
			}
		};
		getNodeButton.addActionListener(searchNode);

		ActionListener searchNeighbors = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String node = tf2.getText();
				getNeighbors(node);
			}
		};
		getNeighbors.addActionListener(searchNeighbors);

		ActionListener addNewNode = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nodeTag = tf3.getText();
				addNodeIn(nodeTag);
			}
		};
		addNode.addActionListener(addNewNode);

		ActionListener deleteNodeAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nodeTag = tf6.getText();
				deleteNode(nodeTag);
			}
		};
		delNode.addActionListener(deleteNodeAL);

		ActionListener searchEdges = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nodeRoute = tf5.getText();
				getEdgesSet(nodeRoute);
			}
		};
		getEdges.addActionListener(searchEdges);

		ActionListener drawPath = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findPath();
			}
		};
		getPath.addActionListener(drawPath);

		frame.setTitle("Test Estructuras de Datos");  // modificacion
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // modificacion
		frame.pack();  // modificacion
		frame.setVisible(true);	  // modificacion

	}

	@SuppressWarnings("rawtypes")
	public ArrayList<Nodo> createNodes() {
		ArrayList<Nodo> inputNodes = new ArrayList<Nodo>();

		ArrayList nodes = sm.getNodeList();
		for(Object current : nodes) {
			Nodo newNode = new Nodo(current.toString());
			inputNodes.add(newNode);
		}

		return inputNodes;
	}

	@SuppressWarnings("rawtypes")
	public Nodo findNode(String name) {
		Nodo nodo = null;
		for(Nodo current : nodos) {
			if(current.name.equals(name)) {
				nodo = current;
				break;
			}
		}
		return nodo;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Edge<Nodo>> createEdges(){
		ArrayList<Edge<Nodo>> inputEdges = new ArrayList<Edge<Nodo>>();
		if (sm.getEdgesList() == null) return null;
		for(Object current : sm.getEdgesList()) {
			Edge auxEdge = (Edge) current;
			Edge<Nodo> edge = new Edge(findNode(auxEdge.start.toString()),findNode(auxEdge.end.toString() ));
			inputEdges.add(edge);
		}
		return inputEdges;
	}


	@SuppressWarnings("rawtypes")
	public ArrayList<Edge<Nodo>> createEdgesForList(){
		ArrayList<Edge<Nodo>> inputEdges = new ArrayList<Edge<Nodo>>();
		System.out.println(nodos.size());
		for (int i = 0; i < nodos.size() - 1; i++) {
			Nodo current = nodos.get(i);
			Nodo next = nodos.get(i+1);
			Edge<Nodo> edge = new Edge<Nodo>(current, next);
			System.out.println(edge.start.name + " " + edge.end.name);
			inputEdges.add(edge);
		}
		return inputEdges;
	}

	@SuppressWarnings("rawtypes")
	public void createGraph() {

		nodos = createNodes();
		edges = createEdges() == null ? createEdgesForList() : createEdges();

		// create a visualization using JGraph, via an adapter
		jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(g);

		setPreferredSize(DEFAULT_SIZE);
		mxGraphComponent component = new mxGraphComponent(jgxAdapter);
		component.setConnectable(false);
		component.getGraph().setAllowDanglingEdges(false);
		getContentPane().add(component);
		resize(DEFAULT_SIZE);

		jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");

		//Add nodes to the graph
		for(Nodo actual : nodos) {
			g.addVertex(actual.name);
		}

		//Add edges
		for(Edge<Nodo> actual : edges) {
			g.addEdge(actual.start.name, actual.end.name);
		}
		if(sm.isLineal() == null || !sm.isLineal()) {
			mxFastOrganicLayout layout = new  mxFastOrganicLayout(jgxAdapter);
			layout.execute(jgxAdapter.getDefaultParent());
		}
		else {
			mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
			layout.execute(jgxAdapter.getDefaultParent());
		}

		if(sm.startingNodeSet() != null) {
			String nodeTags = "";
			ArrayList inputSet = sm.startingNodeSet();
			for (int i = 0; i < inputSet.size() ; i++) {
				nodeTags += inputSet.get(i).toString();
				if(i !=  inputSet.size() - 1) nodeTags += ",";
			}
			getNodeSet(nodeTags);
		}

	}

	public void defaultColors() {
		removeEdgesAndNodes();
		addEdgesAndNodes();
		if(sm.isLineal() == null || !sm.isLineal()) {
			mxFastOrganicLayout layout = new  mxFastOrganicLayout(jgxAdapter);
			layout.execute(jgxAdapter.getDefaultParent());
		}
		else {
			mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
			layout.execute(jgxAdapter.getDefaultParent());
		}

	}

	public void switchNodeColor(ArrayList<String> nodeList, String color) {
		HashMap<String,com.mxgraph.model.mxICell> vertexToCellMap = jgxAdapter.getVertexToCellMap();
		//Create array with the cells of the selected nodes
		Object[] vertexCellArray = new com.mxgraph.model.mxICell[nodeList.size()];
		for (int i = 0; i < nodeList.size(); i++) {
			vertexCellArray[i] = (Object) vertexToCellMap.get(nodeList.get(i));
		}

		jgxAdapter.setCellStyle("fillColor=" + color, vertexCellArray);

	}

	@SuppressWarnings("rawtypes")
	public void switchEdgeColor(ArrayList<Edge<Nodo>> edgesList, String color) {
		HashMap<DefaultEdge,com.mxgraph.model.mxICell> edgeToCellMap = jgxAdapter.getEdgeToCellMap();
		Object[] edgeCellArray = new Object[edgesList.size()];
		for(int i = 0; i < edgesList.size(); i++) 
			edgeCellArray[i] = (Object) edgeToCellMap.get(g.getEdge(edgesList.get(i).start.name, edgesList.get(i).end.name));

		jgxAdapter.setCellStyle("strokeColor=" + color, edgeCellArray);
	}

	public void getNodeSet(String nodes) {
		defaultColors();
		String notFound = "";
		String[] nodeSet = nodes.toString().split(",");
		ArrayList<String> nodeList = new ArrayList<String>();

		for(String a : nodeSet) {
			if(findNode(a) != null) {
				String result = sm.findNode(a).toString();
				nodeList.add(result);
			}
			else {
				notFound += a + ", " ;
			}
		}

		switchNodeColor(nodeList, HIGHLIGHT_COLOR);
		if(!notFound.isEmpty()) {
			String error = "No hubo resultados para nodo(s): " + notFound.substring(0, notFound.length() - 2);
			JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);   // modificacion
		}
	}


	@SuppressWarnings("rawtypes")
	public void getEdgesSet(String edges) {
		defaultColors();

		String notFound = "";
		String[] edgesSet = edges.toString().split(",");
		String nodeSet = "";

		ArrayList<Edge<Nodo>> edgesList = new ArrayList<Edge<Nodo>>();
		for (int i = 0; i < edgesSet.length; i++) {
			if(edgesSet[i].contains(">")) {
				String[] edgeContent = edgesSet[i].split(">");
				if(findEdge(edgeContent[0], edgeContent[1]) != null) {
					Edge searchResult = sm.findEdge(edgeContent[0], edgeContent[1]);
					Edge<Nodo> current = findEdge(searchResult.start.toString(), searchResult.end.toString());
					edgesList.add(current);
					nodeSet += edgeContent[0] + "," + edgeContent[1] + ",";
				}
				else notFound += edgeContent[0] + ">" + edgeContent[1] + ", ";
			}
			else notFound += edgesSet[i] + ", ";
		}
		if(!nodeSet.isEmpty()) getNodeSet(nodeSet.substring(0, nodeSet.length() - 1));
		if(!edgesList.isEmpty()) switchEdgeColor(edgesList, HIGHLIGHT_COLOR);
		if(!notFound.isEmpty()) {
			String error = "No hubo resultados para arco(s): " + notFound.substring(0, notFound.length() - 2);
			JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);  // modificacion
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getNeighbors(String node) {
		defaultColors();

		try {
			ArrayList<Edge<Nodo>> edgesList = new ArrayList<Edge<Nodo>>();
			ArrayList<String> nodeNeighbors = new ArrayList<String>();
			for(Object current : sm.getNeighbors(node)) {
				Edge auxEdge = (Edge) current;
				if(auxEdge.start != auxEdge.end) {
					Edge<Nodo> edge = new Edge(findNode(auxEdge.start.toString()),findNode(auxEdge.end.toString() ));
					edgesList.add(edge);
					nodeNeighbors.add(findNode(auxEdge.end.toString()).name);
					nodeNeighbors.add(findNode(auxEdge.start.toString()).name);
				}
			}
			switchEdgeColor(edgesList, HIGHLIGHT_COLOR);

			ArrayList<String> startingNodeList = new ArrayList<String>();
			startingNodeList.add(node);
			switchNodeColor(nodeNeighbors, HIGHLIGHT_COLOR);
			switchNodeColor(startingNodeList, "#F76262");
		}
		catch(Exception e) {
			String notFound = "No hubo resultados para el nodo: " + node;
			JOptionPane.showMessageDialog(frame, notFound, "Error", JOptionPane.ERROR_MESSAGE);  // modificacion
		}
	}


	@SuppressWarnings({ "rawtypes" })
	public Edge<Nodo> findEdge(String nodeStart, String nodeEnd) {
		Nodo start = findNode(nodeStart);
		Nodo end = findNode(nodeEnd);

		Edge<Nodo> edgeResult = null;
		for(Edge<Nodo> edge : edges)
			if(edge.start.equals(start) && edge.end.equals(end))
				edgeResult = edge;
		return edgeResult;
	}

	public void addNodeIn(String node) {
		defaultColors();
		try {
			sm.addNode(node);
			//Delete and refill current structure
			removeEdgesAndNodes();
			nodos = createNodes();
			edges = createEdges() == null ? createEdgesForList() : createEdges();
			addEdgesAndNodes();	
			if(sm.isLineal() == null || !sm.isLineal()) {
				mxFastOrganicLayout layout = new  mxFastOrganicLayout(jgxAdapter);
				layout.execute(jgxAdapter.getDefaultParent());
			}
			else {
				mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
				layout.execute(jgxAdapter.getDefaultParent());
			}


			//Color new node
			ArrayList<String> nodeList = new ArrayList<String>();
			nodeList.add(findNode(node).name);

			switchNodeColor(nodeList, HIGHLIGHT_COLOR);
		}
		catch(Exception e) {
			String notFound = "No se ha agregado el nodo: " + node;
			JOptionPane.showMessageDialog(frame, notFound, "Error", JOptionPane.ERROR_MESSAGE);  // modificacion
		}
	}

	@SuppressWarnings("rawtypes")
	public void removeEdgesAndNodes() {
		for(Edge<Nodo> edge: edges)
			g.removeEdge(edge.start.name, edge.end.name);
		for(Nodo nodo : nodos)
			g.removeVertex(nodo.name);
	}

	@SuppressWarnings("rawtypes")
	public void addEdgesAndNodes() {
		//Add nodes to the graph
		for(Nodo actual : nodos) 
			g.addVertex(actual.name);


		//Add edges
		for(Edge<Nodo> actual : edges) {
			g.addEdge(actual.start.name, actual.end.name);
		}
	}

	public void deleteNode(String node) {
		defaultColors();
		if(sm.findNode(node) != null) {
			//update node list
			sm.deleteNode(node);
			//Delete and refill current structure
			removeEdgesAndNodes();
			nodos = createNodes();
			edges = createEdges() == null ? createEdgesForList() : createEdges();
			addEdgesAndNodes();	
			if(sm.isLineal() == null || !sm.isLineal()) {
				mxFastOrganicLayout layout = new  mxFastOrganicLayout(jgxAdapter);
				layout.execute(jgxAdapter.getDefaultParent());
			}
			else {
				mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
				layout.execute(jgxAdapter.getDefaultParent());
			}

			if(findNode(node) == null) {
				String message = "El nodo " + node + " eliminado.";
				JOptionPane.showMessageDialog(frame, message, "Nodo eliminado", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				String message = "El nodo " + node + " no fue eliminado.";
				JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE); 
			}
		}
		else{
			String message = "El nodo " + node + " no está en la estructura.";
			JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}

	@SuppressWarnings("rawtypes")
	public void findPath() {
		defaultColors();

		String message = "";
		ArrayList<Edge<Nodo>> edgesInPath = new ArrayList<Edge<Nodo>>();
		ArrayList nodeList = sm.getPath();
		if(nodeList.size() > 1)
			for (int i = 0; i < nodeList.size()-1; i++) {
				if(findEdge(nodeList.get(i).toString(), nodeList.get(i+1).toString()) != null) {
					edgesInPath.add(findEdge(nodeList.get(i).toString(), nodeList.get(i+1).toString()));
				}
				else {
					message += nodeList.get(i).toString() + " => " + nodeList.get(i + 1).toString() + ", ";
				}
			}
		ArrayList<String> colorNodes = new ArrayList<String>();
		for(Object node : nodeList){
			colorNodes.add(node.toString());
		}

		switchNodeColor(colorNodes, HIGHLIGHT_COLOR);
		switchEdgeColor(edgesInPath, HIGHLIGHT_COLOR);

		if(!message.isEmpty()){
			String error = "Los arcos " + message + " no completan el camino.";
			JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}


	public static void main(String[] args) {
		StructureAdapter sa = new StructureAdapter();
		sa.init();
	}
}

