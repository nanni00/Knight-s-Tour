package setup;

import download.Download;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class FilesManager {
    static boolean setUp = false;

    static final String fileSeparator = System.getProperty("file.separator");

    private static final String libKnightTour = "libknighttour"; // the .dll file name
    private static final String savedName = "KnightTourLibsPath";  // a file where is saved the lib dir path

    private static final String txt = ".txt";
    private static final String dll = ".dll";
    private static final String so = ".so";

    private static String resDirPath;
    private static String libDirPath;
    private static String rootDirPath;


    public FilesManager() {
        String libtype;

        String os = System.getProperty("os.name");
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            libtype = dll;
        } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            libtype = so;
        } else {
            libtype = null;
            System.out.println("OS not supported: " + os);
            System.exit(1);
        }

        createProgramDirectories();

        // standard path
        String libPath = libDirPath + fileSeparator + libKnightTour + libtype;
        String savedPath = resDirPath + fileSeparator + savedName + txt;
        int libPathLength = libPath.length();

        /*
         * Try to read an existing path from the saving file;
         * if the path to the dll is correct, System.load() doesn't throw any exception,
         * then it is not necessary download the file from the URL below,
         * otherwise create a new file to save the path
         */
        try {
            if (Files.exists(Path.of(savedPath))) {
                /*
                 * if the file already exists read its contents and try to get the library
                 */
                DataInputStream in = new DataInputStream(new FileInputStream(savedPath));
                char[] buffer = new char[1024];
                libPathLength = in.readInt();
                for (int i = 0; i < libPathLength; ++i) {
                    buffer[i] = (char) in.readByte();
                }
                libPath = String.valueOf(buffer, 0, libPathLength);

            } else {
                /*
                 * if the saving file doesn't exist yet a new one is created and
                 * the dll path is stored into.
                 * Assuming that the dll doesn't exist yet also that file is created
                 */
                Files.createFile(Path.of(savedPath));
                Files.createFile(Path.of(libPath));

                DataOutputStream out = new DataOutputStream(new FileOutputStream(savedPath));

                out.writeInt(libPathLength);
                out.writeBytes(libPath);
                out.flush();
                out.close();

                String link = "https://github.com/nanni00/Knight-s-Tour/blob/master/out/production/resources/" + libKnightTour + libtype + "?raw=true";
                Download.DownloadFileFromLink(link, libPath);
            }

            System.load(libPath);
            setUp = true;

        } catch (IOException e) {
            e.printStackTrace();
            setUp = false;
        } catch (UnsatisfiedLinkError ule) {
            System.out.println("Impossible load file from " + libPath);
            System.out.println(ule.getMessage());
            setUp = false;
        }

    } // SetUpFiles()


    public String getRootDirPath() {
        return rootDirPath;
    } // getRootDirPath()


    /**
     * Create the directory which will hold the program's files.
     * Default for Windows is 'C:\Program Files\KnightsTour, if it is not found
     * the directory will be located into user's home.
     */
    private void createProgramDirectories() {
        String resDir = "res";
        String libDir = "Libs";
        String rootDir = "KnightsTour";

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            if (Files.isDirectory(Path.of("C:\\Program Files"))) {
                rootDirPath = "C:\\Program Files\\" + rootDir;
            } else {
                rootDirPath = System.getProperty("user.home") + System.getProperty("file.separator") + rootDir;
            }
        } else {
            rootDirPath = System.getProperty("user.home") + System.getProperty("file.separator") + rootDir;
        }

        libDirPath = libDirPath + System.getProperty("file.separator") + libDir;
        resDirPath = rootDirPath + System.getProperty("file.separator") + resDir;

        boolean ret1 = false, ret2 = false, ret3 = false;

        if (!Files.exists(Path.of(rootDirPath))) {
            ret1 = new File(rootDirPath).mkdir();
        }

        if (!Files.exists(Path.of(resDirPath))) {
            ret2 = new File(resDirPath).mkdir();
        }

        if (!Files.exists(Path.of(libDirPath))) {
            ret3 = new File(libDirPath).mkdir();
        }

        if (!ret1 || !ret2 || !ret3) {
            rootDirPath = System.getProperty("user.home") + System.getProperty("file.separator") + rootDir;
            resDirPath = rootDirPath + System.getProperty("file.separator") + resDir;
            libDirPath = rootDirPath + System.getProperty("file.separator") + libDir;

            new File(rootDirPath).mkdir();
            new File(resDirPath).mkdir();
            new File(libDirPath).mkdir();
        }
    } // createProgramDirectories()

    /**
     * Write a path on file called "longPath.txt" located in the root program's directory.
     * @param r the number of rows
     * @param c the number of columns
     * @param path the path solved
     */
    public void writePathOnFile(int r, int c, int[] path) {
        String fileName = "longPath";
        String filePath = rootDirPath + fileSeparator + fileName + txt;
        try {
            int k = 1;
            while (Files.exists(Path.of(filePath))) {
                filePath = rootDirPath + fileSeparator + fileName + "(" + k + ")" + txt;
                ++k;
            }

            FileWriter out = new FileWriter(filePath);

            for (int i = 0; i < r; ++i) {
                for (int j = 0; j < c; ++j) {
                    out.write(String.valueOf(path[i * c + j]));
                    out.write("\t");
                }
                out.write("\n");
            }
            out.flush();
            out.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    } // writePathOnFile()

} // class FilesManager
