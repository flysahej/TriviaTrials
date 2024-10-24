public class GraphEdge {
	private GraphNode u;
	private GraphNode v;
	private int type;
	private String label;

	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
	}

	public GraphNode firstEndpoint() {
		return this.u;
	}

	public GraphNode secondEndpoint() {
		return this.v;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int newType) {
		this.type = newType;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String newLabel) {
		this.label = newLabel;
	}
}