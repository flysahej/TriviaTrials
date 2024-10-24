import java.util.*;
import java.io.*;

public class Maze {
	private Graph graph;
	private GraphNode entrance;
	private GraphNode exit;
	private int coins;
	private int width; // Added this to store the width of the maze

	public Maze(String inputFile) throws MazeException {
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
			int scale = Integer.parseInt(reader.readLine());
			width = Integer.parseInt(reader.readLine());  // Store the width here
			int length = Integer.parseInt(reader.readLine());
			coins = Integer.parseInt(reader.readLine());

			int totalRooms = width * length;
			graph = new Graph(totalRooms);

			for (int i = 0; i < length; i++) {
				String row1 = reader.readLine();
				String row2 = reader.readLine();

				for (int j = 0; j < width; j++) {
					char roomChar = row1.charAt(j * 2);
					if (roomChar == 's') {
						entrance = graph.getNode(i * width + j);
					} else if (roomChar == 'x') {
						exit = graph.getNode(i * width + j);
					}

					if (j < width - 1) {
						char horizontalEdgeChar = row1.charAt(j * 2 + 1);
						addEdge(i, j, i, j + 1, horizontalEdgeChar);
					}

					if (i < length - 1) {
						char verticalEdgeChar = row2.charAt(j * 2);
						addEdge(i, j, i + 1, j, verticalEdgeChar);
					}
				}
			}
		} catch (IOException | NumberFormatException | GraphException e) {
			throw new MazeException("Error reading input file: " + e.getMessage());
		}
	}

	private void addEdge(int row1, int col1, int row2, int col2, char edgeChar) throws GraphException {
		int node1 = row1 * width + col1;  // Correct node indexing
		int node2 = row2 * width + col2;  // Correct node indexing

		int edgeType = Character.isDigit(edgeChar) ? Character.getNumericValue(edgeChar) : 0;
		String label = edgeChar == 'w' ? "wall" : edgeChar == 'c' ? "corridor" : "door";
		if (!label.equals("wall")) {
			graph.insertEdge(graph.getNode(node1), graph.getNode(node2), edgeType, label);
		}
	}

	public Graph getGraph() throws MazeException {
		if (graph == null) {
			throw new MazeException("Graph is null.");
		}
		return graph;
	}

	public Iterator<GraphNode> solve() {
		Stack<GraphNode> path = new Stack<>();
		if (findPath(entrance, path, coins)) {
			return path.iterator();
		}
		return null;
	}

	private boolean findPath(GraphNode current, Stack<GraphNode> path, int remainingCoins) {
		System.out.println("Visiting node: " + current.getName()); // Debugging output
		current.mark(true);
		path.push(current);

		if (current.equals(exit)) {
			System.out.println("Exit found at node: " + current.getName()); // Debugging output
			return true;
		}

		try {
			Iterator<GraphEdge> edges = graph.incidentEdges(current);
			while (edges.hasNext()) {
				GraphEdge edge = edges.next();
				GraphNode nextNode = edge.secondEndpoint();
				if (!nextNode.isMarked()) {
					int edgeType = edge.getType();
					if ((edgeType == 0 || remainingCoins >= edgeType) && findPath(nextNode, path, remainingCoins - edgeType)) {
						return true;
					}
				}
			}
		} catch (GraphException e) {
			System.err.println("Error: " + e.getMessage());
		}

		System.out.println("Backtracking from node: " + current.getName()); // Debugging output
		path.pop();
		current.mark(false);
		return false;
	}
}