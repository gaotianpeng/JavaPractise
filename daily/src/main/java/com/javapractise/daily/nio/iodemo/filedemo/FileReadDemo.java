package com.javapractise.daily.nio.iodemo.filedemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileReadDemo {
    public static void readFile(String fileName) {
        File file = new File(fileName);

        try {
            Reader reader = new FileReader(file);
            BufferedReader buffered = new BufferedReader(reader);
            String data = null;
            while ((data = buffered.readLine()) != null) {
                System.out.println(data);
            }
            buffered.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readFile("C:\\work\\algo\\lintcode\\MinimumSpanningTree.java");
    }
}