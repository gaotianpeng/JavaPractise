package com.javapractise.daily.nio.iodemo.filedemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopyDemo {
    public static void blockCopyFile(String srcPath, String dstPath) {
        File srcFile = new File(srcPath);
        File dstFile = new File(dstPath);
        InputStream inStream = null;
        OutputStream outStream = null;

        try {
            if (!dstFile.exists()) {
                dstFile.createNewFile();
            }

            inStream = new FileInputStream(srcFile);
            outStream = new FileOutputStream(dstFile);

            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inStream.read(buf)) != -1) {
                outStream.write(buf, 0, bytesRead);
            }
            outStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inStream.close();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        blockCopyFile("C:\\work\\algo\\lintcode\\MinimumSpanningTree.java",
                        "C:\\work\\algo\\lintcode\\MinimumSpanningTreeBack.java");
    }
}
