
package fr.jeu.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final int DEFAULT_MAX_ROUNDS = 10000;

    private final List<Player> players;
    private final GameEngine engine;
    private final int maxRounds;

    private int roundsPlayed;
    private GameStatus status;

    public Game(int numberOfPlayers, Deck deck, int maxRounds) {
        if (numberOfPlayers < 2 || numberOfPlayers > 52) {
            throw new IllegalArgumentException(
                    "Le nombre de joueurs doit être compris entre 2 et 52."
            );
        }

        if (deck == null) {
            throw new IllegalArgumentException(
                    "Le paquet ne peut pas être null."
            );
        }

        if (maxRounds < 0) {
            throw new IllegalArgumentException(
                    "Le nombre maximal de manches doit être positif ou égal à 0."
            );
        }

        this.players = new ArrayList<>();
        Player.resetId();

        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player());
        }

        Dealer dealer = new Dealer();
        dealer.deal(deck, players);

        this.engine = new GameEngine();
        this.maxRounds = maxRounds;
        this.roundsPlayed = 0;
        this.status = GameStatus.EN_COURS;
    }

    public Game(int numberOfPlayers, Deck deck) {
        this(numberOfPlayers, deck, DEFAULT_MAX_ROUNDS);
    }

    public Game(int numberOfPlayers, int maxRounds) {
        this(numberOfPlayers, new Deck(), maxRounds);
    }

    public Game(int numberOfPlayers) {
        this(numberOfPlayers, new Deck(), DEFAULT_MAX_ROUNDS);
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public Player getWinner() {
        return players.stream()
                .filter(player -> player.getCardCount() == 52)
                .findFirst()
                .orElse(null);
    }

    public GameStatus getStatus(){
        return status;
    }

    public void cancel() {
        if (!isFinished()) {
            this.status = GameStatus.ANNULEE;
        }
    }

    public void finish(){
        if (!isFinished()) {
            this.status = GameStatus.TERMINEE;
        }
    }

    public boolean isFinished(){
        return status.isFinished();
    }

    public boolean isRoundLimitReached() {
        return maxRounds > 0 && roundsPlayed >= maxRounds;
    }

    public RoundResult playRound() {
        if (isFinished()) {
            throw new IllegalStateException(
                    "Impossible de jouer : la partie est déjà terminée."
            );
        }

        if (isRoundLimitReached()) {
            finish();
            throw new IllegalStateException(
                    "Le nombre maximal de manches est atteint."
            );
        }

        RoundResult result = engine.playRound(players);
        roundsPlayed++;

        if (getWinner() != null || isRoundLimitReached()) {
            finish();
        }

        return result;
    }

    public Player playUntilFinished() {
        while (!isFinished() && !isRoundLimitReached()) {
            playRound();
        }

        return getWinner();
    }
}
