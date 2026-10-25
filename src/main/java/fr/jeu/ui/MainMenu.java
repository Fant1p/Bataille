package fr.jeu.ui;

import fr.jeu.model.Deck;
import fr.jeu.model.Game;
import fr.jeu.persistence.dto.SavedGameSummary;

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;
    private final MainMenuDisplay display;
    private Deck deck;
    private int previousPlayers;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
        this.display = new MainMenuDisplay();
        this.deck = new Deck();
    }

    public void show() {
        boolean quit = false;
        ConsoleUtils.clear();
        while (!quit) {
            display.displayMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> deck.display();
                case "2" -> {
                    deck.shuffle();
                    System.out.println("Le paquet a été mélangé.");
                }
                case "3" -> startNewGame();
                case "4" -> startPreviousPlayersGame();
                case "5" -> new SavedGamesMenu(scanner).show();
                case "0" -> quit = true;
                default -> System.out.println("Choix invalide.");
            }
        }

        System.out.println("Au revoir !");
    }

    private void startPreviousPlayersGame() {
        if (previousPlayers == 0) {
            display.displayPreviousPlayersGame(previousPlayers);
            return;
        }

        Game game = new Game(previousPlayers);
        GameMenu menu = new GameMenu(game, scanner);
        menu.show();
    }

    private void startNewGame() {
        ConsoleUtils.clear();
        System.out.print("Nombre de joueurs (2 à 52) : ");
        String input = scanner.nextLine().trim();

        try {
            int numberOfPlayers = Integer.parseInt(input);
            Game game = new Game(numberOfPlayers, deck);
            previousPlayers = numberOfPlayers;
            GameMenu gameMenu = new GameMenu(game, scanner);
            gameMenu.show();

            deck = new Deck();
        } catch (NumberFormatException e) {
            System.out.println("Veuillez saisir un nombre entier.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
