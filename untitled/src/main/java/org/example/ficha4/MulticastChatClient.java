package org.example.ficha4;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class MulticastChatClient {
    // Configuração do endereço de grupo multicast e porta pra o chat
    private static final String GROUP_ADDRESS = "224.0.0.1"; // Endereço multicast
    private static final int PORT = 12345; // Porta do chat multicast

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) { // Cria o socket multicast
            InetAddress group = InetAddress.getByName(GROUP_ADDRESS); // Converte o endereço do grupo
            socket.joinGroup(group); // Entra no grupo multicast pra começar a receber msgs

            // Cria uma thread pra receber e exibir msgs dos outros membros do grupo
            new Thread(() -> {
                byte[] buffer = new byte[256]; // Buffer pra armazenar dados recebidos
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length); // Cria um pacote pra receber a msg
                    try {
                        socket.receive(packet); // Recebe o pacote do grupo multicast
                        String received = new String(packet.getData(), 0, packet.getLength()); // Converte a msg pra string
                        System.out.println("Recebido: " + received); // Exibe a msg recebida
                    } catch (IOException e) {
                        System.out.println("Erro ao receber mensagem: " + e.getMessage()); // Exibe erros de recebimento
                        break; // Sai do loop em caso de erro
                    }
                }
            }).start(); // Inicia a thread de recepção

            // Thread principal pra enviar msgs pra o grupo multicast
            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite suas mensagens (ou 'Bye' para sair): ");
            String message;

            // Loop pra ler e enviar msgs do user
            while (!(message = scanner.nextLine()).equalsIgnoreCase("Bye")) {
                byte[] buffer = message.getBytes(); // Converte a msg pra bytes
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT); // Cria um pacote c a msg
                socket.send(packet); // Envia o pacote pra o grupo multicast
            }

            // Sai do grupo multicast ao encerrar o chat
            socket.leaveGroup(group);
        } catch (IOException e) {
            e.printStackTrace(); // Exibe erros de I/O
        }
    }
}