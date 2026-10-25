package fr.jeu.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dealer {
    public void deal(Deck deck, List<Player> players) {
        Objects.requireNonNull(deck, "Le paquet ne peut pas être null.");
        Objects.requireNonNull(players, "La liste des joueurs ne peut pas être null.");

        if (players.isEmpty()) {
            throw new IllegalArgumentException(
                    "Il faut au moins un joueur."
            );
        }

        if (players.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(
                    "La liste ne peut pas contenir de joueur null."
            );
        }

        int currentPlayer = 0;

        while (deck.size() > 0) {
            Card card = deck.drawCard().orElseThrow();
            players.get(currentPlayer).receiveCard(card);

            currentPlayer = (currentPlayer + 1) % players.size();
        }
    }
}
