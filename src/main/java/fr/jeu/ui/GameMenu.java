package fr.jeu.ui;

import fr.jeu.model.Game;
import fr.jeu.model.GameStatus;
import fr.jeu.model.Player;
import fr.jeu.persistence.GameRepository;

import java.util.Scanner;

public class GameMenu {

    private final Game game;
    private final GameDisplay display;
    private final Scanner scanner;

    public GameMenu(Game game, Scanner scanner) {
        this.game = game;
        this.scanner = scanner;
        this.display = new GameDisplay();
    }

    public void show() {
        while (!game.isFinished()) {
            display.displayGameMenu(game);

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> playNextRound();
                case "2" -> display.displayDistribution(game);
                case "3" -> playAutomatically();
                case "0" -> {
                    game.cancel();
                }
                default -> System.out.println("Choix invalide.");
            }
        }

        game.finish();
        display.displayFinishedGame(game);
        if (game.getStatus() != GameStatus.EN_COURS) {
            saveFinishedGame();
        }

        System.out.println("Appuyez sur Entrée pour quitter la partie...");
        scanner.nextLine();
    }


    private void playNextRound() {
        var result = game.playRound();

        display.displayRound(game.getRoundsPlayed(), result);

        for (Player player : game.getPlayers()) {
            if (!player.hasCards()) {
                display.displayEliminatedPlayer(player);
            }
        }
    }

    private void playAutomatically() {
        while (!game.isFinished() && !game.isRoundLimitReached()) {
            playNextRound();
        }

        if (game.getStatus().isFinished()) {
            display.displayFinishedGame(game);
        }
    }

    private void saveFinishedGame() {
        try {
            long gameId = new GameRepository().save(game);

            if (gameId > 0) {
                System.out.println(
                        "Partie enregistrée. Identifiant : " + gameId
                );
            }

        } catch (RuntimeException e) {
            System.err.println(
                    "Impossible d'enregistrer la partie : " + e.getMessage()
            );
        }
    }
}