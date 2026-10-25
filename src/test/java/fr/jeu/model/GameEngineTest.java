
package fr.jeu.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    private final GameEngine engine = new GameEngine();

    @Test
    void highestCardWinsTheRound() {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.receiveCard(new Card(Suit.SPADES, Rank.ACE));
        player2.receiveCard(new Card(Suit.HEARTS, Rank.KING));

        RoundResult result = engine.playRound(List.of(player1, player2));

        assertEquals(player1, result.winner());
        assertEquals(2, result.playedCards().size());
        assertEquals(2, player1.getCardCount());
        assertEquals(0, player2.getCardCount());
    }

    @Test
    void tiedPlayersDuelWithTheirNextCard() {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.receiveCards(List.of(
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.SPADES, Rank.ACE)
        ));

        player2.receiveCards(List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.HEARTS, Rank.QUEEN)
        ));

        RoundResult result = engine.playRound(List.of(player1, player2));

        assertEquals(player1, result.winner());
        assertEquals(4, result.playedCards().size());
        assertEquals(4, player1.getCardCount());
        assertEquals(0, player2.getCardCount());
    }

    @Test
    void playersCanHaveSeveralConsecutiveTies() {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.receiveCards(List.of(
                new Card(Suit.HEARTS, Rank.KING),
                new Card(Suit.HEARTS, Rank.QUEEN),
                new Card(Suit.HEARTS, Rank.ACE)
        ));

        player2.receiveCards(List.of(
                new Card(Suit.SPADES, Rank.KING),
                new Card(Suit.SPADES, Rank.QUEEN),
                new Card(Suit.SPADES, Rank.JACK)
        ));

        RoundResult result = engine.playRound(List.of(player1, player2));

        assertEquals(player1, result.winner());
        assertEquals(6, result.playedCards().size());
        assertEquals(6, player1.getCardCount());
        assertEquals(0, player2.getCardCount());
    }

    @Test
    void playerWithoutCardsLosesTheDuel() {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.receiveCards(List.of(
                new Card(Suit.HEARTS, Rank.KING),
                new Card(Suit.HEARTS, Rank.ACE)
        ));

        player2.receiveCard(new Card(Suit.SPADES, Rank.KING));

        RoundResult result = engine.playRound(List.of(player1, player2));

        assertEquals(player1, result.winner());
        assertEquals(3, result.playedCards().size());
        assertEquals(3, player1.getCardCount());
        assertEquals(0, player2.getCardCount());
    }

    @Test
    void remainingPlayersContinueWhenAnotherPlayerLosesTheDuel() {
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        player1.receiveCards(List.of(
                new Card(Suit.HEARTS, Rank.KING),
                new Card(Suit.HEARTS, Rank.ACE)
        ));

        player2.receiveCard(new Card(Suit.SPADES, Rank.KING));
        player3.receiveCard(new Card(Suit.CLUBS, Rank.QUEEN));

        RoundResult result = engine.playRound(
                List.of(player1, player2, player3)
        );

        assertEquals(player1, result.winner());
        assertEquals(4, result.playedCards().size());
        assertEquals(4, player1.getCardCount());
        assertEquals(0, player2.getCardCount());
        assertEquals(0, player3.getCardCount());
    }

    @Test
    void noWinnerWhenNoTiedPlayerCanContinue() {
        Player player1 = new Player();
        Player player2 = new Player();

        player1.receiveCard(new Card(Suit.HEARTS, Rank.KING));
        player2.receiveCard(new Card(Suit.SPADES, Rank.KING));

        RoundResult result = engine.playRound(List.of(player1, player2));

        assertNull(result.winner());
        assertEquals(2, result.playedCards().size());
        assertEquals(0, player1.getCardCount());
        assertEquals(0, player2.getCardCount());
    }
}
