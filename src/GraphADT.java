import java.util.Iterator;

public interface GraphADT {
  void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException;
  GraphNode getNode(int name) throws GraphException;
  Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException;
  GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException;
  boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException;
}