import java.io.IOException;

public class PlanAFlight {

	public static void main(String[] args) throws NumberFormatException, IOException {
		Tree tree = new Tree(args[0]);
		tree.getAllPaths(args[1], args[2]);
	}
}
