package org.yk.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.junit.Test;

public class NIOTest {
    
    @Test
    public void nioServer() throws IOException{
        ByteBuffer echoBuffer = ByteBuffer.allocate(8);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8080));
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("开始监听...");
        while(true){
            int num = selector.select();
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey sKey = (SelectionKey)it.next();
                SocketChannel channel = null;
                if(sKey.isAcceptable()){
                    ServerSocketChannel sc = (ServerSocketChannel) sKey.channel();
                    channel = sc.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                    it.remove();
                } else if(sKey.isReadable()){
                    channel = (SocketChannel) sKey.channel();
                    while(true){
                        echoBuffer.clear();
                        try {
                            int r = channel.read(echoBuffer);
                            if(r <= 0){
                                channel.close();
                                System.out.println("接收完毕,断开连接");
                                break;
                            }
                            System.out.println("##"+ r + " " + new String(echoBuffer.array(),0,echoBuffer.position()));
                            echoBuffer.flip();
                        } catch (Exception e) {
                            e.printStackTrace();
                            channel.close();
                            break;
                        }
                    }
                    it.remove();
                }else{
                    sKey.channel().close();
                }
            }
        }
    }
    
    @Test
    public void nioClient() throws IOException{
        ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("localhost",8080));
        try{
            if(channel.finishConnect()){
                echoBuffer.put("123456789abcdefghijklmnopq".getBytes());
                echoBuffer.flip();
                System.out.println("##" + new String(echoBuffer.array()));
                channel.write(echoBuffer);
                System.out.println("写入完毕");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            channel.close();
        }
    }
    
    @Test
    public void bioClient() {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String str = Thread.currentThread().getName() + "...........sadsadasJava";
            os.write(str.getBytes());
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            is.close();
            os.close();
            socket.close();
            System.out.println(Thread.currentThread().getName() + " 写入完毕 " + new String(bos.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
