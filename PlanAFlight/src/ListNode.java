
public class ListNode {
	private Object data;
	private ListNode next;

	public ListNode(Object data, ListNode next) {
		setData(data);
		setNext(next);
	}

	public ListNode(Object data) {
		setData(data);
		setNext(null);
	}

	public ListNode getNext() {
		return next;
	}

	public void setNext(ListNode next) {
		this.next = next;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
