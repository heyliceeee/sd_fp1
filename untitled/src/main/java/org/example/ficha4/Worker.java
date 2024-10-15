package org.example.ficha4;

import java.util.ArrayList;

/**
 * Classe Worker que estende a classe Thread.
 * Esta classe é usada para criar threads que adicionam frases a um ArrayList partilhado.
 */
public class Worker extends Thread {
    // ArrayList partilhado entre as threads para armazenar frases
    ArrayList frases;

    // Número da thread, usado para identificar a thread
    int numero;

    /**
     * Construtor da classe Worker.
     * Inicializa o ArrayList partilhado e o número da thread.
     *
     * @param f objeto partilhado por todas as threads (ArrayList de frases)
     * @param n número identificador da thread
     */
    public Worker(ArrayList f, int n) {
        // Chamando o construtor da classe Thread com o nome "Worker"
        super("Worker");

        // Inicializa o ArrayList partilhado e o número da thread
        this.frases = f;
        this.numero = n;
    }

    /**
     * Método run() que será executado quando a thread for iniciada.
     * Este método adiciona 5 frases ao ArrayList partilhado, identificando a thread
     * e o número da iteração, e depois pausa a execução da thread por um período.
     */
    public void run() {
        // Executa um loop 5 vezes
        for (int i = 0; i < 5; i++) {
            try {
                // Adiciona uma frase ao ArrayList com o número da iteração e da thread
                frases.add("Frase " + i + " da thread " + numero);

                // Pausa a execução da thread por 500ms + 10ms * i
                Thread.sleep(500 + i * 10);
            } catch (Exception ex) {
                // Captura e ignora qualquer exceção
                // (Seria interessante logar a exceção aqui, em vez de ignorá-la)
            }
        }
    }
}