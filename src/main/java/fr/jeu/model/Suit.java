package fr.jeu.model;

public enum Suit {
    HEARTS("Cœur", "♥"),
    DIAMONDS("Carreau", "♦"),
    CLUBS("Trèfle", "♣"),
    SPADES("Pique", "♠");

    private final String label;
    private final String symbol;

    Suit(String label, String symbol) {
        this.label = label;
        this.symbol = symbol;
    }

    public String getLabel() {
        return label;
    }

    public String getSymbol() {
        return symbol;
    }
}
