package org.example.ficha4;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        int port = 12345; // Porta do servidor
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor de chat rodando na porta " + port + "...");

        while (true) {
            new ChatServerWorker(serverSocket.accept()).start(); // Aceita e inicia nova thread para cada cliente
        }
    }
}
