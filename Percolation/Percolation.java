import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int gridLength;

    private int gridCount;

    private boolean[][] openOnes;

    private WeightedQuickUnionUF qf;

    private final int TOP = 0;

    private int BOTTOM;

    private int numOpened;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        gridLength = n;
        gridCount = n * n;

        BOTTOM = gridCount + 1;
        openOnes = new boolean[gridLength][gridLength];

        qf = new WeightedQuickUnionUF(gridCount + 2);
        numOpened = 0;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (!isOpen(row, col)) {
            openOnes[row][col] = true;
            numOpened++;
        }
        if (row == 0) {
            qf.union(quickIndex(row, col), TOP);
        }

        if (row == gridLength - 1) {
            qf.union(quickIndex(row, col), BOTTOM);
        }

        if (row > 0 && isOpen(row - 1, col)) {
            qf.union(quickIndex(row, col), quickIndex(row - 1, col));
        }
        if (row < gridLength - 1 && isOpen(row + 1, col)) {
            qf.union(quickIndex(row, col), quickIndex(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            qf.union(quickIndex(row, col), quickIndex(row, col - 1));
        }
        if (col < gridLength - 1 && isOpen(row, col + 1)) {
            qf.union(quickIndex(row, col), quickIndex(row, col + 1));
        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // return !isFull(row, col);
        return openOnes[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return qf.find(quickIndex(row, col)) == qf.find(TOP);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.find(TOP) == qf.find(BOTTOM);
    }

    private int quickIndex(int row, int col) {
        return (row * gridLength) + (col + 1);
    }

    // unit testing (required)
    public static void main(String[] args) {
        int gL = 10;
        Percolation grid = new Percolation(gL);
        boolean allBlocked = true;
        for (int i = 0; i < gL; i++) {
            for (int j = 0; j < gL; j++) {
                if (grid.isOpen(i, j)) {
                    allBlocked = false;
                }
            }
        }
        System.out.println("All sites initially blocked: " + allBlocked);

        grid.open(0, 0);

        System.out.println("Origin is now open: " + grid.isOpen(0, 0));

        int r1 = grid.numberOfOpenSites();

        grid.open(0, 0);

        System.out.println("Origin is still open: " + grid.isOpen(0, 0));

        int r2 = grid.numberOfOpenSites();

        System.out.println("^^Yes, because r1 == r2 is " + (r1 == r2));

        System.out.println("The origin is full: " + grid.isFull(0, 0));

        for (int it1 = 0; it1 < gL; it1++) {
            grid.open(it1, 0);
        }

        System.out.println("2nd block is open: " + grid.isOpen(1, 0));

        System.out.println("2nd block is full: " + grid.isFull(1, 0));

        System.out.println("This version of grid percolates: " + grid.percolates());


        Percolation grid2 = new Percolation(gL);

        for (int it1 = 0; it1 < gL - 1; it1++) {
            grid2.open(it1, 0);
        }


        System.out.println("This version of grid DOES NOT percolate: " +
                                   (!grid2.percolates()));

        System.out.println("isFull r8 c1: " + grid2.isFull(8, 1));


        grid2.open(8, 1);

        System.out.println("isFull r8 c0: " + grid2.isFull(8, 0));

        grid2.open(9, 1);

        System.out.println("isFull r8 c0: " + grid2.isFull(8, 0));


        System.out.println("Ok");

        System.out.println("isFull r8 c0: " + grid2.isFull(8, 0));

        System.out.println("This version of grid DOES NOT percolate: " +
                                   (!grid2.percolates()));

        System.out.println("This version of grid percolates: " + grid2.percolates());

        Percolation grid3 = new Percolation(6);

        grid3.open(0, 0);
        grid3.open(1, 0);
        grid3.open(2, 0);
        grid3.open(3, 0);

        grid3.open(0, 1);
        grid3.open(3, 1);
        grid3.open(3, 2);
        grid3.open(3, 3);

        grid3.open(2, 2);
        grid3.open(2, 3);
        grid3.open(1, 2);

        System.out.println("grid3 percolates: " + grid3.percolates());
        System.out.println("grid3 r2 c3 isFull: " + grid3.isFull(2, 3));
        System.out.println("grid3 r1 c2 isFull: " + grid3.isFull(1, 2));
        System.out.println("grid3 r2 c2 isFull: " + grid3.isFull(2, 2));

        grid3.open(0, 2);

        System.out.println("new version of grid3 percolates: " + grid3.percolates());
        System.out.println("new version of grid3 r2 c3 isFull: " + grid3.isFull(2, 3));
        System.out.println("new version of grid3 r1 c2 isFull: " + grid3.isFull(1, 2));
        System.out.println("new version of grid3 r2 c2 isFull: " + grid3.isFull(2, 2));
        System.out.println("new version of grid3 r0 c2 isFull: " + grid3.isFull(0, 2));


        grid3.open(1, 5);
        System.out.println("new version of grid3 r1 c5 isFull: " + grid3.isFull(1, 5));

        grid3.open(0, 5);
        System.out.println("new version of grid3 r1 c5 isFull: " + grid3.isFull(1, 5));

        Stopwatch stopwatch = new Stopwatch();


    }


}
