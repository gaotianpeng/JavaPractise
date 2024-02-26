package org.daily.nio.iodemo.nio;

import sun.misc.IOUtils;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockReceiverServer extends ServerSocket {
    private static final int SERVER_PORT = 8888;
    private static final String RECEIVE_PATH = "output";
    public BlockReceiverServer() throws IOException {
        super(SERVER_PORT);
    }

    public void startServer() throws IOException {
        while (true) {
            System.out.println("server listen at " + SERVER_PORT);
            Socket socket = this.accept();
            new Thread(new Task(socket)).start();
        }
    }


    class Task implements Runnable {
        private Socket socket;
        private DataInputStream inputStream;
        private FileOutputStream outputStream;

        Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                inputStream = new DataInputStream(socket.getInputStream());
                long fileLen = inputStream.readLong();
                String fileName = inputStream.readUTF();
                File directory = new File(RECEIVE_PATH);
                if (!directory.exists()) {
                    directory.mkdir();
                }

                File file = new File(directory.getAbsolutePath() + File.separator + fileName);
                outputStream = new FileOutputStream(file);
                long startT = System.currentTimeMillis();
                System.out.println("block IO start transfer");
                byte [] bytes  = new byte[1024];
                int len = 0;
                while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {
                    outputStream.write(bytes, 0, len);
                    outputStream.flush();
                }

                System.out.println("receive file success: file " + fileName);
                long endT = System.currentTimeMillis();

            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    outputStream.close();
                    inputStream.close();
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public static void main(String[] args) {
        try {
            BlockReceiverServer server = new BlockReceiverServer();
            server.startServer();
            Thread.sleep(Integer.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
