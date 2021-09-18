package kthandler;

import setup.FilesManager;


public class KnightTourHandler {

    private native boolean KnightTour_JavaCaller(int m, int n, int startRow, int startCol, int[] path);





    public KnightTourHandler() {

    } // KnightTourHandler()


    /**
     * Solves the Knight's Tour
     * @param r the rows number
     * @param c the columns number
     * @param sr the starting row
     * @param sc the starting column
     * @param answer an array which will store eventually a path
     * @return true if any full path was found, false otherwise
     */
    public boolean solveTour(int r, int c, int sr, int sc, int[] answer) {
        return KnightTour_JavaCaller(r, c, sr, sc, answer);
    } // solveTour()

}
