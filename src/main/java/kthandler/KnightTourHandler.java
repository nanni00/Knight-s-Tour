package kthandler;

import setup.SetUpFiles;


public class KnightTourHandler {

    private native boolean KnightTour_JavaCaller(int m, int n, int startRow, int startCol, int[] path);


    static {
        assert SetUpFiles.getDllPath() != null;
        String dllPath = SetUpFiles.getDllPath() + ".dll";

        try {
            System.load(dllPath);
        } catch (UnsatisfiedLinkError ule) {
            System.out.println("System.load() failed.");
            System.out.println(ule.getMessage());


            System.out.println("Impossible to load the library from " + dllPath);
            System.out.println("Terminating program...");
            System.exit(1);
        }

    }


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
