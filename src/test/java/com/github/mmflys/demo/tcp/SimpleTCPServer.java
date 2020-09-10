package com.github.mmflys.demo.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTCPServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3161);
        Socket socket = serverSocket.accept();
        System.out.println(socket.getInetAddress());
    }

}
