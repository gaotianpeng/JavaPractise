package org.daily.nio.iodemo.nio;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.locks.LockSupport;
public class NioSendClient {
    public NioSendClient() {
    }

    private Charset charset = Charset.forName("UTF-8");

    public void sendFile() {
        try {
            String srcPath = new String("d:\\doris-thirdparty-prebuilt-linux-x86_64.tar.xz");
            File file = new File(srcPath);
            if (!file.exists()) {
                System.out.println(srcPath);
                file = new File(srcPath);
                if (!file.exists()) {
                    System.out.println("file not exist");
                    return;
                }
            }

            FileChannel fileChannel = new FileInputStream(file).getChannel();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
            socketChannel.socket().connect(
                    new InetSocketAddress("127.0.0.1", 8888));
            socketChannel.configureBlocking(false);
            System.out.println("success connect to server");

            while (!socketChannel.finishConnect()) {

            }
            ByteBuffer fileNameByteBuffer = charset.encode(file.getName());

            ByteBuffer buffer = ByteBuffer.allocate(10240);
            int fileNameLen = fileNameByteBuffer.remaining();
            buffer.clear();
            buffer.putInt(fileNameLen);
            //切换到读模式
            buffer.flip();
            socketChannel.write(buffer);
            System.out.println("Client file name length sended:" + fileNameLen);

            // 发送文件名称
            socketChannel.write(fileNameByteBuffer);
            System.out.println("Client file name sended:" + file.getName());
            //发送文件长度
            //清空
            buffer.clear();
            buffer.putInt((int) file.length());
            //切换到读模式
            buffer.flip();
            //写入文件长度
            socketChannel.write(buffer);
            System.out.println("Client file length sended：" + file.length());

            //发送文件内容
            System.out.println("start transfer file");
            int length = 0;
            long offset = 0;
            buffer.clear();
            while ((length = fileChannel.read(buffer)) > 0) {
                buffer.flip();
                socketChannel.write(buffer);

                offset += length;
                System.out.println("| " + (100 * offset / file.length()) + "% |");
                buffer.clear();
            }

            LockSupport.parkNanos(60 * 1000L * 1000L * 1000L);

            if (length == -1) {
                fileChannel.close();
                socketChannel.shutdownOutput();
                socketChannel.close();
            }
            System.out.println("file transfer success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        NioSendClient client = new NioSendClient();
        client.sendFile();
    }
}
