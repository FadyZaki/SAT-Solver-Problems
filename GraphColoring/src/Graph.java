
public class Graph {

	private GraphNode[] vertices;
	
	public Graph(int numOfVertices, int[][] edges) {
		vertices = new GraphNode[numOfVertices];
		for (int i=0; i<vertices.length; i++){
			vertices[i] = new GraphNode();
		}
		for (int i=0; i<edges.length; i++){
			vertices[edges[i][0]].addEdge(edges[i][1]);
			vertices[edges[i][1]].addEdge(edges[i][0]);
		}
	}
	
	
	public int getNumberOfVertices() {
		return vertices.length;
	}
	
	public GraphNode[] getVertices() {
		return vertices;
	}
	
}
