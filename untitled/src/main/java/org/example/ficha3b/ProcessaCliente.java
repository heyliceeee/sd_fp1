package org.example.ficha4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProcessaCliente extends Thread {
    private Socket socket; // Socket associado ao cliente
    private PrintWriter out; // Canal de saída para enviar mensagens ao cliente
    private BufferedReader in; // Canal de entrada para receber mensagens do cliente
    private List<String> mensagens; // Referência à lista de mensagens compartilhada

    // Construtor da classe, recebe o socket do cliente e a lista de mensagens compartilhada
    public ProcessaCliente(Socket socket, List<String> mensagens) {
        this.socket = socket;
        this.mensagens = mensagens;
    }

    // Método run() que será executado quando a thread for iniciada
    public void run() {
        try {
            // Prepara os canais de entrada e saída do cliente
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            String clientIP = socket.getInetAddress().getHostAddress(); // Obtém o IP do cliente

            // Processa as mensagens enviadas pelo cliente
            while ((inputLine = in.readLine()) != null) {
                // Gera o timestamp atual
                String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());

                // Monta a mensagem no formato: [hora] [IP]: mensagem
                String message = timeStamp + " [" + clientIP + "]: " + inputLine;

                // Adiciona a mensagem à lista de mensagens compartilhada
                synchronized (mensagens) {
                    mensagens.add(message);
                }

                // Se o cliente enviar "Bye", a conexão será encerrada
                if (inputLine.equalsIgnoreCase("Bye")) {
                    break;
                }
            }

            // Fecha os recursos ao final da comunicação
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para enviar as mensagens armazenadas para o cliente
    public void enviarMensagens(List<String> mensagens) {
        synchronized (mensagens) {
            for (String msg : mensagens) {
                out.println(msg); // Envia cada mensagem ao cliente
            }
        }
    }
}