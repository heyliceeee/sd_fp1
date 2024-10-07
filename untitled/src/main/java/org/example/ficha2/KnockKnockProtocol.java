package org.example.ficha2;

import java.util.*;

public class KnockKnockProtocol {
    // Definição dos estados do protocolo
    private static final int WAITING = 0;         // Estado inicial
    private static final int SENTKNOCKKNOCK = 1;  // Após o envio de "Knock Knock!"
    private static final int SENTCLUE = 2;        // Após o envio da pista (ex: "Turnip")
    private static final int ANOTHER = 3;         // Pergunta se o cliente quer outra piada

    private static final int NUMJOKES = 5; // Número de piadas disponíveis no protocolo

    private int state = WAITING; // Estado inicial da comunicação
    private int currentJoke = 0; // indice da piada atual

    // Arrays contendo as pistas (clues) e respostas das piadas
    private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    private String[] answers = {
            "Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?"
    };

    // Método que processa a entrada e retorna a resposta adequada
    public String processInput(String theInput) {
        String theOutput = null;

        // Estado inicial, servidor envia "Knock Knock!"
        if (state == WAITING) {
            theOutput = "Knock! Knock!";
            state = SENTKNOCKKNOCK; // Altera o estado
        }
        // Espera que o cliente responda "Who's there?"
        else if (state == SENTKNOCKKNOCK) {
            if (theInput.equalsIgnoreCase("Who's there?")) {
                theOutput = clues[currentJoke]; // Envia a pista (ex: "Turnip")
                state = SENTCLUE; // Altera o estado para aguardando a resposta do cliente
            } else {
                // Se o cliente não seguir o protocolo, envia uma mensagem de correção
                theOutput = "You're supposed to say \"Who's there?\"! Try again. Knock! Knock!";
            }
        }
        // Espera que o cliente responda com a pista + "who?"
        else if (state == SENTCLUE) {
            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                theOutput = answers[currentJoke] + " Want another? (y/n)"; // Resposta da piada
                state = ANOTHER; // Pergunta se o cliente quer outra piada
            } else {
                // Se a resposta não seguir o protocolo, envia uma mensagem de correção
                theOutput = "You're supposed to say \"" + clues[currentJoke] + " who?\"! Try again. Knock! Knock!";
                state = SENTKNOCKKNOCK; // Reinicia a sequência de piadas
            }
        }
        // Pergunta se o cliente deseja outra piada
        else if (state == ANOTHER) {
            if (theInput.equalsIgnoreCase("y")) {
                // Reinicia a sequência com uma nova piada
                theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0; // Reinicia o índice das piadas
                else
                    currentJoke++;
                state = SENTKNOCKKNOCK; // Retorna ao estado inicial para outra piada
            } else {
                theOutput = "Bye."; // Encerra a comunicação
                state = WAITING; // Reseta o estado
            }
        }

        return theOutput; // Retorna a mensagem de resposta
    }
}
