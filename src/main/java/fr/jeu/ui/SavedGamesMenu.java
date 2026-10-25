package fr.jeu.ui;

import fr.jeu.model.GameStatus;
import fr.jeu.persistence.GameRepository;
import fr.jeu.persistence.dto.SavedGameSummary;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class SavedGamesMenu {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");

    private final Scanner scanner;
    private final GameRepository repository;
    private final SavedGamesDisplay display;

    public SavedGamesMenu(Scanner scanner) {
        this.scanner = scanner;
        this.repository = new GameRepository();
        this.display = new SavedGamesDisplay();
    }

    public void show() {
        List<SavedGameSummary> games = repository.findAll();

        display.displayHeader();

        if (games.isEmpty()) {
            display.displayNoSavedGames();
        } else {
            for (SavedGameSummary game : games) {
                String date = game.dateFin().format(DATE_FORMAT);
                String status = game.status().getTitle();
                String winner = game.winnerName() == null
                        ? "Aucun"
                        : game.winnerName();

                display.displayGame(
                        game.id(),
                        date,
                        game.nombreJoueurs(),
                        status,
                        winner
                );
            }
        }

        display.displayFooter();
        scanner.nextLine();
    }
}
