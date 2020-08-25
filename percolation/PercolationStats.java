import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private Percolation p;
    private int t;
    private double[] stats;
    private double mu;
    private double sig;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        t = trials;
        if (n < 1 || t < 1)
            throw new IllegalArgumentException("Grid too small/no trials");

        stats = new double[t];

        int r;
        int c;
        boolean percolates;

        for (int i = 0; i < t; i++) {
            p = new Percolation(n);
            percolates = false;
            while(! percolates) {
                r = StdRandom.uniform(1, n+1);
                c = StdRandom.uniform(1, n+1);
                p.open(r, c);
                percolates = percolates || p.percolates();
            }
            stats[i] = p.numberOfOpenSites()/((double) n*n);
        }
        mu = StdStats.mean(stats);
        sig = StdStats.stddev(stats);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mu;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return sig;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mu - 1.96*sig/Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mu + 1.96*sig/Math.sqrt(t);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats s = new PercolationStats(n,t);
        StdOut.println("mean = "+ s.mean());
        StdOut.println("stddev = "+ s.stddev());
        StdOut.println("95% confidence interval = ["+ s.confidenceLo()
                       + ", " + s.confidenceHi() + "]");
    }
}
