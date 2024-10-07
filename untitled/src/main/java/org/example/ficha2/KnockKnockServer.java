package org.example.ficha2;

import java.net.*;
import java.io.*;

public class KnockKnockServer {
    public static void main(String[] args) throws IOException {
        // Declaração do socket do servidor
        ServerSocket serverSocket = null;

        try {
            // Cria um ServerSocket na porta 4444
            serverSocket = new ServerSocket(4444);
            System.out.println("Sv a espera de conexao...");

            // Mantém o sv em execução contínua, aceitando conexões
            while (true) {
                // Aceita a conexão de um cliente e inicia uma nova thread para o cliente
                new KnockKnockServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            // Em caso de erro ao tentar ouvir na porta 4444 ou aceitar uma conexão
            System.err.println("Nao foi possível ouvir na porta: 4444.");
            System.exit(1); // Encerra o programa com erro

        } finally {
            // Fechar o socket do sv de forma segura
            if (serverSocket != null) serverSocket.close();
        }
    }
}

class KnockKnockServerThread extends Thread {
    private Socket socket = null; // Declaração do socket para a conexão do cliente

    public KnockKnockServerThread(Socket socket) {
        super("KnockKnockServerThread");
        this.socket = socket; // Inicializa o socket com a conexão do cliente
    }

    public void run() {
        try (
                // Prepara o PrintWriter para enviar dados ao cliente
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Prepara o BufferedReader para receber dados do cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String inputLine, outputLine;

            // Cria uma instância do protocolo Knock Knock para gerenciar a sequência de mensagens
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null); // Inicia a comunicação com a primeira mensagem
            out.println(outputLine); // Envia a primeira mensagem ao cliente

            // Loop para continuar a comunicação enquanto houver mensagens
            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine); // Processa a mensagem recebida e gera a resposta
                out.println(outputLine); // Envia a resposta ao cliente
                if (outputLine.equals("Bye.")) // Se o protocolo indicar "Bye.", encerra a conexão
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace(); // Exibe os erros de I/O que ocorrerem
        }
    }
}
