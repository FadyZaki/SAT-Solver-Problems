import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ColorsInitializer {

	public static HashMap<Integer, String> initializeColors(int numOfColors) {
		HashMap<Integer, String> colorsIndexToNameMap = new HashMap<Integer, String>();
		File colorSpecificationFile = new File("colors-specification.txt");
		
        
		try {
			Scanner input = new Scanner(colorSpecificationFile);
	        
			int i=0;
	        while (i++ < numOfColors) {
	        	String line = input.nextLine();
	        	
	        	colorsIndexToNameMap.put(Integer.valueOf(line.split(" ")[0]), line.substring(line.split(" ")[0].length() + 1, line.length()));
	        	
	        }
	        input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return colorsIndexToNameMap;
		
	}
	
}
