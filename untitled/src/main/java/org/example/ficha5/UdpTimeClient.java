package org.example.ficha5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class UdpTimeClient {
    private static final String MULTICAST_ADDRESS = "230.0.0.0"; // Endereço de multicast
    private static final int PORT = 12345; // Porta utilizada

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            socket.joinGroup(group); // Junta-se ao grupo multicast

            // Espera pelo pedido do servidor
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet); // Recebe o pacote do servidor
            String message = new String(packet.getData(), 0, packet.getLength());

            if (message.startsWith("TIME_REQUEST")) {
                // Envia o tempo local como resposta
                long currentTime = System.currentTimeMillis();
                String response = Long.toString(currentTime);
                byte[] responseBytes = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
                System.out.println("Tempo local enviado: " + currentTime);
            }

            socket.leaveGroup(group); // Sai do grupo multicast
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
