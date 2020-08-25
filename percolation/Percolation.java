import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /*
     * Auxiliary functions and variables
     */
    private WeightedQuickUnionUF gridFull;
    private WeightedQuickUnionUF gridPercolates;
    private int n;
    private int top;
    private int bot;
    private boolean[] openSites;
    private int numberOfOpenSites;

    private int getIndex(int row, int col) {
        return (row-1)*n + col;
    }

    /*
     * Here is where the actual implementation begins
     */

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 1)
            throw new IllegalArgumentException("Cannot create a grid with less than 1 squares");

        top = 0;
        bot = n*n+1;
        numberOfOpenSites = 0;
        this.n = n;
        gridFull = new WeightedQuickUnionUF(n*n+2);
        gridPercolates = new WeightedQuickUnionUF(n*n+2);
        // auxiliary
        openSites = new boolean[n*n+2];
        openSites[0] = true;
        openSites[n*n+1] = false;
        for(int i = 1; i < n*n+1; i++)
            openSites[i] = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Col/row index out of bounds");

        if (isOpen(row, col))
            return;

        int i = getIndex(row, col);

        int above = getIndex(row-1, col);
        int below = getIndex(row+1, col);
        int left = getIndex(row, col-1);
        int right = getIndex(row, col+1);

        if (above <= top) {
            gridFull.union(i, top);
            gridPercolates.union(i, top);
        }
        else {
            if (isOpen(row-1, col)){
                gridFull.union(i, above);
                gridPercolates.union(i, above);
            }
        }

        if (below >= bot)
            gridPercolates.union(i, bot);
        else {
            if (isOpen(row+1, col)) {
                gridFull.union(i, below);
                gridPercolates.union(i, below);
            }
        }

        if (!(i%n == 0) && isOpen(row, col+1)) {
            gridFull.union(i, right);
            gridPercolates.union(i, right);
        }

        if (!((i-1)%n == 0) && isOpen(row, col-1)) {
            gridFull.union(i, left);
            gridPercolates.union(i, left);
        }

        openSites[i] = true;
        numberOfOpenSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Col/row index out of bounds");

        int i = getIndex(row, col);
        return openSites[i];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException("Col/row index out of bounds");

        int i = getIndex(row, col);
        return isOpen(row, col) && (gridFull.find(i) == gridFull.find(top));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return (gridPercolates.find(bot) == gridPercolates.find(top));
    }

    // test client (optional)
    public static void main(String[] args) {
        // int n = StdIn.readInt();
        // int trials = StdIn.readInt();
        // Percolation p;
        // int r;
        // int c;
        // double[] stats = new double[trials];
        // boolean percolates;
        // boolean full;
        // for(int i = 0; i < trials; i++) {
        //     p = new Percolation(n);
        //     percolates = false;
        //     while(! percolates) {
        //         r = StdRandom.uniform(1, n+1);
        //         c = StdRandom.uniform(1, n+1);
        //         StdOut.println("Open new site ("+r+", "+c+")");
        //         p.open(r, c);
        //         full = p.isFull(r,c);
        //         StdOut.println("Is it full? "+full);
        //         percolates = p.percolates();
        //         StdOut.println("Does it percolate? "+percolates);
        //     }
        //     stats[i] = p.numberOfOpenSites()/((double) n*n);
        // }
        // double mu = StdStats.mean(stats);
        // double sigma = StdStats.stddev(stats);
        // StdOut.println("mean: " + mu);
        // StdOut.println("standard dev: " + sigma);
        //
        // Percolation p = new Percolation(3);
        // StdOut.println("Does it percolate? " + p.percolates());
        // p.open(1,1);
        // StdOut.println("Does it percolate? " + p.percolates());
        // p.open(1,2);
        // StdOut.println("Does it percolate? " + p.percolates());
        // p.open(3,2);
        // StdOut.println("Does it percolate? " + p.percolates());
    }
}
