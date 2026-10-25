
package fr.jeu.persistence;

import fr.jeu.model.Game;
import fr.jeu.model.GameStatus;
import fr.jeu.model.Player;
import fr.jeu.persistence.dto.SavedGameSummary;
import fr.jeu.persistence.entity.JoueurPartie;
import fr.jeu.persistence.entity.Partie;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;

public class GameRepository {

    /**
     * Enregistre une partie terminée ou annulée.
     *
     * @return l'identifiant de la partie créée,
     *         ou 0 si la partie est encore en cours.
     */
    public long save(Game game) {
        Objects.requireNonNull(game, "La partie ne peut pas être null.");

        GameStatus status = game.getStatus();

        if (status == GameStatus.EN_COURS) {
            return 0;
        }

        List<Player> players = game.getPlayers();
        Player winner = game.getWinner();

        Partie partie = new Partie(players.size(), status);

        for (Player player : players) {
            JoueurPartie joueur = new JoueurPartie(
                    player.getName(),
                    player.equals(winner),
                    player.getCardCount()
            );

            partie.addJoueur(joueur);
        }

        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.persist(partie);
            transaction.commit();

            return partie.getId();

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw e;
        }
    }

    public List<SavedGameSummary> findAll() {
        String hql = """
            SELECT new fr.jeu.persistence.dto.SavedGameSummary(
                p.id,
                p.dateFin,
                p.nombreJoueurs,
                p.status,
                j.nomJoueur
            )
            FROM Partie p
            LEFT JOIN p.joueurs j ON j.gagnant = true
            ORDER BY p.dateFin DESC, p.id DESC
            """;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(hql, SavedGameSummary.class)
                    .getResultList();
        }
    }
}
