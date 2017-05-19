import java.util.ArrayList;

public class GraphNode {

	private ArrayList<Integer> adjListIndices;
	
	public GraphNode(){
		this.adjListIndices = new ArrayList<Integer>();
	}
	
	public void addEdge(Integer adjNodeIndex) {
		this.adjListIndices.add(adjNodeIndex);
	}

	public ArrayList<Integer> getAdjList() {
		return adjListIndices;
	}
	
	
}
