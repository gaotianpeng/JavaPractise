package com.javapractise.daily.nio.iodemo.oio.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NioReceiveServer {
    private static final String RECEIVE_PATH = "output";

    private Charset charset = Charset.forName("UTF-8");

    static class Session {
        /*
            1 file name length
            2 file name
            3 file length
            4 file content
         */
        int step = 1;

        String fileName = null;
        long fileLen;
        int fileNameLen;
        long startTime;

        InetSocketAddress remtoteAddress;
        FileChannel fileChannel;
        long receiveLen;

        public boolean isFinished() {
            return receiveLen >= fileLen;
        }
    }

    private ByteBuffer buffer = ByteBuffer.allocate(10240);

    Map<SelectableChannel, Session> clientMap = new HashMap<SelectableChannel, Session>();

    public void startServer() {
        try {
            Selector selector = Selector.open();

            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverChannel.socket();

            serverChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(8888);
            serverSocket.bind(address);

            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("serverChannel is listening...");

            while (selector.select() > 0) {
                if (null == selector.selectedKeys()) {
                    continue;
                }

                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (null == key) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = server.accept();
                        if (socketChannel == null) {
                            continue;
                        }
                        socketChannel.configureBlocking(false);
                        socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);

                        SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                        Session session = new Session();
                        session.remtoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                        clientMap.put(socketChannel, session);
                        System.out.println(socketChannel.getRemoteAddress() + "connected success...");
                    } else if (key.isReadable()) {
                        handleData(key);
                    }
                    it.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleData(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num = 0;
        Session session = clientMap.get(key.channel());

        buffer.clear();
        while ((num = socketChannel.read(buffer)) > 0) {
            System.out.println("received bytes: " + num);
            buffer.flip();
            process(session, buffer);
            buffer.clear();
        }
    }

    private void process(Session session, ByteBuffer buffer) {
        while (len(buffer) > 0) {
            if (1 == session.step) {
                int fileNameLengthByteLen = len(buffer);
                System.out.println("读取文件名称长度之前，可读取的字节数 = " + fileNameLengthByteLen);
                System.out.println("读取文件名称长度之前，buffer.remaining() = " + buffer.remaining());
                System.out.println("读取文件名称长度之前，buffer.capacity() = " + buffer.capacity());
                System.out.println("读取文件名称长度之前，buffer.limit() = " + buffer.limit());
                System.out.println("读取文件名称长度之前，buffer.position() = " + buffer.position());

                if (len(buffer) < 4) {
                    System.out.println("出现半包问题，需要更加复杂的拆包方案");
                    throw new RuntimeException("出现半包问题，需要更加复杂的拆包方案");
                }

                //获取文件名称长度
                session.fileNameLen = buffer.getInt();

                System.out.println("读取文件名称长度之后，buffer.remaining() = " + buffer.remaining());
                System.out.println("读取文件名称长度 = " + session.fileNameLen);

                session.step = 2;
            } else if (2 == session.step) {
                System.out.println("step 2");

                if (len(buffer) < session.fileNameLen) {
                    System.out.println("出现半包问题，需要更加复杂的拆包方案");
                    throw new RuntimeException("出现半包问题，需要更加复杂的拆包方案");
                }

                byte[] fileNameBytes = new byte[session.fileNameLen];
                //读取文件名称
                buffer.get(fileNameBytes);

                // 文件名
                String fileName = new String(fileNameBytes, charset);
                System.out.println("读取文件名称 = " + fileName);

                File directory = new File(RECEIVE_PATH);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                System.out.println("NIO  传输目标dir：" + directory);

                session.fileName = fileName;
                String fullName = directory.getAbsolutePath() + File.separatorChar + fileName;
                System.out.println("NIO 传输目标文件：" + fullName);

                File file = new File(fullName.trim());

                try {
                    if (!file.exists()) {
                        file.createNewFile();

                    }
                    FileChannel fileChannel = new FileOutputStream(file).getChannel();
                    session.fileChannel = fileChannel;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                session.step = 3;
            } else if (3 == session.step) {
                System.out.println("step 3");

                //客户端发送过来的，首先处理文件内容长度

                if (len(buffer) < 4) {
                    System.out.println("出现半包问题，需要更加复杂的拆包方案");
                    throw new RuntimeException("出现半包问题，需要更加复制的拆包方案");
                }
                //获取文件内容长度
                session.fileLen = buffer.getInt();

                System.out.println("读取文件内容长度之后，buffer.remaining() = " + buffer.remaining());
                System.out.println("读取文件内容长度 = " + session.fileLen);

                session.step = 4;
                session.startTime = System.currentTimeMillis();
            } else if (4 == session.step) {
                System.out.println("step 4");
                //客户端发送过来的，最后是文件内容

                session.receiveLen += len(buffer);

                // 写入文件
                try {
                    session.fileChannel.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (session.isFinished()) {
                    finished(session);
                }
            }
        }
    }

    private static int len(ByteBuffer buffer) {
        System.out.println(" >>> buffer left:" + buffer.remaining());
        return buffer.remaining();
    }

    private void finished(Session session) {
        try {
            session.fileChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("上传完毕");
        System.out.println("文件接收成功, File Name：" + session.fileName);
        System.out.println(" Size：" + getFormatFileSize(session.fileLen));
        long endTime = System.currentTimeMillis();
        System.out.println("NIO IO 传输毫秒数：" + (endTime - session.startTime));
    }

    public static String getFormatFileSize(long length) {
        DecimalFormat fileSizeFormater = FormatUtil.decimalFormat(1);
        double size = ((double) length) / (1 << 30);
        if (size >= 1) {
            return fileSizeFormater.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if (size >= 1) {
            return fileSizeFormater.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if (size >= 1) {
            return fileSizeFormater.format(size) + "KB";
        }
        return length + "B";
    }

    public static class FormatUtil  {
        public static DecimalFormat decimalFormat(int fractions) {

            DecimalFormat df = new DecimalFormat("#0.0");
            df.setRoundingMode(RoundingMode.HALF_UP);
            df.setMinimumFractionDigits(fractions);
            df.setMaximumFractionDigits(fractions);
            return df;
        }
    }

    public static void main(String[] args) {
        NioReceiveServer server = new NioReceiveServer();
        server.startServer();
    }
}
