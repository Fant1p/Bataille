package fr.jeu.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "joueur_partie",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"partie_id", "nom_joueur"}
        )
)
public class JoueurPartie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partie_id", nullable = false)
    private Partie partie;

    @Column(name = "nom_joueur", nullable = false, length = 50)
    private String nomJoueur;

    @Column(name = "gagnant", nullable = false)
    private boolean gagnant;

    @Column(name = "cartes_restantes", nullable = false)
    private int cartesRestantes;

    protected JoueurPartie() {
    }

    public JoueurPartie(
            String nomJoueur,
            boolean gagnant,
            int cartesRestantes
    ) {
        this.nomJoueur = nomJoueur;
        this.gagnant = gagnant;
        this.cartesRestantes = cartesRestantes;
    }

    void setPartie(Partie partie) {
        this.partie = partie;
    }

    public Long getId() {
        return id;
    }

    public Partie getPartie() {
        return partie;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public boolean isGagnant() {
        return gagnant;
    }

    public int getCartesRestantes() {
        return cartesRestantes;
    }
}