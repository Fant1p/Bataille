package fr.jeu.model;

import java.util.List;

public record RoundResult(Player winner, List<PlayedCard> playedCards) {

    public RoundResult {
        playedCards = List.copyOf(playedCards);
    }

    public boolean hasWinner() {
        return winner != null;
    }
}
