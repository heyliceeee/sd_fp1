package org.example.ficha4;

import java.util.ArrayList;

/**
 * Classe ObjSharing demonstra a criação e execução de múltiplas threads
 * que compartilham o mesmo ArrayList para adicionar frases.
 */
public class ObjSharing {
    public static void main(String[] args) {
        // Número de threads a serem criadas
        int NThreads = 5;

        // ArrayList compartilhado entre as threads para armazenar as frases geradas
        ArrayList asFrases = new ArrayList();

        // Cria e inicia NThreads threads, passando o ArrayList compartilhado e o número da thread
        for (int i = 0; i < NThreads; i++) {
            // Cria uma nova thread Worker e a inicia
            new Worker(asFrases, i).start();
        }

        // Loop para pausar a main thread e exibir as frases geradas
        for (int j = 0; j < 6; j++) {
            //try {
                // Pausa a execução da thread principal por 1 segundo (1000ms)
                //Thread.sleep(1000);
            //} catch (Exception ex) {
                // Captura e ignora qualquer exceção
                //System.out.println(ex.getMessage());
            //}

            // Exibe todas as frases armazenadas no ArrayList até o momento
            for (int k = 0; k < asFrases.size(); k++) {
                System.out.println(asFrases.get(k));
            }
        }
    }
}