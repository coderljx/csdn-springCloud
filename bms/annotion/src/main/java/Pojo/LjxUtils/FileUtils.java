package Pojo.LjxUtils;

import java.io.*;

public class FileUtils {

    private static OutputStream fileOutputStream = null;
    private static FileInputStream inputStream = null;


    public static Boolean fileIsExit(String url) {
        File file = new File(url);
        return file.exists();
    }

    public static byte[] readFile(String url) throws IOException {
        byte[] bytes = null;
        if ( fileIsExit(url) ) {
            inputStream = new FileInputStream(url);
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
        }
        return bytes;
    }




}
