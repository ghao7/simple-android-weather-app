package com.example.guhao.myweather.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by guhao on 6/14/17.
 */

public class FileUtil {
    public static void copyFile(String source, String dest){
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

            input.close();
            output.close();
        }catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
