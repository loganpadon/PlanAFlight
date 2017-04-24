import java.util.LinkedList;
import java.util.Stack;
import java.io.*;

public class Tree {
	private LinkedList<Node> tree;
	private LinkedList<Stack<Node>> allPaths;
	private LinkedList<LinkedList<LinkedList<Node>>> pathsByCity;

	private Node getStartingNode(String name) {
		for (int i = 0; i < tree.size(); i++) {
			if (tree.get(i).getName() == name)
				return tree.get(i);
		}
		return null;
	}

	@SuppressWarnings("null")
	public LinkedList<Stack<Node>> findAllPaths(String name) {
		Stack<Node> stack = null;
		stack.push(getStartingNode(name));
		findAllPathsHelper(name, stack);
		return allPaths;
	}

	public LinkedList<LinkedList<Node>> findPathsByName(String starting, String destination) {
		LinkedList<LinkedList<Node>> pathsToDestination = new LinkedList<LinkedList<Node>>();
		for (int i = 0; i < pathsByCity.size(); i++) {
			if (pathsByCity.get(i).get(0).get(0).getName() == starting) {
				for (int j = 0; j < pathsByCity.get(i).size(); i++) {
					if (pathsByCity.get(i).get(j).get(pathsByCity.get(i).get(j).size() - 1).getName() == destination)
						pathsToDestination.add(pathsByCity.get(i).get(j));
				}
			}
		}
		return pathsToDestination;
	}

	private LinkedList<LinkedList<Node>> findBestPaths(LinkedList<LinkedList<Node>> paths, char metric) {
		LinkedList<LinkedList<Node>> truePaths = new LinkedList<LinkedList<Node>>();
		for (int i = 0; i < paths.size(); i++) {
			if (truePaths.size() < 3) {
				truePaths.add(paths.get(i));
			} else {
				int pathCost = calculateCost(paths.get(i), metric);
				for (int j = 0; j < truePaths.size(); j++) {
					int maxPathCost = calculateCost(truePaths.get(j), metric);
					if (pathCost < maxPathCost) {
						switch (j) {
						case 0:
							truePaths.add(2, truePaths.get(1));
							truePaths.add(1, truePaths.get(0));
							truePaths.add(0, paths.get(i));
							break;
						case 1:
							truePaths.add(2, truePaths.get(1));
							truePaths.add(1, paths.get(i));
							break;
						case 2:
							truePaths.add(2, paths.get(i));
							break;
						default:
							System.out.println("Error");
						}
					}
				}
			}
		}
		return truePaths;
	}

	private int calculateCost(LinkedList<Node> path, char metric) {
		int cost = 0;
		if (metric == 'C') {
			for (int i = 0; i < path.size() - 1; i++) {
				Node node = path.get(i);
				for (int j = 0; j < node.getConnectionsLength(); j++) {
					if (path.get(i + 1).getName() == node.getNode(j).getName()) {
						cost += node.getPrice(j);
					}
				}
			}
		} else {
			for (int i = 0; i < path.size() - 1; i++) {
				Node node = path.get(i);
				for (int j = 0; j < node.getConnectionsLength(); j++) {
					if (path.get(i + 1).getName() == node.getNode(j).getName()) {
						cost += node.getDistance(j);
					}
				}
			}
		}
		return cost;
	}

	public void getAllPaths(String infile, String outfile) throws IOException, FileNotFoundException {
		BufferedReader in = new BufferedReader(new FileReader(infile));
		BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
		int tripsCount = Integer.parseInt(in.readLine());
		for (int i = 0; i < tripsCount; i++) {
			String[] inputs = in.readLine().split("\\|");
			out.write("Flight " + Integer.toString(i + 1) + ": " + inputs[0] + ", " + inputs[1] + "(" + inputs[2]
					+ ")\n");
			LinkedList<LinkedList<Node>> paths = findPathsByName(inputs[0], inputs[1]);
			LinkedList<LinkedList<Node>> bestPaths = findBestPaths(paths, inputs[3].charAt(0));
			for (int j = 0; j < bestPaths.size(); j++) {
				out.write("Path " + Integer.toString(j + 1) + ": ");
				LinkedList<Node> path = bestPaths.get(j);
				for (int k = 0; k < path.size(); k++) {
					out.write(path.get(k).getName());
					if (k != path.size() - 1) {
						out.write(" -> ");
					}
				}
				out.write("Time: " + calculateCost(path, 'T') + " Cost: " + calculateCost(path, 'C') + "\n");
			}
		}
		out.close();
		in.close();
	}

	public void findAllPathsHelper(String name, Stack<Node> stack) {
		Node currentNode = stack.peek();
		for (int i = 0; i < currentNode.getConnectionsLength(); i++) {
			if (currentNode.getNode(i) == null) {
				stack.peek().setChecked(true);
				stack.pop();
			} else if (currentNode.getNode(i).isChecked()) {
				continue;
			} else if (currentNode.getNode(i).getName() == name) {
				stack.push(currentNode.getNode(i));
				addStackToList(stack);
			}
			stack.push(currentNode.getNode(i));
			findAllPathsHelper(name, stack);
		}
	}

	public void addStackToList(Stack<Node> stack) {
		LinkedList<Node> path = new LinkedList<Node>();
		for (int i = 0; i < stack.size(); i++) {
			path.add(null);
		}
		for (int i = path.size() - 1; i >= 0; i--) {
			path.addFirst(stack.pop());
		}
		boolean added = false;
		for (int i = 0; i < allPaths.size(); i++) {
			if (pathsByCity.get(i).get(0).get(0).getName() == path.get(0).getName()) {
				pathsByCity.get(i).add(path); // ASDF
				added = true;
			}
		}
		if (!added) {
			LinkedList<LinkedList<Node>> e = new LinkedList<LinkedList<Node>>();
			e.add(path);
			pathsByCity.add(e);
		}

	}

	public Tree(String fileName) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		int connectionsNum = Integer.parseInt(in.readLine());
		for (int i = 0; i < connectionsNum; i++) {
			String[] inputs = in.readLine().split("\\|");
			Node node1 = null;
			for (int j = 0; j < tree.size(); j++) {
				if (tree.get(j).getName() == inputs[0]) {
					node1 = tree.get(j);
					break;
				}
				if (j == tree.size() - 1) {
					node1 = new Node(inputs[0]);
					tree.add(node1);
				}
			}
			Node node2 = null;
			for (int j = 0; j < tree.size(); j++) {
				if (tree.get(j).getName() == inputs[1]) {
					node2 = tree.get(j);
					break;
				}
				if (j == tree.size() - 1) {
					node2 = new Node(inputs[1]);
					tree.add(node2);
				}
			}
			node1.addConnection(node2, Integer.parseInt(inputs[2]), Integer.parseInt(inputs[3]));
		}
		in.close();
	}

}
