package org.example.ficha3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// Classe que extende Thread para gerenciar cada cliente de forma independente
public class WorkerThread extends Thread {
    private Socket socket = null; // Socket do cliente

    public WorkerThread(Socket socket) {
        super("WorkerThread"); // Nome da thread
        this.socket = socket; // Associa o socket do cliente à thread
    }

    public void run() {
        try {
            // Configura os fluxos de entrada e saída de dados para o cliente
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;

            // Processa as mensagens recebidas do cliente e as devolve (Echo)
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine); // Retorna a mensagem ao cliente (echo)
                if (inputLine.equals("Bye")) // Encerra se o cliente enviar "Bye"
                    break;
            }

            // Fecha os recursos ao final
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace(); // Mostra erros de entrada/saída
        }
    }
}