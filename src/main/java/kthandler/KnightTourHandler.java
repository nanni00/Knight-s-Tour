package kthandler;

import java.io.File;
import java.io.FileNotFoundException;

public class KnightTourHandler {

    private native boolean KnightTour_JavaCaller(int m, int n, int startRow, int startCol, int[] path);

    String relativePath = "src\\main\\resources\\";
    String dllName = "KnightTour.dll";
    String path;

    private boolean dllLoaded = false;


    public KnightTourHandler() {

    } // KnightTourHandler()


    private void checkExistingDllPath() throws FileNotFoundException {
        File file = new File(relativePath + dllName);
        if (file.exists()) {
            path = file.getAbsolutePath();
        } else {
            throw new FileNotFoundException("Impossible to find " + dllName);
        }
    } // checkExistingDllPath()


    /**
     * Solves the Knight's Tour
     * @param r the rows number
     * @param c the columns number
     * @param sr the starting row
     * @param sc the starting column
     * @param answer an array which will store eventually a path
     * @return true if any full path was found, false otherwise
     * @throws FileNotFoundException if .dll file 'KnightTour.dll' is not found
     * @throws UnsatisfiedLinkError if has been impossible to load the .dll 'KnightTour.dll' file
     */
    public boolean solveTour(int r, int c, int sr, int sc, int[] answer) throws FileNotFoundException, UnsatisfiedLinkError {

        if (!dllLoaded) {
            checkExistingDllPath();
            System.load(path);

            dllLoaded = true;
        }

        return KnightTour_JavaCaller(r, c, sr, sc, answer);
    } // solveTour()

}
