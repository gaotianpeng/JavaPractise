package com.javapractise.daily.nio.iodemo.filedemo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNIOReadDemo {
    public static final int CAPACITY = 1024;

    public static void nioReadFile(String fileName) {
        try {
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            FileChannel inChannel = file.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(CAPACITY);

            int len = -1;
            while ((len = inChannel.read(buf)) != -1) {
                buf.flip();
                byte[] bytes = buf.array();
                System.out.print(new String(bytes, 0, len));
            }

            inChannel.close();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        nioReadFile("C:\\work\\algo\\lintcode\\MinimumSpanningTree.java");
    }
}
