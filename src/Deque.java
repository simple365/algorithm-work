import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node first = null;
	private Node last = null;
	private int size = 0;

	private class Node {
		Item item;
		Node next;
		Node previous;
	}

	public Deque() // construct an empty deque
	{

	}

	public boolean isEmpty() // is the deque empty?
	{
		return size == 0;
	}

	public int size() // return the number of items on the deque
	{
		return size;
	}

	public void addFirst(Item item) // add the item to the front
	{
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		Node node = new Node();
		node.item = item;
		if (size == 0) {
			last = node;
		} else if (size == 1) {
			node.next = last;
			last.previous = node;
		} else {
			node.next = first;
			first.previous = node;
		}
		first = node;
		size++;
	}

	public void addLast(Item item) // add the item to the end
	{
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		Node node = new Node();
		node.item = item;
		if (size == 0) {
			first = node;
		} else if (size == 1) {
			node.previous = first;
			first.next = node;
		} else {
			last.next = node;
			node.previous = last;
		}
		last = node;
		size++;
	}

	public Item removeFirst() // remove and return the item from the front
	{
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		Item target = first.item;
		if (size == 1) {
			first = null;
			last = null;
		} else if (size == 2) {
			first = last;
			last.previous = null;
		} else {
			Node next = first.next;
			first = next;
			first.previous = null;
		}
		size--;
		return target;
	}

	public Item removeLast() // remove and return the item from the end
	{
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		Item target = last.item;
		if (size == 1) {
			first = null;
			last = null;
		} else if (size == 2) {
			last = first;
			first.next = null;
		} else {
			Node previous = last.previous;
			previous.next = null;
			last = previous;
		}
		size--;
		return target;
	}

	public Iterator<Item> iterator() // return an iterator over items in order from front to end
	{
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}
		
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (current == null) {
				throw new java.util.NoSuchElementException();
			}
			Item item = current.item;
			current=current.next;
			return item;
		}
	}

	public static void main(String[] args) // unit testing (optional)
	{
		Deque<Integer> deque=new Deque<>();
		deque.addFirst(2);
		deque.addLast(3);
		Iterator<Integer> iterator=deque.iterator();
		while (iterator.hasNext()) {
			int flg= iterator.next();
			System.out.println(flg);
		}
		deque.removeFirst();
		deque.removeLast();
	}
}
