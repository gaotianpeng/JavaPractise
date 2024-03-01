package com.javapractise.daily.nio.iodemo.filedemo;

import java.io.File;
import java.io.IOException;

public class DirListDemo {
    public static void listFile(String path) throws IOException {
        if (path == null) {
            return;
        }

        File file = new File(path);
        if (file.isFile()) {
            System.out.println("File: " + file.getCanonicalFile());
        } else if (file.isDirectory()) {
            System.out.println("Directory: " + file.getCanonicalFile());

            File[] subFiles = file.listFiles();
            if (subFiles == null) {
                return;
            }
            for (File f: subFiles) {
                listFile(f.getCanonicalPath());
            }
        } else {
            System.out.println("error: " + file.getCanonicalPath());
        }
    }
    public static void main(String[] args) {
        try {
            listFile("C:\\work\\algo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
