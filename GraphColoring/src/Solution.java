import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class Solution {

	private static final int NUM_OF_COLORS = 1315;
	private static final HashMap<Integer, String> COLOR_INDEX_TO_NAME_MAP = ColorsInitializer.initializeColors(NUM_OF_COLORS);
	private static final String CNF_FILE_NAME = "graph-cnf-file.cnf";

	public static void main(String[] args) throws FileNotFoundException {

		//Choose here the graph file you would like to use
		File specificationFile = new File("graph-specification-small.txt");

		int numOfVertices = 0;
		int[][] edges = new int[0][2];

		try {
			Scanner input = new Scanner(specificationFile);
			numOfVertices = input.nextInt();
			int numOfEdges = input.nextInt();
			edges = new int[numOfEdges][2];

			int i = 0;
			while (i < numOfEdges) {
				edges[i][0] = input.nextInt();
				edges[i][1] = input.nextInt();
				i++;
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Graph g = new Graph(numOfVertices, edges);

		// Determine if Graph g is k colorable or not
		int k = 3;
		if (graphIsKColorable(g, k, null))
			System.out.println("Colorable with " + k + " colors!");
		else
			System.out.println("Uncolorable with " + k + " colors!");

		// Determine if Graph g is k colorable or not (with Additional
		// Constraints)
		// Constraints should be described in CNF DIMACS format
		ArrayList<Integer[]> constraints = new ArrayList<Integer[]>();
		// This constraint means that Vertex number "0" should be colored with
		// either the first or the second color
		constraints.add(new Integer[] { (0 * k + 1), (0 * k + 2), 0 });
		// These two constraints mean that Vertex number "1" should not be colored
		// with the first color or the second color
		constraints.add(new Integer[] { -(1 * k + 1), 0 });
		constraints.add(new Integer[] { -(1 * k + 2), 0 });
		if (graphIsKColorable(g, k, constraints))
			System.out.println("Colorable with " + k + " colors!");
		else
			System.out.println("Uncolorable with " + k + " colors!");

		// Find Graph Chromatic Number
		int chromaticNumber = findGraphChromaticNumber(g, null);
		System.out.println("Chromatic Number is : " + chromaticNumber);
		System.out.println("===================================");

		// Find all possible ways of coloring Graph g with its chromatic number
		ArrayList<Integer[]> models = findAllPossibleColoringsUsingChromaticNumber(g, null);
		int i = 0;
		for (Integer[] model : models) {
			System.out.println("Model " + ++i);
			HashMap<Integer, Integer> vertexColorMap = ResultMapper.mapResultToProblemDomain(chromaticNumber, model);

			for (Map.Entry<Integer, Integer> e : vertexColorMap.entrySet()) {
				System.out.println("Vertex number : " + e.getKey() + " : " + COLOR_INDEX_TO_NAME_MAP.get(e.getValue()));
			}

			System.out.println("===================================");
		}

	}

	private static boolean graphIsKColorable(Graph g, int k, ArrayList<Integer[]> constraints) throws FileNotFoundException {
		CNFBuilder.buildCnfFile(g, k, constraints, CNF_FILE_NAME);
		return SATSolver.decideSatisfiability(CNF_FILE_NAME, new ArrayList<Integer[]>());
	}

	private static int findGraphChromaticNumber(Graph g, ArrayList<Integer[]> constraints) throws FileNotFoundException {
		int chromaticNumber = -1;
		int start = 1;
		int end = g.getNumberOfVertices();
		while (start <= end) {
			int middle = (start + end) / 2;

			CNFBuilder.buildCnfFile(g, middle, null, CNF_FILE_NAME);
			if (SATSolver.decideSatisfiability(CNF_FILE_NAME, new ArrayList<Integer[]>())) {
				chromaticNumber = middle;
				end = middle - 1;
			} else {
				start = middle + 1;
			}
		}

		return chromaticNumber;
	}

	private static ArrayList<Integer[]> findAllPossibleColoringsUsingChromaticNumber(Graph g, ArrayList<Integer[]> constraints) throws FileNotFoundException {
		int chromaticNumber = findGraphChromaticNumber(g, constraints);

		CNFBuilder.buildCnfFile(g, chromaticNumber, constraints, CNF_FILE_NAME);
		ArrayList<Integer[]> models = new ArrayList<Integer[]>();
		SATSolver.decideSatisfiability(CNF_FILE_NAME, models);
		return models;
	}

}
