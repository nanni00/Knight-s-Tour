package setup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SetUpFiles {
    static boolean setUp = false;

    private static final String dllName = "KnightTour";
    private static String dllPath;

    private static String resDirPath;


    public SetUpFiles() {
        createProgramDirectories();

        String link = "https://github.com/Giovanni-M00/Knight-s-Tour/blob/master/src/main/resources/KnightTour.dll?raw=true";

        dllPath = resDirPath + System.getProperty("file.separator") + dllName + ".dll";


        try {
            new File(dllPath).createNewFile();

            Download.DownloadFileFromLink(link, dllPath);
            setUp = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // SetUpFiles()


    /**
     * Create the directory which will hold the program's files.
     * Default for Windows is 'C:\Program Files\KnightsTour, if it is not found
     * the directory will be located into user's home.
     */
    private void createProgramDirectories() {

        String rootDir = "KnightsTour";
        String resDir = "res";
        String rootDirPath;

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            if (Files.isDirectory(Path.of("C:\\Program Files"))) {
                rootDirPath = "C:\\Program Files\\" + rootDir;
            } else {
                rootDirPath = System.getProperty("user.home") + System.getProperty("file.separator") + rootDir;
            }
        } else {
            rootDirPath = System.getProperty("user.home") + System.getProperty("file.separator") + rootDir;
        }

        resDirPath = rootDirPath + System.getProperty("file.separator") + resDir;

        boolean ret1 = false, ret2 = false;
        if (!Files.exists(Path.of(rootDirPath))) {
            ret1 = new File(rootDirPath).mkdir();
        }

        if (!Files.exists(Path.of(resDirPath))) {
            ret2 = new File(resDirPath).mkdir();
        }

        if (!ret1 || !ret2) {
            rootDirPath = System.getProperty("user.home") + System.getProperty("file.separator") + rootDir;
            resDirPath = rootDirPath + System.getProperty("file.separator") + resDir;

            new File(rootDirPath).mkdir();
            new File(resDirPath).mkdir();
        }
    } // createProgramDirectories()


    public static String getDllPath() {
        if (setUp) {
            return resDirPath + System.getProperty("file.separator") + dllName;
        }
        else
            return null;
    } // getDllPath()

}
