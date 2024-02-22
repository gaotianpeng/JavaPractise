package org.daily.nio.iodemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNIODemoCopy {
    public static void nioCopyFile(String srcPath, String dstPath) {
        File srcFile = new File(srcPath);
        File dstFile = new File(dstPath);

        try {
            if (!dstFile.exists()) {
                dstFile.createNewFile();
            }

            FileInputStream inStream = null;
            FileOutputStream outStream = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;

            inStream = new FileInputStream(srcFile);
            outStream = new FileOutputStream(dstFile);
            inChannel = inStream.getChannel();
            outChannel = outStream.getChannel();

            int len = 0;
            ByteBuffer buf = ByteBuffer.allocateDirect(1024);
            while ((len = inChannel.read(buf)) != -1) {
                buf.flip();
                int outLen = 0;
                while ((outLen = outChannel.write(buf)) != 0) {
                    System.out.println("write bytes: " + outLen);
                }
                buf.clear();
            }
            outChannel.force(true);

            outChannel.close();
            inChannel.close();
            outStream.close();
            outChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        nioCopyFile("C:\\work\\algo\\lintcode\\MinimumSpanningTree.java",
                "C:\\work\\algo\\lintcode\\MinimumSpanningTreeBack.java");
    }
}
