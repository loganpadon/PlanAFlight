
public class StackRecreation {
	LinkedListRecreation stack;
	ListNode top;
	int size;
	
	public StackRecreation(){
		stack = null;
		top = null;
		size = 0;
	}
	public void push(Object object){
		ListNode node = new ListNode(object);
		stack.add(node);
		top = node;
		size++;
	}
	public Object pop(){
		size--;
		Object rv = stack.remove(size);
		top = stack.getLast();
		return rv;
	}
}
