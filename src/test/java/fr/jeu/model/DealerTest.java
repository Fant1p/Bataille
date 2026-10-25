
package fr.jeu.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    private final Dealer dealer = new Dealer();

    @Test
    void distributesAll52CardsAmongThreePlayers() {
        Deck deck = new Deck();
        deck.shuffle();

        List<Player> players = List.of(
                new Player(),
                new Player(),
                new Player()
        );

        dealer.deal(deck, players);

        assertEquals(18, players.get(0).getCardCount());
        assertEquals(17, players.get(1).getCardCount());
        assertEquals(17, players.get(2).getCardCount());

        assertEquals(
                52,
                players.stream()
                        .mapToInt(Player::getCardCount)
                        .sum()
        );
        assertEquals(0, deck.size());
    }

    @Test
    void distributesCardsEquallyBetweenTwoPlayers() {
        Deck deck = new Deck();

        List<Player> players = List.of(
                new Player(),
                new Player()
        );

        dealer.deal(deck, players);

        assertEquals(26, players.get(0).getCardCount());
        assertEquals(26, players.get(1).getCardCount());
        assertEquals(0, deck.size());
    }

    @Test
    void refusesAnEmptyPlayerList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> dealer.deal(new Deck(), List.of())
        );
    }

    @Test
    void refusesANullDeck() {
        assertThrows(
                NullPointerException.class,
                () -> dealer.deal(null, List.of(new Player()))
        );
    }

    @Test
    void refusesANullPlayerList() {
        assertThrows(
                NullPointerException.class,
                () -> dealer.deal(new Deck(), null)
        );
    }

    @Test
    void refusesAPlayerListContainingNull() {
        List<Player> players = java.util.Arrays.asList(
                new Player(),
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> dealer.deal(new Deck(), players)
        );
    }
}
