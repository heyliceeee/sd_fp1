package org.example;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // Declaração das variáveis do socket e fluxos de I/O
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Cria um ServerSocket na porta 7 (tradicionalmente usada para o sv)
            serverSocket = new ServerSocket(7);
            System.out.println("SV à espera de ligações...");

            // Aceita a conexão de um cliente (bloqueante, espera até q um cliente se conecte)
            clientSocket = serverSocket.accept();
            System.out.println("Ligação estabelecida com cliente.");

            // Prepara o PrintWriter pra enviar dados pra o cliente
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Prepara o BufferedReader pra receber dados do cliente
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            // Lê as msgs recebidas do cliente e envia de volta a mm msg
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensagem recebida: " + inputLine);
                out.println(inputLine); // Envia a mm msg de volta ao cliente
            }

        } catch (IOException e) {
            System.out.println("Erro ao tentar ouvir o porto 7 ou aceitar conexão.");
            System.exit(-1); // Encerra o programa se houver uma falha de I/O

        } finally {
            // Fechar tds os recursos de forma segura no bloco 'finally'
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        }
    }
}