import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;


public class PercolationStats {

	private int trials=0;
	private double mean=0;
	private double dev=0;

	public PercolationStats(int n, int trials) // perform trials independent
												// experiments on an n-by-n grid
	{
		this.trials = trials;
		Percolation percolation;
		double[] thresholds=new double[trials];
		for (int i = 0; i < trials; i++) {
			percolation = new Percolation(n);
			while (!percolation.percolates()) {
				int rowSeed = StdRandom.uniform(n) + 1;
				int colSeed = StdRandom.uniform(n) + 1;
				percolation.open(rowSeed, colSeed);
			}
			int numberOfOpenSite=percolation.numberOfOpenSites();
			thresholds[i]=numberOfOpenSite/(n*n*1.0);
		}
		mean=StdStats.mean(thresholds);
		dev=StdStats.stddev(thresholds);
	}

	public double mean() // sample mean of percolation threshold
	{
		return mean;
	}

	public double stddev() // sample standard deviation of percolation threshold
	{
		return dev;
	}

	public double confidenceLo() // low endpoint of 95% confidence interval
	{
		return mean-1.96/Math.sqrt(trials);
	}	

	public double confidenceHi() // high endpoint of 95% confidence interval
	{
		return mean+1.96/Math.sqrt(trials);
	}

	public static void main(String[] args) // test client (described below)
	{
		int size = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats percolationStats = new PercolationStats(size, trials);
		System.out.println(String.format("mean                    = %s\r\n" + 
				"stddev                  = %s\r\n" + 
				"95%% confidence interval = [%s, %s]",percolationStats.mean(),percolationStats.stddev(),percolationStats.confidenceLo(),percolationStats.confidenceHi()));
	}
}
