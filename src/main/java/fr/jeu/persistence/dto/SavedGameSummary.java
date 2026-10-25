package fr.jeu.persistence.dto;

import fr.jeu.model.GameStatus;

import java.time.OffsetDateTime;

public record SavedGameSummary(
        Long id,
       OffsetDateTime dateFin,
       int nombreJoueurs,
       GameStatus status,
       String winnerName
) {
}
