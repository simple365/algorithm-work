import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] items = null;
	private int size = 0;

	public RandomizedQueue() // construct an empty randomized queue
	{
		items = (Item[]) new Object[1];
	}

	public boolean isEmpty() // is the randomized queue empty?
	{
		return size == 0;
	}

	public int size() // return the number of items on the randomized queue
	{
		return size;
	}

	public void enqueue(Item item) // add the item
	{
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		if (size == items.length) {
			resize(2 * items.length);
		}
		items[size]=item;
		size++;
	}

	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++)
			copy[i] = items[i];
		items = copy;
	}

	public Item dequeue() // remove and return a random item
	{
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		int removeIndex=StdRandom.uniform(size);
		Item target=items[removeIndex];
		for(int i=removeIndex;i<size-1;i++) {
			items[i]=items[i+1];
		}
		items[size-1]=null;
		if (size > 0 && size == items.length/4) resize(items.length/2);
		size--;
		return target;
	}

	public Item sample() // return a random item (but do not remove it)
	{
		if (size == 0) {
			throw new java.util.NoSuchElementException();
		}
		int removeIndex=StdRandom.uniform(size);
		Item target=items[removeIndex];
		return target;
	}

	public Iterator<Item> iterator() // return an independent iterator over items in random order
	{
		return new ArrayIterator();
	}
	
	private class ArrayIterator implements Iterator<Item> {

		RandomizedQueue<Integer> removeIndexesQueue=new RandomizedQueue<Integer>();
		int[] removeIndexex=new int[size];
		int currentIndex=-1;
		
		
		public ArrayIterator() {
			for(int i=0;i<size;i++) {
				removeIndexesQueue.enqueue(i);
			}
			for(int i=0;i<size;i++) {
				removeIndexex[i]=removeIndexesQueue.dequeue();
			}
			
		}
		
		@Override
		public boolean hasNext() {
			return currentIndex+1<size;
		}
		
		@Override
		public Item next() {
			if (currentIndex+1 == size) {
				throw new java.util.NoSuchElementException();
			}
			currentIndex++;
			return items[removeIndexex[currentIndex]];
		}
		
	}

	public static void main(String[] args) // unit testing (optional)
	{
		RandomizedQueue<Integer> rand=new RandomizedQueue<>();
		rand.enqueue(34);
		rand.enqueue(1);
		rand.enqueue(12);
		Iterator<Integer> iterator=rand.iterator();
		while (iterator.hasNext()) {
			int flg= iterator.next();
			System.out.println(flg);
		}
//		int k=2;
//		while((k=rand.dequeue())!=0) {
//			System.out.println(k);
//		}
	}
}
