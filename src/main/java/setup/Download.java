package setup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Download {

    public Download() {

    } // Download()


    public static void DownloadFileFromLink(String link, String dstFile) throws IOException {
        URL url = new URL(link);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        InputStream inputStream = http.getInputStream();

        Files.copy(inputStream, Paths.get(dstFile), StandardCopyOption.REPLACE_EXISTING);

    } // DownloadFileFromLink()

}
