package fr.jeu.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public class Player {
    private final int id;
    private final String name;
    private final Deque<Card> cards;

    private static int nextId = 1;

    public Player() {
        this.id = nextId++;
        this.name = "J" + id;
        this.cards = new ArrayDeque<>();
    }

    public int getId() {
        return id;
    }

    public static void resetId() {
        nextId = 1;
    }

    public String getName() {
        return name;
    }

    public int getCardCount() {
        return cards.size();
    }

    public void receiveCard(Card card) {
        cards.addLast(Objects.requireNonNull(card));
    }

    public void receiveCards(List<Card> newCards) {
        newCards.forEach(this::receiveCard);
    }

    public Card drawCard() {
        return cards.pollFirst();
    }

    public boolean hasCards() {
        return !cards.isEmpty();
    }
}
