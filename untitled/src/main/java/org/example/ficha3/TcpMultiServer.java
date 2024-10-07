package org.example.ficha3;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpMultiServer {
    public static void main(String[] args) throws IOException {
        int port = 2048; // Porta para o servidor
        boolean listening = true; // O servidor ficará rodando enquanto 'true'
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port); // Cria um ServerSocket na porta 2048
            System.out.println("Servidor rodando na porta " + port + "...");

            // Loop para aceitar múltiplos clientes
            while (listening) {
                // Para cada cliente, cria uma nova WorkerThread e a inicia
                new WorkerThread(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            System.err.println("Não foi possível ouvir na porta: " + port);
            System.exit(-1); // Sai com erro
        } finally {
            if (serverSocket != null) serverSocket.close(); // Fecha o ServerSocket
        }
    }
}
