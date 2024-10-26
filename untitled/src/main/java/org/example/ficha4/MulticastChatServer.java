package org.example.ficha4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MulticastChatServer {
    // Configuração do endereço de grupo multicast e porta pra o chat
    private static final String GROUP_ADDRESS = "224.0.0.1"; // Endereço multicast
    private static final int PORT = 12345; // Porta do chat multicast

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) { // Cria um socket UDP simples pra enviar msgs
            String message = "Bem-vindo ao chat multicast!"; // Msg de boas-vindas pra o grupo
            byte[] buffer = message.getBytes(); // Converte a msg pra bytes
            InetAddress group = InetAddress.getByName(GROUP_ADDRESS); // Converte o endereço do grupo

            // Cria o pacote de msg pra enviar pra o grupo multicast
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(packet); // Envia o pacote pra o grupo multicast
            System.out.println("Mensagem de boas-vindas enviada para o grupo multicast."); // Confirma envio
        } catch (IOException e) {
            e.printStackTrace(); // Exibe erros de I/O
        }
    }
}