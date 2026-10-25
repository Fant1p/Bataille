
package fr.jeu.persistence;

import fr.jeu.model.Card;
import fr.jeu.model.Game;
import fr.jeu.model.GameStatus;
import fr.jeu.model.Player;
import fr.jeu.persistence.entity.JoueurPartie;
import fr.jeu.persistence.entity.Partie;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameRepositoryTest {

    @Test
    void shouldSaveFinishedGameAndItsPlayers() {
        Game game = new Game(2);

        List<Player> players = game.getPlayers();
        Player winner = players.get(0);
        Player loser = players.get(1);

        // Donne les cartes du perdant au vainqueur.
        List<Card> cards = new ArrayList<>();

        while (loser.hasCards()) {
            cards.add(loser.drawCard());
        }

        winner.receiveCards(cards);

        assertSame(winner, game.getWinner());

        // La partie doit être terminée pour être enregistrée.
        game.finish();

        long gameId = 0;

        try {
            gameId = new GameRepository().save(game);

            assertTrue(gameId > 0);

            try (Session session =
                         HibernateUtil.getSessionFactory().openSession()) {

                Partie partie = session.find(Partie.class, gameId);

                assertNotNull(partie);
                assertEquals(2, partie.getNombreJoueurs());
                assertEquals(GameStatus.TERMINEE, partie.getStatus());

                Long playerCount = session.createSelectionQuery(
                        """
                        select count(j)
                        from JoueurPartie j
                        where j.partie.id = :id
                        """,
                        Long.class
                ).setParameter("id", gameId).getSingleResult();

                assertEquals(2L, playerCount);

                Long winnerCount = session.createSelectionQuery(
                        """
                        select count(j)
                        from JoueurPartie j
                        where j.partie.id = :id
                          and j.gagnant = true
                          and j.cartesRestantes = 52
                        """,
                        Long.class
                ).setParameter("id", gameId).getSingleResult();

                assertEquals(1L, winnerCount);

                Long loserCount = session.createSelectionQuery(
                        """
                        select count(j)
                        from JoueurPartie j
                        where j.partie.id = :id
                          and j.gagnant = false
                          and j.cartesRestantes = 0
                        """,
                        Long.class
                ).setParameter("id", gameId).getSingleResult();

                assertEquals(1L, loserCount);
            }
        } finally {
            // Supprime uniquement les données créées par ce test.
            if (gameId > 0) {
                deleteTestGame(gameId);
            }
        }
    }

    private void deleteTestGame(long gameId) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            try {
                Partie partie = session.find(Partie.class, gameId);

                if (partie != null) {
                    session.remove(partie);
                }

                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }
}
