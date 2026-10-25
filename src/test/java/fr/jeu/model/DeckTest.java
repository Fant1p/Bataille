
package fr.jeu.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void deckShouldContain52Cards() {
        Deck deck = new Deck();

        assertEquals(52, deck.size());
    }

    @Test
    void deckShouldContainAllFourSuits() {
        Deck deck = new Deck();

        Set<Suit> suits = deck.getCards().stream()
                .map(Card::suit)
                .collect(java.util.stream.Collectors.toSet());

        assertEquals(Set.of(Suit.values()), suits);
    }

    @Test
    void deckShouldContainAllThirteenRanks() {
        Deck deck = new Deck();

        Set<Rank> ranks = deck.getCards().stream()
                .map(Card::rank)
                .collect(java.util.stream.Collectors.toSet());

        assertEquals(Set.of(Rank.values()), ranks);
    }

    @Test
    void deckShouldContainOnlyUniqueCards() {
        Deck deck = new Deck();

        Set<Card> uniqueCards = new HashSet<>(deck.getCards());

        assertEquals(52, uniqueCards.size());
    }

    @Test
    void shuffleShouldPreserveAllCards() {
        Deck deck = new Deck();
        Set<Card> originalCards = new HashSet<>(deck.getCards());

        deck.shuffle();

        Set<Card> shuffledCards = new HashSet<>(deck.getCards());

        assertEquals(originalCards, shuffledCards);
        assertEquals(52, deck.size());
    }

    @Test
    void getCardsShouldNotAllowExternalModification() {
        Deck deck = new Deck();

        List<Card> cards = deck.getCards();

        assertThrows(
                UnsupportedOperationException.class,
                cards::clear
        );

        assertEquals(52, deck.size());
    }
}
