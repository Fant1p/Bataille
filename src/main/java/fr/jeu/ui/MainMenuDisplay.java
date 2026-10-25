package fr.jeu.ui;

public class MainMenuDisplay {
    public void displayMainMenu(){
        System.out.println();
        System.out.println("*************** Menu principal **************");
        System.out.println("1- Afficher les cartes");
        System.out.println("2- Mélanger les cartes");
        System.out.println("3- Commencer la partie");
        System.out.println("4- Jouer une partie avec les joueurs précédents");
        System.out.println("5- Consulter les parties sauvegardées en base de données");
        System.out.println("0- Quitter");
        System.out.println("*********************************************");
        System.out.print("Votre choix : ");
    }

    public void displayPreviousPlayersGame(int previousPlayers) {
        if (previousPlayers == 0) {
            System.out.println(
                "Aucune partie précédente. "
                + "Veuillez d'abord commencer une partie."
            );
            return;
        }

        System.out.println(
            "Nouvelle partie avec "
            + previousPlayers
            + " joueurs."
        );
    }
}
