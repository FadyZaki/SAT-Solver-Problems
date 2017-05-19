import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;

public class SATSolver {

	public static boolean decideSatisfiability(String cnfFileName, ArrayList<Integer[]> models) {
		ISolver solver = new ModelIterator(SolverFactory.newDefault());
		solver.setTimeout(3600); // 1 hour timeout
		Reader reader = new DimacsReader(solver);
		boolean sat = false;
		try {
			IProblem problem = reader.parseInstance(cnfFileName);
			while (problem.isSatisfiable()) {
				sat = true;
				int[] model = problem.model();
				models.add(Arrays.stream(model).boxed().toArray(Integer[]::new));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (ParseFormatException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (ContradictionException e) {
			System.out.println("Unsatisfiable (trivial)!");
		} catch (TimeoutException e) {
			System.out.println("Timeout, sorry!");
		}
		
		
		return sat;
	}

}
