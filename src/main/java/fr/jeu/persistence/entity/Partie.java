package fr.jeu.persistence.entity;

import fr.jeu.model.GameStatus;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "partie")
public class Partie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_fin", nullable = false)
    private OffsetDateTime dateFin;

    @Column(name = "nombre_joueurs", nullable = false)
    private int nombreJoueurs;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private GameStatus status;

    @OneToMany(
            mappedBy = "partie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<JoueurPartie> joueurs = new ArrayList<>();

    protected Partie() {
    }

    public Partie(int nombreJoueurs, GameStatus status) {
        this.nombreJoueurs = nombreJoueurs;
        this.status = status;
        this.dateFin = OffsetDateTime.now();
    }

    public void addJoueur(JoueurPartie joueur) {
        joueurs.add(joueur);
        joueur.setPartie(this);
    }

    public Long getId() {
        return id;
    }

    public OffsetDateTime getDateFin() {
        return dateFin;
    }

    public int getNombreJoueurs() {
        return nombreJoueurs;
    }

    public GameStatus getStatus() {
        return status;
    }
}