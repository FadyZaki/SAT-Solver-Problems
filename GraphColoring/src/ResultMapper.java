import java.util.HashMap;

public class ResultMapper {

	public static HashMap<Integer, Integer> mapResultToProblemDomain(int numOfColors, Integer[] model) {
		HashMap<Integer, Integer> vertexToColorMap = new HashMap<Integer, Integer>();
		for (Integer i : model) {
			if (i > 0) {
				Integer vertexIndex = (i-1)/numOfColors;
				Integer colorIndex = i%numOfColors != 0 ? i%numOfColors : numOfColors;
				
				vertexToColorMap.put(vertexIndex, colorIndex);
			}
		}
		
		return vertexToColorMap;
	}

}
