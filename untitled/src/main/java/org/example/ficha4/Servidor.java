package org.example.ficha4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    // Lista compartilhada que armazena todas as mensagens enviadas por todos os clientes
    private static List<String> mensagens = new ArrayList<>();

    // Lista de clientes conectados (cada cliente é representado por uma instância de ProcessaCliente)
    private static List<ProcessaCliente> clientes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int port = 12345; // Porta do servidor
        ServerSocket serverSocket = new ServerSocket(port); // Cria um ServerSocket na porta especificada
        System.out.println("Servidor rodando na porta " + port + "...");

        // Thread responsável por enviar mensagens para todos os clientes a cada 5 segundos
        new Thread(() -> {
            while (true) {
                try {
                    // Aguarda 5 segundos antes de enviar as mensagens
                    Thread.sleep(5000);

                    // Sincroniza o acesso à lista de clientes para evitar problemas de concorrência
                    synchronized (clientes) {
                        // Envia todas as mensagens para cada cliente conectado
                        for (ProcessaCliente cliente : clientes) {
                            cliente.enviarMensagens(mensagens);
                        }
                    }

                    // Limpa as mensagens depois de enviá-las para todos os clientes
                    synchronized (mensagens) {
                        mensagens.clear();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Loop infinito que aguarda novas conexões de clientes
        while (true) {
            Socket socket = serverSocket.accept(); // Aceita uma nova conexão de cliente
            ProcessaCliente cliente = new ProcessaCliente(socket, mensagens); // Cria uma nova thread para processar o cliente
            synchronized (clientes) {
                clientes.add(cliente); // Adiciona o novo cliente à lista de clientes conectados
            }
            cliente.start(); // Inicia a thread do cliente
        }
    }
}