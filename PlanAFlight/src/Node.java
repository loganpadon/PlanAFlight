import java.util.LinkedList;

public class Node {
	private String name;
	private LinkedList<Node> connections;
	private LinkedList<Integer> price;
	private LinkedList<Integer> distance;
	private boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Node(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Node> getConnections() {
		return connections;
	}

	public void setConnections(LinkedList<Node> connections) {
		this.connections = connections;
	}

	public int getConnectionsLength() {
		return connections.size();
	}

	public int getPrice(int i) {
		return price.get(i);
	}

	public void addPrice(int i) {
		price.add(i);
	}

	public int getDistance(int i) {
		return distance.get(i);
	}

	public void addDistance(int i) {
		distance.add(i);
	}

	public void addConnection(Node node, int price, int distance) {
		connections.add(node);
		addPrice(price);
		addDistance(distance);
	}

	public Node getNode(int i) {
		return connections.get(i);
	}

	public Node getNode(String name) {
		for (int i = 0; i < connections.size(); i++) {
			if (connections.get(i).getName() == name)
				return connections.get(i);
		}
		return null;
	}

}
