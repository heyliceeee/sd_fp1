package org.example.ficha2;

import java.io.*;
import java.net.*;

public class KnockKnockClient {
    public static void main(String[] args) throws IOException {
        // Declaração das variáveis do socket e fluxos de I/O
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Conecta ao sv que está a correr no localhost na porta 4444
            kkSocket = new Socket("localhost", 4444);

            // Prepara o PrintWriter para enviar dados ao sv
            out = new PrintWriter(kkSocket.getOutputStream(), true);

            // Prepara o BufferedReader para receber dados do sv
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));

            // Prepara o BufferedReader para ler a entrada do utilizador (do terminal)
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer; // Armazena a mensagem recebida do servidor
            String fromUser;   // Armazena a entrada do utilizador (a mensagem que será enviada ao sv)

            // Loop para comunicação com o sv até o sv encerrar (responder "Bye.")
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer); // Exibe a mensagem recebida do sv

                // Se o sv enviar "Bye.", o cliente encerra a comunicação
                if (fromServer.equals("Bye."))
                    break;

                // Lê a resposta do utilizador do terminal
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser); // Exibe a mensagem que será enviada
                    out.println(fromUser); // Envia a mensagem ao sv
                }
            }
        } catch (UnknownHostException e) {
            // Caso o host 'localhost' não seja encontrado
            System.err.println("Nao sei sobre o host: localhost.");
            System.exit(1); // Encerra o programa com erro

        } catch (IOException e) {
            // Caso haja erro de I/O ao tentar conectar ao servidor
            System.err.println("Nao consegui obter I/O para a conexao com: localhost.");
            System.exit(1); // Encerra o programa com erro

        } finally {
            // Fechar todos os recursos de forma segura no bloco 'finally'
            if (out != null) out.close();
            if (in != null) in.close();
            if (kkSocket != null) kkSocket.close();
        }
    }
}
