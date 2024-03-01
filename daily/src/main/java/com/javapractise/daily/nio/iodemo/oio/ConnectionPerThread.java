package com.javapractise.daily.nio.iodemo.oio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionPerThread implements Runnable {
    @Override
    public void run() {
        try {
            ServerSocket serSocket = new ServerSocket(8888);
            System.out.println("Server is up");

            while (!Thread.interrupted()) {
                // 每接收一个客户端的socket连接，创建一个线程，进行阻塞式的读写
                Socket socket = serSocket.accept();
                Handler handler = new Handler(socket);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class Handler implements Runnable {
        final Socket socket;

        Handler(Socket s) {
            socket = s;
            System.out.println(String.format("连接的两个端口: %s %s", socket.getPort(), socket.getPort()));
        }
        @Override
        public void run() {
            while (true) {
                try {
                    byte[] input = new byte[10240];
                    socket.getInputStream().read(input);
                    System.out.println("收到: " + new String(input));
                    byte[] output = input;
                    socket.getOutputStream().write(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("hello world");
        new ConnectionPerThread().run();
    }
}
