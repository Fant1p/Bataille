package fr.jeu;

import fr.jeu.ui.MainMenu;
import fr.jeu.ui.MainMenuDisplay;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            MainMenu mainMenu = new MainMenu(scanner);
            mainMenu.show();
        }
    }
}