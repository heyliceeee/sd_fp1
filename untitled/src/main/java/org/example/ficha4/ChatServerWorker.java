package org.example.ficha4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// Classe WorkerThread para gerenciar cada cliente
public class ChatServerWorker extends Thread {
    private Socket socket;
    private PrintWriter out;
    private static Set<PrintWriter> clientWriters = new HashSet<>(); // Armazena todos os clientes conectados

    public ChatServerWorker(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // Prepara os fluxos de entrada e saída
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            synchronized (clientWriters) {
                clientWriters.add(out); // Adiciona o cliente à lista
            }

            String inputLine;
            String clientIP = socket.getInetAddress().getHostAddress(); // Obtém o IP do cliente

            while ((inputLine = in.readLine()) != null) {
                // Pega a hora atual
                String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());

                // Prepara a mensagem com data, hora e IP do cliente
                String message = timeStamp + " [" + clientIP + "]: " + inputLine;

                // Envia a mensagem para todos os clientes conectados
                synchronized (clientWriters) {
                    for (PrintWriter writer : clientWriters) {
                        writer.println(message);
                    }
                }

                // Encerra a comunicação se o cliente enviar "Bye"
                if (inputLine.equalsIgnoreCase("Bye")) {
                    break;
                }
            }

            // Remove o cliente quando ele desconectar
            synchronized (clientWriters) {
                clientWriters.remove(out);
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}