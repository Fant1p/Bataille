package fr.jeu.model;

public enum GameStatus {
    EN_COURS("En cours", "en cours"),
    TERMINEE("Terminée", "terminée"),
    ANNULEE("Annulée", "annulée");

    private final String title;
    private final String text;

    GameStatus(String title, String text) {
        this.title = title;
        this.text = text;
    }
    public String getTitle() {
        return title;
    }
    public String getText() {return  text;}

    public boolean isFinished() {
        return this != EN_COURS;
    }
}
