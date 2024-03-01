package com.javapractise.daily.nio.iodemo.oio;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Socket socket = new Socket("localhost", 8888);
            System.out.println("连接的两个端口号:" + socket.getPort() + ", " + socket.getLocalPort());
            if (socket != null) {
                System.out.println("客户端连接服务器成功");
            }

            while (true) {
                System.out.println("Input strint to send: ");
                String str = sc.next();
                socket.getOutputStream().write(str.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
