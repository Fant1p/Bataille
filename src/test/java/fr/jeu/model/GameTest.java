
package fr.jeu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void gameStartsWithAll52CardsDistributed() {
        Game game = new Game(2);

        assertEquals(2, game.getPlayers().size());
        assertEquals(
                52,
                game.getPlayers().stream()
                        .mapToInt(Player::getCardCount)
                        .sum()
        );
        assertEquals(GameStatus.EN_COURS, game.getStatus());
        assertFalse(game.isFinished());
        assertEquals(0, game.getRoundsPlayed());
    }

    @Test
    void rejectsInvalidPlayerCount() {
        assertThrows(IllegalArgumentException.class, () -> new Game(1));
        assertThrows(IllegalArgumentException.class, () -> new Game(53));
    }

    @Test
    void rejectsNullDeck() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(2, (Deck) null, 10)
        );
    }

    @Test
    void rejectsNegativeMaxRounds() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(2, -1)
        );
    }

    @Test
    void playingOneRoundKeepsAll52Cards() {
        Game game = new Game(2, 1);

        game.playRound();

        assertEquals(1, game.getRoundsPlayed());
        assertEquals(
                52,
                game.getPlayers().stream()
                        .mapToInt(Player::getCardCount)
                        .sum()
        );
    }

    @Test
    void playUntilFinishedRespectsMaxRounds() {
        Game game = new Game(2, 1);

        Player winner = game.playUntilFinished();

        assertNull(winner);
        assertEquals(1, game.getRoundsPlayed());
        assertTrue(game.isFinished());
        assertTrue(game.isRoundLimitReached());
        assertEquals(GameStatus.TERMINEE, game.getStatus());
    }

    @Test
    void cancelMarksGameAsCancelled() {
        Game game = new Game(2);

        game.cancel();

        assertEquals(GameStatus.ANNULEE, game.getStatus());
        assertTrue(game.isFinished());
    }

    @Test
    void finishMarksGameAsFinished() {
        Game game = new Game(2);

        game.finish();

        assertEquals(GameStatus.TERMINEE, game.getStatus());
        assertTrue(game.isFinished());
    }

    @Test
    void cannotPlayRoundAfterGameIsFinished() {
        Game game = new Game(2);
        game.finish();

        assertThrows(IllegalStateException.class, game::playRound);
    }

    @Test
    void cannotPlayRoundAfterMaxRoundsIsReached() {
        Game game = new Game(2, 1);

        game.playRound();

        assertEquals(1, game.getRoundsPlayed());
        assertTrue(game.isRoundLimitReached());

        assertThrows(
                IllegalStateException.class,
                game::playRound
        );
    }
}
