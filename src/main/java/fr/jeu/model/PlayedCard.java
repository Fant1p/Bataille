package fr.jeu.model;

import java.util.Objects;

public record PlayedCard(Player player, Card card) {
    public PlayedCard {
        Objects.requireNonNull(player, "Le joueur ne peut pas être null.");
        Objects.requireNonNull(card, "La carte ne peut pas être null.");
    }
}
