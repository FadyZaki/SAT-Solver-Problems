import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Utility {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner input = new Scanner (System.in);
		
		StringBuilder colors = new StringBuilder();
		int i = 10;
		while (i<=1316) {
			colors.append(i + " " + input.nextLine() + "\n");
			i++;
		}
		
		PrintWriter out = new PrintWriter("colors-specification.txt");
		out.print(colors.toString());
		out.close();
		
	}
	

}
