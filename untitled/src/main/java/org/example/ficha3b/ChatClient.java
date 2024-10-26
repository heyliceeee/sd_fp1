package org.example.ficha4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345); // Conecta ao servidor de chat

        // Thread para ler e enviar mensagens do teclado para o servidor
        new Thread(new Runnable() {
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                    String userInput;

                    while ((userInput = stdIn.readLine()) != null) {
                        out.println(userInput); // Envia mensagem para o servidor
                        if (userInput.equalsIgnoreCase("Bye")) {
                            break; // Encerra a conexão se digitar "Bye"
                        }
                    }

                    out.close();
                    stdIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Thread para receber e imprimir mensagens do servidor
        new Thread(new Runnable() {
            public void run() {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String serverMessage;

                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage); // Exibe mensagem do servidor
                    }

                    in.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
