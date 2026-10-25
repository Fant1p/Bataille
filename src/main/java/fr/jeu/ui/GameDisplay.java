
package fr.jeu.ui;

import fr.jeu.model.*;

import java.util.Objects;

public class GameDisplay {
    private static final String SEPARATOR =
            "================================";

    public void displayGameMenu(Game game){
        System.out.println();
        System.out.println(
                "******** Manche " + (game.getRoundsPlayed() + 1)
                        + " ********"
        );
        System.out.println("1- Main suivante");
        System.out.println("2- Afficher la distribution des cartes par joueur");
        System.out.println("3- Automatiser les mains jusqu’à la fin de la partie");
        System.out.println("0- Retour au menu principal");
        System.out.println("**************************************");
        System.out.print("Votre choix : ");
    }

    public void displayRound(int roundNumber, RoundResult result) {
        Objects.requireNonNull(result, "Le résultat de la manche ne peut pas être null.");

        System.out.println();
        System.out.println("========== Manche " + roundNumber + " ==========");
        System.out.println("Cartes jouées :");

        for (PlayedCard playedCard : result.playedCards()) {
            System.out.println(
                    "  " + playedCard.player().getName()
                            + " : " + playedCard.card()
            );
        }

        if (result.hasWinner()) {
            System.out.println(
                    "Gagnant de la manche : " + result.winner().getName()
            );
        } else {
            System.out.println("Aucun gagnant pour cette manche.");
        }
    }

    public void displayDistribution(Game game) {
        ConsoleUtils.clear();
        System.out.println();
        System.out.println("------ Distribution des cartes ------");
        for (Player player : game.getPlayers()) {
            System.out.println(
                    player.getName() + " : "
                            + player.getCardCount() + " carte(s)"
            );
        }
        System.out.println("-------------------------------------");
    }

    public void displayEliminatedPlayer(Player player) {
        System.out.println(player.getName() + " : éliminé");
    }

    public void displayFinishedGame(Game game) {
        ConsoleUtils.clear();
        System.out.println(SEPARATOR);
        if(game.getStatus().isFinished()){
            System.out.println("Partie "+game.getStatus().getText()+".");
            if(game.getWinner() == null){
                if(game.getStatus() == GameStatus.TERMINEE){
                    System.out.println("Limite de manches atteinte.");
                    System.out.println(SEPARATOR);
                    System.out.println("Répartition finale des cartes :");
                    displayDistribution(game);
                }
                System.out.println("La partie s'est arrêtée sans vainqueur.");
                System.out.println();
            }
            System.out.println(SEPARATOR);
        }
    }
}
