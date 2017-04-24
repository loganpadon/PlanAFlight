
public class LinkedListRecreation {
	private ListNode start;
	private int elementsCount;

	public LinkedListRecreation() {
		start = null;
		elementsCount = 0;
	}

	public int size() {
		return elementsCount;
	}

	public void add(Object data) {
		if (start != null) {
			ListNode node = start;
			for (int i = 0; i < size(); i++) {
				node = node.getNext();
			}
			ListNode addNode = new ListNode(data);
			node.setNext(addNode);
		} else {
			start = new ListNode(data);
		}
	}

	public Object get(int i) {
		if (i >= elementsCount) {
			return null;
		}
		ListNode node = start;
		for (int j = 0; j < i; j++) {
			node = node.getNext();
		}
		return node.getData();
	}
	
	public ListNode getLast(){
		ListNode node = start;
		for(int i = 0; i < elementsCount; i++){
			node = node.getNext();
		}
		return node;
	}

	public boolean add(int i, Object data) {
		if (i > elementsCount)
			return false;
		ListNode node = start;
		for (int j = 0; j < i; j++) {
			node = node.getNext();
		}
		ListNode addNode = new ListNode(data);
		node.setNext(addNode);
		elementsCount++;
		return true;
	}

	public Object remove(int i) {
		if (i > elementsCount)
			return null;
		ListNode lower = null;
		ListNode higher = null;
		ListNode removed = start;
		for (int j = 0; j < i; j++) {
			if (j == i) {
				lower = removed;
			}
			removed = removed.getNext();
			if (j == i) {
				higher = removed.getNext();
			}

		}
		lower.setNext(higher);
		elementsCount--;
		return removed.getData();
	}
}
