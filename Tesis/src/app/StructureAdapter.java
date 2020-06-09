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
import javax.swing.JTextArea;
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

import modelo.complementos.*;


/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs. Applet based on
 * JGraphAdapterDemo.
 *
 */
public class StructureAdapter extends JApplet
{
	private static final long serialVersionUID = 2202072534703043194L;
	private static final Dimension DEFAULT_SIZE = new Dimension(1366, 768);

	//list with the nodes
	@SuppressWarnings("rawtypes")
	private static ArrayList<Nodo> nodos;
	//list with the edges
	@SuppressWarnings("rawtypes")
	private static ArrayList<Edge<Nodo>> edges; 
	//color to highlight nodes and edges
	private static final String HIGHLIGHT_COLOR = "#FFCC5C";
	private static final String EDGES_HIGHLIGHT = "#e5a200";

	// create a JGraphT graph
	private static ListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));
	// create a visualization using JGraph, via an adapter
	private static JGraphXAdapter<String, DefaultEdge> jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(g);
	private StandardMethods sm = new StandardMethods();
	private JFrame frame;
	private JTextArea infoPanelContent;
	private int structureType = sm.structureType();


	public void init()
	{
		StructureAdapter graph = new StructureAdapter();

		//Main frame
		frame = new JFrame();
		frame.getContentPane().setLayout(new BorderLayout());  // modificacion

		// Options panel
		JPanel options = new JPanel();
		options.setPreferredSize(new Dimension(220, getHeight()));
		options.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));

		//Information panel
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setPreferredSize(new Dimension(1366, 60));
		String tipo = "";
		if(structureType == 0) tipo = "árbol binario ordenado";
		else if(structureType == 1) tipo = "lista encadenada";
		else if(structureType == 2) tipo = "grafo dirigido";
		else tipo = "grafo no dirigido";

		infoPanelContent = new JTextArea();
		infoPanelContent.setEditable(false);
		infoPanelContent.setLineWrap(true);
		infoPanelContent.setWrapStyleWord(true);
		infoPanelContent.setOpaque(false);
		infoPanel.add(infoPanelContent, BorderLayout.CENTER);

		//Visualization panel
		graph.createGraph();
		initInfoPanel(tipo);
		JScrollPane graphContainer = new JScrollPane(graph);
		frame.getContentPane().add(graphContainer, BorderLayout.CENTER);  // modificacion

		// Search for nodes panel
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));		
		p1.add(new JLabel("Buscar nodos (1 o más)"));
		JTextField tf1 = new JTextField(5);
		tf1.setPreferredSize(new Dimension(getWidth(),20));
		p1.add(tf1);
		JButton getNodeButton = new JButton("Buscar");
		getNodeButton.setAlignmentX(LEFT_ALIGNMENT);
		p1.add(getNodeButton);
		options.add(p1);

		ActionListener searchNode = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String node = tf1.getText();
				node = node.replaceAll("\\s+","");
				if(node.isEmpty()) emptyInput("La operación búsqueda de nodo ");
				else
					getNodeSet(node);

				tf1.setText("");
			}
		};
		getNodeButton.addActionListener(searchNode);

		//Search for neighbors panel
		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.PAGE_AXIS));		
		p2.add(new JLabel("Buscar adyacentes"));
		JTextField tf2 = new JTextField(10);
		tf2.setPreferredSize(new Dimension(getWidth(),20));
		p2.add(tf2);
		JButton getNeighbors = new JButton("Buscar");
		getNeighbors.setAlignmentX(LEFT_ALIGNMENT);
		p2.add(getNeighbors);
		options.add(p2);

		ActionListener searchNeighbors = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String node = tf2.getText();
				node = node.replaceAll("\\s+","");
				if(node.isEmpty()) emptyInput("La operación búsqueda de adyacentes ");
				else
					getNeighbors(node);

				tf2.setText("");
			}
		};
		getNeighbors.addActionListener(searchNeighbors);

		//Add a node panel
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

		ActionListener addNewNode = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nodeTag = tf3.getText();
				nodeTag = nodeTag.replaceAll("\\s+","");
				if(nodeTag.isEmpty()) emptyInput("La operación agregar nodos ");
				else
					addNodeIn(nodeTag);

				tf3.setText("");
			}
		};
		addNode.addActionListener(addNewNode);

		//Delete node panel
		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.PAGE_AXIS));		
		p4.add(new JLabel("Eliminar nodo"));
		JTextField tf4 = new JTextField(15);
		tf4.setPreferredSize(new Dimension(getWidth(),20));
		p4.add(tf4);
		JButton delNode = new JButton("Eliminar");
		delNode.setAlignmentX(LEFT_ALIGNMENT);
		p4.add(delNode);
		options.add(p4);

		ActionListener deleteNodeAL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nodeTag = tf4.getText();
				nodeTag = nodeTag.replaceAll("\\s+","");
				if(nodeTag.isEmpty()) emptyInput("La operación eliminación de nodos ");
				else
					deleteNode(nodeTag);

				tf4.setText("");
			}
		};
		delNode.addActionListener(deleteNodeAL);

		//Search for edges panel
		JPanel p5 = new JPanel();
		p5.setLayout(new BoxLayout(p5, BoxLayout.PAGE_AXIS));	
		String p5Text = "";
		int tfSize = 10;
		if(structureType == 0 || structureType == 1) {
			p5Text = "<html>Buscar conexiones <br>(1 o más)</html>";
		}
		else {
			p5Text = "<html>Buscar arcos <br>(1 o más)</html>\"";
			tfSize = 15;
		}
		p5.add(new JLabel(p5Text));
		JTextField tf5 = new JTextField(tfSize);
		tf5.setPreferredSize(new Dimension(getWidth(),20));
		p5.add(tf5);
		JButton getEdges = new JButton("Buscar");
		getEdges.setAlignmentX(LEFT_ALIGNMENT);
		p5.add(getEdges);
		options.add(p5);

		ActionListener searchEdges = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nodeRoute = tf5.getText();
				nodeRoute = nodeRoute.replaceAll("\\s+","");
				if(nodeRoute.isEmpty()) {
					if(structureType == 0 || structureType == 1)
						emptyInput("La operación búsqueda de conexiones ");
					else
						emptyInput("La operación búsqueda de arcos ");
				}
				else
					getEdgesSet(nodeRoute);

				tf5.setText("");
			}
		};
		getEdges.addActionListener(searchEdges);

		//Add edges panel
		if(structureType == 2 || structureType == 3) {
			JPanel p8 = new JPanel();
			p8.setLayout(new BoxLayout(p8, BoxLayout.PAGE_AXIS));		
			p8.add(new JLabel("Agregar  arcos (1 o más)"));
			JTextField tf8 = new JTextField(5);
			tf8.setPreferredSize(new Dimension(getWidth(),20));
			p8.add(tf8);
			JButton addEdge = new JButton("Agregar");
			addEdge.setAlignmentX(LEFT_ALIGNMENT);
			p8.add(addEdge);
			options.add(p8);

			ActionListener addEdgeAction = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String edges = tf8.getText();
					edges = edges.replaceAll("\\s+","");
					if(edges.isEmpty()) emptyInput("La operación agregar arcos ");
					else
						addEdge(edges);

					tf8.setText("");
				}
			};
			addEdge.addActionListener(addEdgeAction);
		}

		//Show edge set panel
		if(structureType == 2 || structureType == 3) {
			JPanel p7 = new JPanel();
			p7.setLayout(new BoxLayout(p7, BoxLayout.PAGE_AXIS));
			p7.add(new JLabel ("Mostrar arcos                           "));
			JButton showEdges = new JButton("Mostrar");
			showEdges.setAlignmentX(LEFT_ALIGNMENT);
			p7.add(showEdges);
			options.add(p7);

			ActionListener showEdgesSet = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showEdges();
				}
			};
			showEdges.addActionListener(showEdgesSet);
		}

		//Show node set panel
		JPanel p9 = new JPanel();
		p9.setLayout(new BoxLayout(p9, BoxLayout.PAGE_AXIS));
		p9.add(new JLabel ("Mostrar nodos                           "));
		JButton showNodes = new JButton("Mostrar");
		showNodes.setAlignmentX(LEFT_ALIGNMENT);
		p9.add(showNodes);
		options.add(p9);

		ActionListener showNodesSet = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showNodes();
			}
		};
		showNodes.addActionListener(showNodesSet);

		//Search path panel
		JPanel p6 = new JPanel();
		p6.setLayout(new BoxLayout(p6, BoxLayout.PAGE_AXIS));		
		p6.add(new JLabel("Mostrar camino                        "));
		JButton getPath = new JButton("Mostrar");
		getPath.setAlignmentX(LEFT_ALIGNMENT);
		p6.add(getPath);
		options.add(p6);

		ActionListener drawPath = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findPath();
			}
		};
		getPath.addActionListener(drawPath);

		//Restart structure panel
		JPanel p7 = new JPanel();
		p7.setLayout(new BoxLayout(p7, BoxLayout.PAGE_AXIS));
		p7.add(new JLabel("Reiniciar estructura                  "));
		JButton restartStructure = new JButton("Reiniciar");
		restartStructure.setAlignmentX(LEFT_ALIGNMENT);
		p7.add(restartStructure);
		options.add(p7);

		ActionListener restart = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartStructure();
			}
		};
		restartStructure.addActionListener(restart);

		frame.getContentPane().add(infoPanel, BorderLayout.SOUTH);
		frame.getContentPane().add(options, BorderLayout.EAST);  // modificacion
		frame.setTitle("Visualizador de Estructuras de Datos");  // modificacion
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // modificacion
		frame.pack();  // modificacion
		frame.setVisible(true);	  // modificacion

	}

	/**
	 * Init information panel with the corresponding data
	 * @param tipo Type of the data structure
	 */
	public void initInfoPanel(String tipo) {
		String message = "Estructura de datos: " + tipo;
		message += "\nCantidad de nodos: " + nodos.size();
		if(structureType == 0 || structureType == 1) {
			message += "\nCantidad de conexiones: ";
		}
		else
			message += "\nCantidad de arcos: ";
		message = structureType == 3 ? message + edges.size()/2 : message + edges.size();
		infoPanelContent.setText(message);
	}

	/**
	 * Gets the nodes for creating the structure
	 * @return list with the nodes fetched from the StandardMethods class, and parsed to the Nodo class
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Nodo> createNodes() {
		ArrayList<Nodo> inputNodes = new ArrayList<Nodo>();

		ArrayList nodes = sm.getNodeList();
		if(nodes != null && !nodes.isEmpty())
			for(Object current : nodes) {
				Nodo newNode = new Nodo(current.toString());
				inputNodes.add(newNode);
			}

		return inputNodes;
	}

	/**
	 * Find a node in the nodes list
	 * @param name Tag associated to the node to be found
	 * @return node resulting from the search
	 */
	@SuppressWarnings("rawtypes")
	public Nodo findNode(String name) {
		Nodo nodo = null;
		for(Nodo current : nodos) {
			if(current.name.equals(name)) {
				nodo = current;
				return nodo;
			}
		}
		return null;
	}

	/**
	 * Gets the edges for creating the structure
	 * @return list with the edges/connections fetched from the StandardMethods class, and parsed to Edge<Nodo>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Edge<Nodo>> createEdges(){
		ArrayList<Edge<Nodo>> inputEdges = new ArrayList<Edge<Nodo>>();
		if (sm.getEdgesList() == null) return null;
		for(Object current : sm.getEdgesList()) {
			Edge auxEdge = (Edge) current;
			Edge<Nodo> edge = new Edge(findNode(auxEdge.start.toString()),findNode(auxEdge.end.toString()));
			inputEdges.add(edge);
			if(structureType == 3) {
				edge = new Edge(findNode(auxEdge.end.toString()), findNode(auxEdge.start.toString()));
				inputEdges.add(edge);
			}
		}
		return inputEdges;
	}

	/**
	 * create the edges for a list, they are created in order according to the node list
	 * @return list with the edges/connections fetched from the StandardMethods class, and parsed to Edge<Nodo>
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Edge<Nodo>> createEdgesForList(){
		ArrayList<Edge<Nodo>> inputEdges = new ArrayList<Edge<Nodo>>();
		for (int i = 0; i < nodos.size() - 1; i++) {
			Nodo current = nodos.get(i);
			Nodo next = nodos.get(i+1);
			Edge<Nodo> edge = new Edge<Nodo>(current, next);
			inputEdges.add(edge);
		}
		return inputEdges;
	}

	/**
	 * Creates the visualization of the structure
	 */
	@SuppressWarnings("rawtypes")
	public void createGraph() {
		try {
			sm.createStructure();
			nodos = createNodes();
			edges = structureType == 1 ? createEdgesForList() : createEdges();

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
			//If it's a graph, get disconnected nodes and connect them 
			if(structureType == 2 || structureType == 3) {
				ArrayList<Edge<Nodo>> disconnected = disconnectedNodes();
				switchEdgeColor(disconnected, "#EEEEEE");
			}
			if(sm.isLineal() == null || !sm.isLineal()) {
				mxFastOrganicLayout layout = new  mxFastOrganicLayout(jgxAdapter);
				layout.execute(jgxAdapter.getDefaultParent());
			}
			else {
				mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
				layout.execute(jgxAdapter.getDefaultParent());
			}
		}
		catch(Exception e) {
			String error = "Se presentó un error al crear la estructura.";
			JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE); 
			frame.dispose();
		}
	}

	/**
	 * Resets the colors of the structure to the default ones for cleaning the visual result of previous operations
	 */
	@SuppressWarnings("rawtypes")
	public void defaultColors() {
		removeEdgesAndNodes();
		addEdgesAndNodes();
		//If it's a graph, get the disconnected nodes and connect them with and invisible edge
		if(structureType == 2 || structureType == 3) {
			ArrayList<Edge<Nodo>> disconnected = disconnectedNodes();
			switchEdgeColor(disconnected, "#EEEEEE");
		}
		if(sm.isLineal() == null || !sm.isLineal()) {
			mxFastOrganicLayout layout = new  mxFastOrganicLayout(jgxAdapter);
			layout.execute(jgxAdapter.getDefaultParent());
		}
		else {
			mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
			layout.execute(jgxAdapter.getDefaultParent());
		}

	}

	/**
	 * Gets the disconnected nodes in the graph so they can be linked with an invisible edge, and avoiding that they appear stacked on the left corner
	 * @return list with the edges that connect the nodes that don't have any edges from the structure provided by the student
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<Edge<Nodo>> disconnectedNodes() {
		ArrayList<Nodo> nodesWithEdges = new ArrayList<Nodo>();
		ArrayList<Nodo> nodesWithoutEdges = new ArrayList<Nodo>();
		for(Edge<Nodo> current : edges) {
			if(!nodesWithEdges.contains(current.start)) nodesWithEdges.add(current.start);
			if(!nodesWithEdges.contains(current.end)) nodesWithEdges.add(current.end);
		}
		for(Nodo node : nodos) {
			if(!nodesWithEdges.contains(node)) nodesWithoutEdges.add(node);
		}

		ArrayList<Edge<Nodo>> disconnectedEdges = new ArrayList<Edge<Nodo>>();
		Edge<Nodo> newEdge = null;
		if(nodesWithoutEdges.size() > 1)
			for (int i = 0; i < nodesWithoutEdges.size() - 1; i++) {
				newEdge = new Edge<Nodo>(nodesWithoutEdges.get(i), nodesWithoutEdges.get(i + 1));
				disconnectedEdges.add(newEdge);
				g.addEdge(newEdge.start.name, newEdge.end.name);
			}

		return disconnectedEdges;
	}

	/**
	 * Changes the color of the specifies nodes
	 * @param nodeList List with the nodes to be changed
	 * @param color Color of the nodes 
	 */
	public void switchNodeColor(ArrayList<String> nodeList, String color) {
		HashMap<String,com.mxgraph.model.mxICell> vertexToCellMap = jgxAdapter.getVertexToCellMap();
		//Create array with the cells of the selected nodes
		Object[] vertexCellArray = new com.mxgraph.model.mxICell[nodeList.size()];
		for (int i = 0; i < nodeList.size(); i++) {
			vertexCellArray[i] = (Object) vertexToCellMap.get(nodeList.get(i));
		}

		jgxAdapter.setCellStyle("fillColor=" + color, vertexCellArray);

	}

	/**
	 * Changes the color of the specifies edges
	 * @param edgesList List with the edges to be changed
	 * @param color Color of the edges
	 */
	@SuppressWarnings("rawtypes")
	public void switchEdgeColor(ArrayList<Edge<Nodo>> edgesList, String color) {
		HashMap<DefaultEdge,com.mxgraph.model.mxICell> edgeToCellMap = jgxAdapter.getEdgeToCellMap();
		Object[] edgeCellArray = new Object[edgesList.size()];
		for(int i = 0; i < edgesList.size(); i++) 
			edgeCellArray[i] = (Object) edgeToCellMap.get(g.getEdge(edgesList.get(i).start.name, edgesList.get(i).end.name));

		jgxAdapter.setCellStyle("strokeColor=" + color, edgeCellArray);
	}

	/**
	 * Highlights the nodes specified as input 
	 * @param nodes Node(s) to be found and fetched from the StandardMethods class
	 */
	public void getNodeSet(String nodes) {
		defaultColors();
		String notFound = "";

		String search = "Búsqueda de nodo";
		search = (nodes.split(",").length > 1) ? search + "s " + nodes +  " retornó " : search + " " + nodes + " retornó ";
		String[] nodeSet = nodes.toString().split(",");
		ArrayList<String> nodeList = new ArrayList<String>();

		String responseFromStructure = "";
		try {
			for(String a : nodeSet) {
				if(findNode(a) != null) {
					String result = sm.findNode(a).toString();
					responseFromStructure += result + ", ";
					nodeList.add(result);
				}
				else {
					notFound += a + ", " ;
				}
			}

			if(!responseFromStructure.isEmpty()) {
				responseFromStructure = responseFromStructure.substring(0, responseFromStructure.length() - 2);
				search += ":" + responseFromStructure;
			}
			else search += "vacío";
			infoPanelContent.setText(search);

			switchNodeColor(nodeList, HIGHLIGHT_COLOR);
			if(!notFound.isEmpty()) {
				String error = "No hubo resultados para nodo(s): " + notFound.substring(0, notFound.length() - 2);
				JOptionPane.showMessageDialog(frame, error, "Advertencia", JOptionPane.INFORMATION_MESSAGE);   // modificacion
			}
		}
		catch(Exception e) {
			String operation = "La búsqueda de nodos " + nodes + " retornó error";
			infoPanelContent.setText(operation);
			String error = "La búsqueda de los nodos " + nodes + " generó un error.";
			JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}

	/**
	 * Highlights the edges specified as input
	 * @param edges Edge(s) to be found and fetched from the StandardMethods class
	 */
	@SuppressWarnings("rawtypes")
	public void getEdgesSet(String edges) {
		defaultColors();

		String notFound = "";
		String[] edgesSet = edges.toString().split(",");
		String nodeSet = "";
		String operation = (structureType == 0 || structureType == 1) ? "La búsqueda de conexiones " : "La búsqueda de arcos ";
		operation += edges + " retornó ";
		String result = "";

		ArrayList<Edge<Nodo>> edgesList = new ArrayList<Edge<Nodo>>();
		try {
			Edge searchResult = null;
			Edge<Nodo> current = null;
			for (int i = 0; i < edgesSet.length; i++) {
				if(edgesSet[i].contains(">")) {
					String[] edgeContent = edgesSet[i].split(">");
					if(findEdge(edgeContent[0], edgeContent[1]) != null) {
						if(structureType == 3) {
							if(sm.findEdge(edgeContent[0], edgeContent[1]) != null)
								searchResult = sm.findEdge(edgeContent[0], edgeContent[1]);
							else if(sm.findEdge(edgeContent[1], edgeContent[0]) != null)
								searchResult = sm.findEdge(edgeContent[1], edgeContent[0]);

							edgesList.add(findEdge(searchResult.end.toString(), searchResult.start.toString()));
						}
						else searchResult = sm.findEdge(edgeContent[0], edgeContent[1]);
						current = findEdge(searchResult.start.toString(), searchResult.end.toString());
						edgesList.add(current);
						result += current.start.name + ">" + current.end.name + ", ";
						nodeSet += edgeContent[0] + "," + edgeContent[1] + ",";
					}
					else notFound += edgeContent[0] + ">" + edgeContent[1] + ", ";
				}
				else notFound += edgesSet[i] + ", ";
			}

			if(!nodeSet.isEmpty()) getNodeSet(nodeSet.substring(0, nodeSet.length() - 1));
			if(!edgesList.isEmpty()) switchEdgeColor(edgesList, EDGES_HIGHLIGHT);
			if(!result.isEmpty()) {
				result = result.substring(0, result.length() - 2);
				operation += ": " + result;
			}
			else operation += "vacío";
			infoPanelContent.setText(operation);
			if(!notFound.isEmpty()) {
				String error = "No hubo resultados para ";
				error += (structureType == 0 || structureType == 1) ? "las conexiones: " : "los arcos: ";
				error += notFound.substring(0, notFound.length() - 2);
				JOptionPane.showMessageDialog(frame, error, "Advertencia", JOptionPane.INFORMATION_MESSAGE);  // modificacion
			}
		}
		catch(Exception e){
			operation = (structureType == 0 || structureType == 1) ? "La búsqueda de las conexiones " : "La búsqueda de los arcos ";
			operation += edges + " retornó error";
			infoPanelContent.setText(operation);
			String error = (structureType == 0 || structureType == 1) ? "La búsqueda de las conexiones " : "La búsqueda de los arcos ";
			error += edges + " generó un error.";
			JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}


	/**
	 * Highlights the main node and the neighbors
	 * @param node Tag that corresponds to the node which neighbors are to be found and fetched from the StandardMethods class
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getNeighbors(String node) {
		defaultColors();
		String operation = "Adyacentes del nodo " + node + " retornó: ";
		try {
			if(findNode(node) != null){
				ArrayList<Edge<Nodo>> edgesList = new ArrayList<Edge<Nodo>>();
				ArrayList<String> nodeNeighbors = new ArrayList<String>();
				ArrayList<Edge<Object>> neighborsFromStudent = sm.getNeighbors(node);

				for(Object current : neighborsFromStudent) {
					Edge auxEdge = (Edge) current;
					if(findNode(auxEdge.start.toString()) != null && findNode(auxEdge.end.toString()) != null) {
						Edge<Nodo> edge = new Edge(findNode(auxEdge.start.toString()),findNode(auxEdge.end.toString() ));
						edgesList.add(edge);
						if(structureType == 1)
							nodeNeighbors.add(findNode(auxEdge.start.toString()).name);
						nodeNeighbors.add(findNode(auxEdge.end.toString()).name);
						if(structureType == 3) {
							edge = new Edge(findNode(auxEdge.end.toString()), findNode(auxEdge.start.toString()));
							edgesList.add(edge);
						}
					}
				}

				if(!nodeNeighbors.isEmpty()) {
					if(structureType != 1) {
						String results = "";
						for(String nodeNeighbor : nodeNeighbors) 
							if(!results.contains(nodeNeighbor))
								results += nodeNeighbor + ", ";

						results = results.substring(0, results.length() - 2);
						operation += results;
					}
					else {
						for(String nodeNeighbor : nodeNeighbors) 
							if(!operation.contains(nodeNeighbor))
								operation += nodeNeighbor + ", ";

						operation = operation.substring(0, operation.length() - 2);
					}
				}
				else operation = "Adyacentes del nodo " + node + " retornó vacío";
				infoPanelContent.setText(operation);

				switchEdgeColor(edgesList, EDGES_HIGHLIGHT);

				ArrayList<String> startingNodeList = new ArrayList<String>();
				startingNodeList.add(node);
				switchNodeColor(nodeNeighbors, HIGHLIGHT_COLOR);
				switchNodeColor(startingNodeList, "#F76262");
			}
			else {
				operation = "Adyacentes del nodo " + node + " no se completó";
				infoPanelContent.setText(operation);
				String notFound = "El nodo " + node + " no está en la estructura";
				JOptionPane.showMessageDialog(frame, notFound, "Advertencia", JOptionPane.INFORMATION_MESSAGE);  // modificacion
			}
		}
		catch(Exception e) {
			operation = "Adyacentes del nodo " + node + " retornó error";
			infoPanelContent.setText(operation);
			String notFound = "La búsqueda de adyacentes para el nodo " + node + " generó un error.";
			JOptionPane.showMessageDialog(frame, notFound, "Error", JOptionPane.ERROR_MESSAGE); 
		}

	}

	
	/**
	 * find an edge in the edges list
	 * @param nodeStart Tag associated to the starting node in the edge
	 * @param nodeEnd Tag associated to the ending node in the edge
	 * @return edge resulting from the search in the StandardMethods class
	 */
	@SuppressWarnings({ "rawtypes" })
	public Edge<Nodo> findEdge(String nodeStart, String nodeEnd) {
		Nodo start = findNode(nodeStart);
		Nodo end = findNode(nodeEnd);

		Edge<Nodo> edgeResult = null;
		for(Edge<Nodo> edge : edges)
			if(edge.start.equals(start) && edge.end.equals(end)) {
				edgeResult = edge;
				return edgeResult;
			}
		return null;
	}

	
	/**
	 * Add a node to the structure
	 * @param node Tag associated to the node that will be created through the StandardMethods class
	 */
	@SuppressWarnings("rawtypes")
	public void addNodeIn(String node) {
		defaultColors();
		String operation = "Agregar nodo " + node;
		if(findNode(node) != null) {
			operation = " El nodo " + node + " ya está en la estructura";
			infoPanelContent.setText(operation);
			String found = "El nodo " + node + " ya existe en la estructura";
			JOptionPane.showMessageDialog(frame, found, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			try {
				sm.addNode(node);
				//Delete and refill current structure
				removeEdgesAndNodes();
				nodos = createNodes();
				edges = createEdges() == null ? createEdgesForList() : createEdges();
				addEdgesAndNodes();	
				//If it's a graph, get disconnected nodes and connect them 
				if(structureType == 2 || structureType == 3) {
					ArrayList<Edge<Nodo>> disconnected = disconnectedNodes();
					switchEdgeColor(disconnected, "#EEEEEE");
				}
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
				operation += " completado";
				operation += "\nCantidad de nodos: " + nodos.size();
				if(structureType == 0 || structureType == 1) {
					operation += "\nCantidad de conexiones: ";
				}
				else
					operation += "\nCantidad de arcos: ";
				operation = structureType == 3 ? operation + edges.size()/2 : operation + edges.size();
				infoPanelContent.setText(operation);
			}
		catch(Exception e) {
			operation += " no se completó";
			infoPanelContent.setText(operation);
			String notFound = "No se ha agregado el nodo: " + node;
			JOptionPane.showMessageDialog(frame, notFound, "Advertencia", JOptionPane.INFORMATION_MESSAGE);  // modificacion
		}
	}

	
	/**
	 * Clears the visualization by removing all the edges and nodes associated
	 */
	@SuppressWarnings("rawtypes")
	public void removeEdgesAndNodes() {
		if(!edges.isEmpty())
			for(Edge<Nodo> edge: edges)
				g.removeEdge(edge.start.name, edge.end.name);
		if(!nodos.isEmpty())
			for(Nodo nodo : nodos)
				g.removeVertex(nodo.name);
	}

	
	/**
	 * Adds the nodes and edges in the lists to the structure
	 */
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

	
	/**
	 * Delete the node specified
	 * @param node String associated to the node that's going to be deleted from the structure through the StandardMethods class
	 */
	@SuppressWarnings("rawtypes")
	public void deleteNode(String node) {
		defaultColors();
		String operation = "Eliminar nodo " + node;
		if(findNode(node) == null) {
			operation += " no se completó";
			infoPanelContent.setText(operation);
			String message = "El nodo " + node + " no está en la estructura.";
			JOptionPane.showMessageDialog(frame, message, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
		}
		else
			try{
				//update node list
				sm.deleteNode(node);
				//Delete and refill current structure
				removeEdgesAndNodes();
				nodos = createNodes();
				edges = createEdges() == null ? createEdgesForList() : createEdges();
				addEdgesAndNodes();	
				//If it's a graph, get disconnected nodes and connect them 
				if(structureType == 2 || structureType == 3) {
					ArrayList<Edge<Nodo>> disconnected = disconnectedNodes();
					switchEdgeColor(disconnected, "#EEEEEE");
				}
				if(sm.isLineal() == null || !sm.isLineal()) {
					mxFastOrganicLayout layout = new  mxFastOrganicLayout(jgxAdapter);
					layout.execute(jgxAdapter.getDefaultParent());
				}
				else {
					mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
					layout.execute(jgxAdapter.getDefaultParent());
				}

				if(findNode(node) == null) {
					operation += " completado";
					operation += "\nCantidad de nodos: " + nodos.size();
					if(structureType == 0 || structureType == 1) {
						operation += "\nCantidad de conexiones: ";
					}
					else
						operation += "\nCantidad de arcos: ";
					operation = structureType == 3 ? operation + edges.size()/2 : operation + edges.size();
					infoPanelContent.setText(operation);
					String message = "El nodo " + node + " fue eliminado.";
					JOptionPane.showMessageDialog(frame, message, "Advertencia", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					operation += " no se completó";
					operation += "\nCantidad de nodos: " + nodos.size();
					if(structureType == 0 || structureType == 1) {
						operation += "\nCantidad de conexiones: ";
					}
					else
						operation += "\nCantidad de arcos: ";
					operation = structureType == 3 ? operation + edges.size()/2 : operation + edges.size();
					infoPanelContent.setText(operation);
					String message = "El nodo " + node + " no fue eliminado.";
					JOptionPane.showMessageDialog(frame, message, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
				}
			}
		catch(Exception e){
			operation += " no se completó";
			operation += "\nCantidad de nodos: " + nodos.size();
			operation += "\nCantidad de arcos: ";
			operation = structureType == 3 ? operation + edges.size()/2 : operation + edges.size();
			infoPanelContent.setText(operation);
			String message = "El nodo " + node + " no se pudo eliminar.";
			JOptionPane.showMessageDialog(frame, message, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
		}
	}

	/**
	 * Draw the path fetched from the StandardMethods class
	 */
	@SuppressWarnings("rawtypes")
	public void findPath() {
		//Reset the colors in the visualization
		defaultColors();

		String message = "";
		String path = "";
		ArrayList<Edge<Nodo>> edgesInPath = new ArrayList<Edge<Nodo>>();
		try {
			ArrayList nodeList = sm.getPath();
			if(nodeList != null && nodeList.size() > 1) {
				ArrayList<String> breakingNodes = new ArrayList<String>();
				//Verify that the edges make up a sequence
				for (int i = 0; i < nodeList.size()-1; i++) {
					if(findEdge(nodeList.get(i).toString(), nodeList.get(i+1).toString()) != null) {
						edgesInPath.add(findEdge(nodeList.get(i).toString(), nodeList.get(i+1).toString()));
						if(structureType == 3) {
							edgesInPath.add(findEdge(nodeList.get(i + 1).toString(), nodeList.get(i).toString()));
						}
					}
					else {
						message += nodeList.get(i).toString() + " => " + nodeList.get(i + 1).toString() + ", ";
						breakingNodes.add(nodeList.get(i).toString());
						breakingNodes.add(nodeList.get(i+1).toString());
					}
				}
				for(Object current : nodeList)
					path += current.toString() + " => ";

				ArrayList<String> colorNodes = new ArrayList<String>();
				ArrayList<String> startAndEnd = new ArrayList<String>();
				for(int i = 0; i < nodeList.size(); i++)
					if(i == 0 || i == nodeList.size() - 1) startAndEnd.add(nodeList.get(i).toString());
					else colorNodes.add(nodeList.get(i).toString());

				//Color the edges and nodes of the path
				switchNodeColor(startAndEnd, "#68EEEA");
				switchNodeColor(colorNodes, HIGHLIGHT_COLOR);
				switchEdgeColor(edgesInPath, EDGES_HIGHLIGHT);

				//color in red the edges that break the sequence in the path
				if(!breakingNodes.isEmpty())
					switchNodeColor(breakingNodes, "#ea6161");
				if(path.length() > 0) path = path.substring(0,path.length() - 3);
				path = "El camino retornado es: " + path;
				infoPanelContent.setText(path);
			}
			else {
				path = "El camino está vacío";
				infoPanelContent.setText(path);
				String error = "No hay un camino especificado.";
				JOptionPane.showMessageDialog(frame, error, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
			}

			if(!message.isEmpty()){
				String error = "";
				error = (structureType == 0 || structureType == 1) ? "Las conexiones" : "Los arcos";
				error += message + " no completan el camino.";
				JOptionPane.showMessageDialog(frame, error, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
			}
		}
		catch(Exception e) {
			path = "El camino retornó error";
			infoPanelContent.setText(path);
			String error = "Hay un error en el camino";
			JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE); 
		}

	}

	
	/**
	 * Adds edges to the structure through the StandardMethods class
	 * @param toAdd String 
	 */
	@SuppressWarnings("rawtypes")
	public void addEdge(String toAdd) {
		defaultColors();
		String notAdded = "";
		String[] edgesSet = toAdd.toString().split(",");
		ArrayList<Edge> edgesList = new ArrayList<Edge>();
		ArrayList<String> nodesList = new ArrayList<String>();

		String operation = "Agregar arco";
		operation = (edgesSet.length > 1) ? operation + "s " : operation + " ";
		operation += toAdd;

		for(String a : edgesSet) {
			if(a.contains(">")) {
				String[] currentEdge = a.split(">");
				//If the any of the nodes don't exist
				if(findNode(currentEdge[0]) == null || findNode(currentEdge[1]) == null)
					notAdded += currentEdge[0] + ">" + currentEdge[1] + ", ";
				//If the edge already exists it's not added again
				else if(sm.findEdge(currentEdge[0], currentEdge[1]) != null) 
					notAdded += currentEdge[0] + ">" + currentEdge[1] + ", ";
				else {
					sm.addEdge(currentEdge[0], currentEdge[1]);
					if(sm.findEdge(currentEdge[0], currentEdge[1]) != null) {
						Edge found = (Edge) sm.findEdge(currentEdge[0], currentEdge[1]);
						edgesList.add(found);
					}
					else notAdded += currentEdge[0] + ">" + currentEdge[1] + ", ";
					nodesList.add(findNode(currentEdge[0]).name);
					nodesList.add(findNode(currentEdge[1]).name);
				}
			}
			else notAdded += a + ", ";
		}
		ArrayList<Edge<Nodo>> edgesToColor = new ArrayList<Edge<Nodo>>();
		removeEdgesAndNodes();
		nodos = createNodes();
		edges = (structureType == 1) ? createEdgesForList() : createEdges();
		addEdgesAndNodes();
		for(Edge current : edgesList) {
			if(structureType == 3) {
				edgesToColor.add(findEdge(current.end.toString(), current.start.toString()));
			}
			if(findEdge(current.start.toString(), current.end.toString()) != null)
				edgesToColor.add(findEdge(current.start.toString(), current.end.toString()));
			else notAdded += current.start.toString() + ">" + current.end.toString();
		}
		//If it's a graph, get disconnected nodes and connect them 
		if(structureType == 2 || structureType == 3) {
			ArrayList<Edge<Nodo>> disconnected = disconnectedNodes();
			switchEdgeColor(disconnected, "#EEEEEE");
		}
		if(sm.isLineal() == null || !sm.isLineal()) {
			mxFastOrganicLayout layout = new  mxFastOrganicLayout(jgxAdapter);
			layout.execute(jgxAdapter.getDefaultParent());
		}
		else {
			mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
			layout.execute(jgxAdapter.getDefaultParent());
		}

		switchNodeColor(nodesList, HIGHLIGHT_COLOR);
		switchEdgeColor(edgesToColor, EDGES_HIGHLIGHT);

		if(!edgesList.isEmpty()) operation += " completado";
		else operation += " no se completó";
		operation += "\nCantidad de nodos: " + nodos.size();
		operation += "\nCantidad de arcos: ";
		operation = structureType == 3 ? operation + edges.size()/2 : operation + edges.size();
		infoPanelContent.setText(operation);

		if(!notAdded.isEmpty()){	
			notAdded = notAdded.substring(0, notAdded.length() - 2);
			String error = "Los arcos " + notAdded + " no se agregaron.";
			JOptionPane.showMessageDialog(frame, error, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
		}	

	}

	/**
	 * Draws the edges set fetched from the StandardMethods class
	 */
	public void showEdges() {
		String operation = "El conjunto de arcos retornó: ";
		ArrayList<Edge<Object>> edgesSet = sm.showEdgesSet();
		if(edgesSet != null && !edgesSet.isEmpty()) {
			String formattedEdges = "";
			for(Edge<Object> edge : edgesSet) {
				formattedEdges += edge.start.toString() + ">" + edge.end.toString() + ",";
				operation += edge.start.toString() + ">" + edge.end.toString() + ", ";
			}
			getEdgesSet(formattedEdges);
			operation = operation.substring(0, operation.length() - 2);
			infoPanelContent.setText(operation);
		}
		else {
			operation += "vacío";
			infoPanelContent.setText(operation);
			String error = "No hay un conjunto de arcos especificado.";
			JOptionPane.showMessageDialog(frame, error, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
		}
	}

	
	/**
	 * Draws the node set fetched from the StandardMethods class
	 */
	@SuppressWarnings("rawtypes")
	public void showNodes() {
		String operation = "El conjunto de nodos retornó: ";
		ArrayList inputSet = sm.showNodeSet();
		if(inputSet != null && !inputSet.isEmpty()) {
			String nodeTags = "";
			for (int i = 0; i < inputSet.size() ; i++) {
				nodeTags += inputSet.get(i).toString() + ",";
				operation += inputSet.get(i).toString() + ", ";
			}
			operation = operation.substring(0, operation.length() - 2);
			getNodeSet(nodeTags);
			infoPanelContent.setText(operation);
		}
		else {
			operation += "vacío";
			infoPanelContent.setText(operation);
			String error = "No hay un conjunto de nodos especificado.";
			JOptionPane.showMessageDialog(frame, error, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
		}

	}

	/**
	 * Sets the message inthe info panel when the input is empty
	 * @param operation Operation that is clicked by the user
	 */
	public void emptyInput(String operation) {
		infoPanelContent.setText(operation + " no se completó");
		String error = "La entrada especificada está vacía";
		JOptionPane.showMessageDialog(frame, error, "Advertencia", JOptionPane.INFORMATION_MESSAGE); 
	}

	/**
	 * Fetches the initial data of the structure and displays it without any of the changes made to it
	 */
	public void restartStructure() {
		try {
			removeEdgesAndNodes();
			createGraph();
			String message = "Estructura reinicializada";
			if(nodos.isEmpty()) message += " vacía";
			else {
				message += "\nCantidad de nodos: " + nodos.size();
				if(structureType == 0 || structureType == 1) {
					message += "\nCantidad de conexiones: ";
				}
				else
					message += "\nCantidad de arcos: ";
				message = structureType == 3 ? message + edges.size()/2 : message + edges.size();
			}
			infoPanelContent.setText(message);
		}
		catch(Exception e) {
			infoPanelContent.setText("Reinicializar estructura no se completó");
			String error = "Se presentó un error al reiniciar la estructura.";
			JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE); 
		}
	}

	public static void main(String[] args) {
		StructureAdapter sa = new StructureAdapter();
		sa.init();
	}
}

