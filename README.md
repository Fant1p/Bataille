Jeu de bataille

Présentation

Jeu de bataille développé en Java 21, jouable dans un terminal. Le projet utilise Maven pour la gestion des dépendances et PostgreSQL pour la sauvegarde des parties terminées ou annulées.

Fonctionnalités

Création et mélange d'un paquet de cartes.

Partie opposant plusieurs joueurs.

Déroulement des manches et comparaison des cartes jouées.

Gestion des égalités.

Limitation du nombre de manches.

Consultation des parties sauvegardées.

Persistance des résultats dans une base de données PostgreSQL.

Technologies utilisées

Java 21 : langage de programmation.

Maven : gestion du projet et des dépendances.

Hibernate : persistance des données.

PostgreSQL : base de données relationnelle.

Règles et choix de conception

Gestion des égalités

Lorsqu'au moins deux joueurs jouent une carte de même valeur maximale, une égalité est détectée. Le traitement de cette égalité suit les règles définies par le jeu.

À compléter : préciser si les joueurs concernés rejouent une carte, si les autres joueurs sont exclus de la résolution de l'égalité et comment le gagnant de la manche est finalement déterminé.

Limitation du nombre de manches

Une partie est limitée à un nombre maximal de manches afin d'éviter qu'elle ne se prolonge indéfiniment.

Cette limite permet également de définir une condition de fin lorsque personne n'a remporté la partie avant son atteinte.

À compléter : indiquer le nombre maximal de manches retenu et expliquer comment le résultat final est déterminé si la limite est atteinte sans vainqueur.

Fin d'une partie

Une partie peut se terminer lorsqu'un joueur remplit la condition de victoire prévue par les règles, lorsque la limite de manches est atteinte ou lorsque la partie est annulée.

Les résultats des parties terminées ou annulées sont enregistrés en base de données pour permettre leur consultation ultérieure.

Installation et lancement

Prérequis

JDK 21

Maven

PostgreSQL

Configuration de la base de données

Créer la base de données jeu_bataille.

Exécuter le script SQL fourni dans le dossier database/.

Configurer les variables d'environnement DB_USER et DB_PASSWORD pour accéder à PostgreSQL.

Lancement

Depuis la racine du projet :

mvn compile exec:java "-Dexec.mainClass=fr.jeu.Main"

Tests

Pour exécuter les tests :

mvn test

Architecture

Le projet est organisé en plusieurs packages :

fr.jeu.model : règles métier, cartes, joueurs et déroulement des parties.

fr.jeu.ui : menus et affichage dans le terminal.

fr.jeu.persistence : accès à la base de données.

fr.jeu.persistence.entity : entités Hibernate.

fr.jeu.persistence.dto : objets destinés au transfert des données de consultation.

Améliorations possibles

Amélioration de l'expérience utilisateur dans le terminal.

Enrichissement des statistiques des parties sauvegardées.

Ajout de nouveaux modes de jeu.