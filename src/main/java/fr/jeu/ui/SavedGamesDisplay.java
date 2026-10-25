package fr.jeu.ui;

public class SavedGamesDisplay {

    private static final String SEPARATOR =
            "==============================================================";

    public void displayHeader() {
        ConsoleUtils.clear();
        System.out.println();
        System.out.println("                 Parties sauvegardées");
        System.out.println(SEPARATOR);
        System.out.printf("%-5s %-20s %-9s %-12s %-15s%n",
                "ID", "Date", "Joueurs", "Statut", "Vainqueur");
        System.out.println(SEPARATOR);
    }

    public void displayGame(
            Long id,
            String date,
            int nombreJoueurs,
            String status,
            String winner
    ) {
        System.out.printf("%-5d %-20s %-9d %-12s %-15s%n",
                id, date, nombreJoueurs, status, winner);
    }

    public void displayNoSavedGames() {
        System.out.println("Aucune partie sauvegardée.");
    }

    public void displayFooter() {
        System.out.println(SEPARATOR);
        System.out.println("Appuyez sur Entrée pour revenir au menu principal...");
    }
}