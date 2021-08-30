package kthandler;

public class KnightTourHandler {
    public native boolean KnightTour_JavaCaller(int m, int n, int startRow, int startCol, int[] path);

    static {
        System.load("C:\\Users\\giova\\IdeaProjects\\Knight-s-Tour\\src\\main\\c\\KnightTour.dll");
        //System.loadLibrary("KnightTour");
    }


    public KnightTourHandler() {

    }


    private void printBoard(int m, int n, int[] path) {
        for (int r = 0; r < m; ++r) {
            for (int c = 0; c < n; ++c) {
                System.out.print(path[r * n + c] + "\t");
            }
            System.out.println();
        }
    }


    public void allPoints(int m, int n) {
        int[] path = new int[m * n];

        for (int r = 0; r < m; ++r) {
            for (int c = 0; c < n; ++c) {
                if (KnightTour_JavaCaller(m, n, r, c, path)) {
                    System.out.println("Starting Position: (" + r + ", " + c + ") ");
                    printBoard(m, n, path);
                    System.out.println();
                }
            }
        }
    }


    public int[] solveTour(int m, int n, int startRow, int startColumn) {
        int[] path = new int[m * n];

        KnightTour_JavaCaller(m, n, startRow, startColumn, path);

        return path;
    }
}
