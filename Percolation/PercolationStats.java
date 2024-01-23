import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    // private Percolation[] grids;

    private double[] frac;

    private double nt;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        nt = (double) trials;
        // grids = new Percolation[trials];
        frac = new double[trials];
        /*
        for (int i = 0; i < trials; i++) {
            grids[i] = new Percolation(n);
        }
         */
        for (int j = 0; j < trials; j++) {
            Percolation thisOne = new Percolation(n);
            double numOpened = 0;
            while (!thisOne.percolates()) {
                int r = StdRandom.uniformInt(0, n);
                int c = StdRandom.uniformInt(0, n);
                if (!thisOne.isOpen(r, c)) {
                    thisOne.open(r, c);
                    numOpened++;
                }
            }
            frac[j] = numOpened / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(frac);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(frac);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(nt));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(nt));
    }

    // test client (see below)
    public static void main(String[] args) {

        Stopwatch f = new Stopwatch();
        PercolationStats grid1 = new PercolationStats(200, 100);
        double ftime = f.elapsedTime();
        System.out.println("Testing 100 trials with 200-by-200 grid:");
        System.out.println("mean: " + grid1.mean());
        System.out.println("stddev: " + grid1.stddev());
        System.out.println("confidenceLow: " + grid1.confidenceLow());
        System.out.println("confidenceHigh: " + grid1.confidenceHigh());
        System.out.println("elapsed time: " + ftime);

        Stopwatch s = new Stopwatch();
        PercolationStats grid2 = new PercolationStats(200, 100);
        double stime = s.elapsedTime();
        System.out.println("Testing 100 trials with 200-by-200 grid:");
        System.out.println("mean: " + grid2.mean());
        System.out.println("stddev: " + grid2.stddev());
        System.out.println("confidenceLow: " + grid2.confidenceLow());
        System.out.println("confidenceHigh: " + grid2.confidenceHigh());
        System.out.println("elapsed time: " + stime);


    }
}
