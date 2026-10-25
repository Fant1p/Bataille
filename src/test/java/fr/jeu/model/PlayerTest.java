
package fr.jeu.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void newPlayerHasUniqueGeneratedIdentityAndNoCards() {
        Player player1 = new Player();
        Player player2 = new Player();

        assertTrue(player1.getId() > 0);
        assertEquals("J" + player1.getId(), player1.getName());

        assertNotEquals(player1.getId(), player2.getId());
        assertNotEquals(player1.getName(), player2.getName());

        assertEquals(0, player1.getCardCount());
        assertFalse(player1.hasCards());
    }

    @Test
    void playerCanReceiveAndDrawCard() {
        Player player = new Player();
        Card card = new Card(Suit.SPADES, Rank.ACE);

        player.receiveCard(card);

        assertEquals(1, player.getCardCount());
        assertTrue(player.hasCards());

        assertEquals(card, player.drawCard());
        assertEquals(0, player.getCardCount());
        assertFalse(player.hasCards());
    }

    @Test
    void playerDrawsCardsInTheOrderReceived() {
        Player player = new Player();

        Card firstCard = new Card(Suit.SPADES, Rank.ACE);
        Card secondCard = new Card(Suit.HEARTS, Rank.KING);

        player.receiveCards(List.of(firstCard, secondCard));

        assertEquals(firstCard, player.drawCard());
        assertEquals(secondCard, player.drawCard());
        assertEquals(0, player.getCardCount());
    }

    @Test
    void drawingFromEmptyPlayerReturnsNull() {
        Player player = new Player();

        assertNull(player.drawCard());
        assertEquals(0, player.getCardCount());
        assertFalse(player.hasCards());
    }

    @Test
    void receivingNullCardThrowsException() {
        Player player = new Player();

        assertThrows(
                NullPointerException.class,
                () -> player.receiveCard(null)
        );

        assertEquals(0, player.getCardCount());
    }
}
