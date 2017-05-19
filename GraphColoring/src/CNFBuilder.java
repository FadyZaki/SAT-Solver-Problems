import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CNFBuilder {

	public static void buildCnfFile(Graph g, int numOfColors, ArrayList<Integer[]> constraints, String cnfFileName) throws FileNotFoundException {

		File cnfFile = new File(cnfFileName);

		PrintWriter pw = new PrintWriter(cnfFile);

		ArrayList<String> clauses = new ArrayList<String>();

		for (int i = 0; i < g.getNumberOfVertices(); i++) {
			StringBuilder currentClause = new StringBuilder();
			// at least one color
			for (int j = 1; j <= numOfColors; j++) {
				int variable = (i * numOfColors) + j;
				currentClause.append(variable + " ");
			}
			currentClause.append("0");
			clauses.add(currentClause.toString());
			// at most one color
			for (int j = 1; j < numOfColors; j++) {
				for (int k = j + 1; k <= numOfColors; k++) {
					int notVariableColorOne = -((i * numOfColors) + j);
					int notVariableColorTwo = -((i * numOfColors) + k);
					clauses.add(new String(notVariableColorOne + " " + notVariableColorTwo + " 0"));
				}
			}

		}

		for (int currentNodeIndex = 0; currentNodeIndex < g.getNumberOfVertices(); currentNodeIndex++) {
			GraphNode currentNode = g.getVertices()[currentNodeIndex];
			for (Integer adjNodeIndex : currentNode.getAdjList()) {
				for (int j = 1; j <= numOfColors; j++) {
					String currentClause = new String();
					int notCurrentNodeColor = -((currentNodeIndex * numOfColors) + j);
					int notAdjacentNodeColor = -((adjNodeIndex * numOfColors) + j);
					currentClause = notCurrentNodeColor + " " + notAdjacentNodeColor + " 0";
					clauses.add(currentClause);
				}
			}
		}

		if (constraints != null) {
			for (Integer[] constraint : constraints) {
				StringBuilder clause = new StringBuilder();
				for (int i = 0; i < constraint.length-1; i++) {
					clause.append(constraint[i] + " ");
				}
				clause.append(constraint[constraint.length-1]);
				clauses.add(clause.toString());
			}
		}

		int numOfVariables = g.getNumberOfVertices() * numOfColors;
		int numOfClauses = clauses.size();
		pw.println("p cnf " + numOfVariables + " " + numOfClauses);
		for (String clause : clauses) {
			pw.println(clause);
		}
		pw.close();
	}

}
