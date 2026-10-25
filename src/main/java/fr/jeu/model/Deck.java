package fr.jeu.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    public int size() {
        return cards.size();
    }

    public void display() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.printf("%-12s", cards.get(i));

            if ((i + 1) % 4 == 0) {
                System.out.println();
            }
        }

        System.out.println();
        System.out.println("Nombre de cartes : " + size());
    }

    public Optional<Card> drawCard() {
        if (cards.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(cards.remove(0));
    }
}
