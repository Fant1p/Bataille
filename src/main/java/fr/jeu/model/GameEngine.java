
package fr.jeu.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameEngine {

    public RoundResult playRound(List<Player> players) {
        Objects.requireNonNull(players, "Les joueurs ne peuvent pas être null.");

        if (players.isEmpty()) {
            throw new IllegalArgumentException("Il faut au moins un joueur.");
        }

        if (players.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(
                    "La liste ne peut pas contenir de joueur null."
            );
        }

        List<Card> pot = new ArrayList<>();
        List<PlayedCard> playedCards = new ArrayList<>();
        Map<Player, Card> visibleCards = new LinkedHashMap<>();

        // Chaque joueur ayant des cartes joue une carte.
        for (Player player : players) {
            Card card = player.drawCard();

            if (card != null) {
                pot.add(card);
                playedCards.add(new PlayedCard(player, card));
                visibleCards.put(player, card);
            }
        }

        // Aucun joueur n'a pu jouer.
        if (visibleCards.isEmpty()) {
            return new RoundResult(null, playedCards);
        }

        while (true) {
            int highestValue = visibleCards.values().stream()
                    .mapToInt(card -> card.rank().getStrength())
                    .max()
                    .orElseThrow();

            List<Player> contenders = visibleCards.entrySet().stream()
                    .filter(entry ->
                            entry.getValue().rank().getStrength() == highestValue)
                    .map(Map.Entry::getKey)
                    .toList();

            // Un seul joueur possède la carte la plus forte.
            if (contenders.size() == 1) {
                Player winner = contenders.get(0);
                winner.receiveCards(pot);
                return new RoundResult(winner, playedCards);
            }

            // Les joueurs à égalité tentent de poursuivre le duel.
            Map<Player, Card> nextVisibleCards = new LinkedHashMap<>();

            for (Player player : contenders) {
                Card card = player.drawCard();

                // Un joueur sans carte perd le duel.
                if (card != null) {
                    pot.add(card);
                    playedCards.add(new PlayedCard(player, card));
                    nextVisibleCards.put(player, card);
                }
            }

            // Aucun joueur ne peut continuer : aucun vainqueur.
            if (nextVisibleCards.isEmpty()) {
                return new RoundResult(null, playedCards);
            }

            // Un seul joueur peut continuer : il remporte le duel.
            if (nextVisibleCards.size() == 1) {
                Player winner = nextVisibleCards.keySet().iterator().next();
                winner.receiveCards(pot);
                return new RoundResult(winner, playedCards);
            }

            // Plusieurs joueurs peuvent continuer : on compare leurs cartes.
            visibleCards = nextVisibleCards;
        }
    }
}
