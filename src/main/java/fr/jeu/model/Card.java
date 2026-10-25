package fr.jeu.model;

import java.util.Objects;

public record Card(Suit suit, Rank rank) {
    public Card {
        Objects.requireNonNull(suit, "La couleur est obligatoire");
        Objects.requireNonNull(rank, "La valeur est obligatoire");
    }

    @Override
    public String toString() {
        return rank.getLabel() + " " + suit.getSymbol();
    }
}
