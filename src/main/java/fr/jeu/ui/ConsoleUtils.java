package fr.jeu.ui;

public final class ConsoleUtils {

    private ConsoleUtils() {
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
