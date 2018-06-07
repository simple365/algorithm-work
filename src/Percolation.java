
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF grid;
//	virtual top could use as open judge
	private int[] virtualTop;
	private int[] virtualBottom;
	private int openSites=0;
	private int length;
	private boolean percolated=false;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {
		if(n<=0)
			throw new java.lang.IllegalArgumentException();
		length = n;
		grid = new WeightedQuickUnionUF(length * length);
		virtualBottom = new int[n * n];
		virtualTop = new int[n * n];
		virtualTop[0]=-1;
	}

	private int convert2d(int row,int col) {
		if (row > length || row < 1) {
			throw new java.lang.IllegalArgumentException("row " + row + " is out the size " + length);
		}

		if (col > length || col < 1) {
			throw new java.lang.IllegalArgumentException("col " + col + " is out the size " + length);
		}
		int currentIndex = (row - 1) * length + col-1;
		return currentIndex;
	}
	
	public void open(int row, int col) // open site (row, col) if it is not open
										// already
	{
		int currentIndex = convert2d(row, col);
		if (!isOpen(currentIndex)) {
			int upNeighborIndex = row > 1 ? currentIndex - length : -1;
			int downNeighborIndex = row < length ? currentIndex + length : -1;
			int leftNeighborIndex = col > 1 ? currentIndex - 1 : -1;
			int rightNeighborIndex = col < length ? currentIndex + 1 : -1;
			int[] neighborIndexes = new int[] { upNeighborIndex, leftNeighborIndex, rightNeighborIndex,
					downNeighborIndex };
			int largestBottom=currentIndex;
			int leastTop=currentIndex;
			for (int i = 0; i < neighborIndexes.length; i++) {
				if (neighborIndexes[i] > -1 && isOpen(neighborIndexes[i])) {
					int parent=grid.find(neighborIndexes[i]);
					
					if(leastTop>neighborIndexes[i]) leastTop=neighborIndexes[i];
					int topPoint=parent;
					if(virtualTop[topPoint]<topPoint) {
						topPoint=virtualTop[topPoint];
					}
					if(leastTop>topPoint) leastTop=topPoint;
					
					if(largestBottom<neighborIndexes[i]) largestBottom=neighborIndexes[i];	
					int bottomPoint=parent;
					if(virtualBottom[bottomPoint]>topPoint) {
						bottomPoint=virtualBottom[bottomPoint];
					}
					if(bottomPoint>largestBottom) largestBottom=bottomPoint;
					grid.union(currentIndex, neighborIndexes[i]);
				}
			}
			int parent=grid.find(currentIndex);
			virtualTop[parent]=leastTop;
			virtualBottom[parent]=largestBottom;
			virtualTop[currentIndex]=leastTop;
			virtualBottom[currentIndex]=largestBottom;
			if(leastTop<length&&largestBottom>=(length-1)*length) {
				percolated=true;
			}
			openSites++;
		}
	}

	public boolean isOpen(int row, int col) // is site (row, col) open?
	{
		int currentIndex = convert2d(row, col);
		return isOpen(currentIndex);
	}
	
	private boolean isOpen(int index) {
		if (index==0) {
			return virtualTop[0]==0;
		}else {
			return virtualTop[index]+virtualBottom[index]>0;
		}
	}

	public boolean isFull(int row, int col) // is site (row, col) full?
	{
		int currentIndex = convert2d(row, col);
		if(!isOpen(row, col)) return false;
		if(virtualTop[grid.find(currentIndex)]<this.length) {
			return true;
		}
		return false;
	}

	public int numberOfOpenSites() // number of open sites
	{
		return openSites;
	}

	public boolean percolates() // does the system percolate?
	{
		return percolated;
	}

	public static void main(String[] args) // test client (optional)
	{
		int size=6;
		Percolation percolation = new Percolation(size);
		System.out.println(percolation.numberOfOpenSites());
		percolation.open(1, 6);
		System.out.println(percolation.isOpen(1, 6));
		int flag=0;
		while(percolation.numberOfOpenSites()<size*size) {
			int rowSeed = StdRandom.uniform(size) + 1;
			int colSeed = StdRandom.uniform(size) + 1;
			percolation.open(rowSeed, colSeed);
			System.out.println(rowSeed+" "+colSeed);
			flag++;
		}
		System.out.println(percolation.percolates()+" "+flag+" "+percolation.numberOfOpenSites());
	}
}
