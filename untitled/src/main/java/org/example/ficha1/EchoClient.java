package org.example.ficha1;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        // Declaração das variáveis do socket e fluxos de I/O
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("localhost", 7); // Conecta ao sv q está a correr no localhost na porta 7
            out = new PrintWriter(echoSocket.getOutputStream(), true); // Prepara o PrintWriter pra enviar dados pra o sv
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); // Prepara o BufferedReader pra ler os dados recebidos do sv
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in)); // BufferedReader pra ler a entrada do utilizador (a partir do terminal)

            String userInput;

            // Lê as msgs do utilizador (a partir do terminal) e envia pra o sv
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput); // Envia a msg pra o sv
                System.out.println("client: " + in.readLine()); // Exibe a resposta (echo) do sv
            }

        } catch (UnknownHostException e) {
            // Caso o host 'localhost' n seja encontrado
            System.err.println("N sei sobre o host: localhost.");
            System.exit(1); // Encerra o programa com erro

        } catch (IOException e) {
            // Caso haja erro de I/O ao tentar conectar ao sv
            System.err.println("N consegui obter I/O pra a conexão com: localhost.");
            System.exit(1); // Encerra o programa com erro

        } finally {
            // Fechar tds os recursos de forma segura no bloco 'finally'
            if (out != null) out.close();
            if (in != null) in.close();
            if (echoSocket != null) echoSocket.close();
        }
    }
}
