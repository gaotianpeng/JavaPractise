package com.javapractise.daily.nio.iodemo.oio.nio;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class BlockSenderClient extends Socket {
    private Socket client;
    private FileInputStream intputStream;
    private DataOutputStream outputStream;

    public BlockSenderClient() throws IOException {
        super("127.0.0.1", 8888);
        this.client = this;
    }

    public void sendFile() {
        try {
            String srcPath = new String("C:\\work\\algo\\lintcode\\MinimumSpanningTree.java");
            System.out.println("source path " + srcPath);

            File file = new File(srcPath);
            if (file.exists()) {
                intputStream = new FileInputStream(file);
                outputStream = new DataOutputStream(client.getOutputStream());

                outputStream.writeLong(file.length());
                outputStream.flush();
                outputStream.writeUTF("copy_" + file.getName());
                outputStream.flush();

                System.out.println("start transfer file");
                byte[] bytes = new byte[1024];
                int len = 0;
                long progress = 0;
                while ((len = intputStream.read(bytes, 0, bytes.length)) != -1) {
                    outputStream.write(bytes, 0, len);
                    outputStream.flush();
                    progress += len;
                    System.out.println("| " + (100 * progress / file.length()) + "% |");
                }
                System.out.println("file transfer success");
            } else {
                System.out.println("failed to transfer file, file not exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                intputStream.close();
                this.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) {
        try {
            BlockSenderClient client = new BlockSenderClient();
            client.sendFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
