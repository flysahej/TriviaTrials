import java.util.*;

public class Graph implements GraphADT {
	private Map<Integer, GraphNode> nodes;
	private Map<GraphNode, List<GraphEdge>> adjacencyList;

	public Graph(int n) {
		nodes = new HashMap<>();
		adjacencyList = new HashMap<>();
		for (int i = 0; i < n; i++) {
			GraphNode node = new GraphNode(i);
			nodes.put(i, node);
			adjacencyList.put(node, new ArrayList<>());
		}
	}

	public void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException {
		if (!nodes.containsKey(u.getName()) || !nodes.containsKey(v.getName())) {
			throw new GraphException("One or both nodes do not exist.");
		}
		GraphEdge edge = new GraphEdge(u, v, edgeType, label);
		adjacencyList.get(u).add(edge);
		adjacencyList.get(v).add(edge); // Ensure the edge is added to both nodes' adjacency lists
	}

	public GraphNode getNode(int name) throws GraphException {
		if (!nodes.containsKey(name)) {
			throw new GraphException("Node does not exist.");
		}
		return nodes.get(name);
	}

	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		if (!nodes.containsKey(u.getName())) {
			throw new GraphException("Node does not exist.");
		}
		return adjacencyList.get(u).iterator();
	}

	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		if (!nodes.containsKey(u.getName()) || !nodes.containsKey(v.getName())) {
			throw new GraphException("One or both nodes do not exist.");
		}
		for (GraphEdge edge : adjacencyList.get(u)) {
			if (edge.secondEndpoint().equals(v) || edge.firstEndpoint().equals(v)) { // Check both endpoints
				return edge;
			}
		}
		throw new GraphException("No edge exists between the nodes.");
	}

	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		if (!nodes.containsKey(u.getName()) || !nodes.containsKey(v.getName())) {
			throw new GraphException("One or both nodes do not exist.");
		}
		for (GraphEdge edge : adjacencyList.get(u)) {
			if (edge.secondEndpoint().equals(v) || edge.firstEndpoint().equals(v)) { // Check both endpoints
				return true;
			}
		}
		return false;
	}
}