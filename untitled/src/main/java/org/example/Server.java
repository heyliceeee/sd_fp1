package org.example;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // Declaração das variáveis do socket e fluxos de I/O
        ServerSocket svSocket = null;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Cria um ServerSocket na porta 7 (tradicionalmente usada para o sv)
            svSocket = new ServerSocket(7);
            System.out.println("SV à espera de ligações...");

            // Aceita a conexão de um cliente (bloqueante, espera até q um cliente se conecte)
            clientSocket = svSocket.accept();
            System.out.println("Ligação estabelecida com cliente.");

            // Prepara o PrintWriter pra enviar dados pra o cliente
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Prepara o BufferedReader pra receber dados do cliente
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //obter o ip do sv e do client
            String svIP = svSocket.getInetAddress().getHostAddress(); //ip do sv
            String clientIP = clientSocket.getInetAddress().getHostAddress(); //ip do client

            String inputLine;
            // Ler as msgs do cliente e devolver a estrutura: IP SERVIDOR:IP CLIENTE:MENSAGEM
            while ((inputLine = in.readLine()) != null) {
                System.out.println("msg recebida: " + inputLine);
                String response = svIP + ":" + clientIP + ":" + inputLine;
                out.println(response); // Enviar de volta a msg formatada
            }

        } catch (IOException e) {
            System.out.println("Erro ao tentar ouvir o porto 7 ou aceitar conexão.");
            System.exit(-1); // Encerra o programa se houver uma falha de I/O

        } finally {
            // Fechar tds os recursos de forma segura no bloco 'finally'
            if (out != null) out.close();
            if (in != null) in.close();
            if (clientSocket != null) clientSocket.close();
            if (svSocket != null) svSocket.close();
        }
    }
}