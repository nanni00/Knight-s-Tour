import chessboard.BoardFrame;

public class Test {

    public native boolean KnightTour(int m, int n, int startRow, int startCol, int[] path);

    static {
        System.load("C:\\Users\\giova\\IdeaProjects\\Knight-s-Tour\\src\\main\\c\\KnightTour.dll");
        //System.loadLibrary("KnightTour");
    }

    private static void printBoard(int m, int n, int[] path) {
        for (int r = 0; r < m; ++r) {
            for (int c = 0; c < n; ++c) {
                System.out.print(path[r * n + c] + "\t");
            }
            System.out.println();
        }
    }


    private static void allPoints(int m, int n) {
        Test t = new Test();
        boolean b;
        int[] path = new int[m * n];

        for (int r = 0; r < m; ++r) {
            for (int c = 0; c < n; ++c) {
                if (t.KnightTour(m, n, r, c, path)) {
                    System.out.println("Starting Position: (" + r + ", " + c + ") ");
                    printBoard(m, n, path);
                    System.out.println();
                }
            }
        }
    }


    public static void main(String[] args) {

        int m = 50;
        int n = 50;
        int starRow = 0;
        int startColumn = 0;
        int[] path = new int[m * n];

        long start = System.nanoTime();
        if (new Test().KnightTour(m, n, starRow, startColumn, path)) {
            long end = System.nanoTime();

            System.out.println("Total finding path time: " + ((end - start) / 1000000.0));

            //printBoard(m, n, path);
            //BoardFrame boardFrame = new BoardFrame(m, n, path);
            //boardFrame.display();
        }
    }
}
