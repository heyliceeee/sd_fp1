package org.example.ficha5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class UdpTimeServer {
    private static final String MULTICAST_ADDRESS = "230.0.0.0"; // Endereço de multicast
    private static final int PORT = 12345; // Porta utilizada
    private static final int TIMEOUT = 5000; // Tempo limite de espera em milissegundos
    private static final int NUM_DATAGRAMS = 10; // Número de datagramas a serem enviados

    public static void main(String[] args) {
        try (MulticastSocket socket = new MulticastSocket()) {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            int packetsLost = 0; // Contador de pacotes perdidos
            long totalRTT = 0; // Total de RTT

            // Enviar pedidos de tempo
            for (int i = 0; i < NUM_DATAGRAMS; i++) {
                long startTime = System.nanoTime(); // Tempo de início
                String request = "TIME_REQUEST_" + i; // Pedido de tempo
                byte[] buffer = request.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
                socket.send(packet);
                System.out.println("Pedido de tempo enviado: " + request);

                // Configura o timeout para receber respostas
                socket.setSoTimeout(TIMEOUT);
                try {
                    DatagramPacket responsePacket = new DatagramPacket(new byte[256], 256);
                    socket.receive(responsePacket);
                    long rtt = System.nanoTime() - startTime; // Calcular RTT
                    totalRTT += rtt;
                    System.out.println("RTT #" + i + ": " + rtt + " nanoseconds");
                } catch (SocketTimeoutException e) {
                    System.out.println("Timeout: Pacote #" + i + " perdido.");
                    packetsLost++;
                }
            }

            // Calcular a porcentagem de perda de pacotes e RTT médio
            double packetLoss = (packetsLost / (double) NUM_DATAGRAMS) * 100;
            System.out.println("Percentual de perda de pacotes: " + packetLoss + "%");
            System.out.println("RTT médio: " + (totalRTT / (NUM_DATAGRAMS - packetsLost)) + " nanoseconds");

            // Aqui você pode adicionar lógica para calcular o tempo médio recebido
            // e o ajuste para os clientes
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
