
import edu.princeton.cs.algs4.StdIn;
public class Permutation {
public static void main(String[] args) {
	int k=Integer.parseInt(args[0]);
	String line=null;
	RandomizedQueue<String> queue=new RandomizedQueue<String>();
	while((line=StdIn.readString())!=null) {
		queue.enqueue(line);
	};
	for(int i=0;i<k;i++) {
		System.out.println(queue.dequeue());
	}
	
}
}
