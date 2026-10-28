# Échecs Pokémon — Génération 1 (Java)

Projet de Java (LPDIM 2025/2026) réalisé par **Majda Chligui**.

## Contenu
- `Type.java` : noms d'espèces, types, table d'efficacité (méthodes statiques complétées)
- `Pokemon.java` : classe Pokemon (constructeurs, accesseurs, equals, toString, attaque)
- `Main.java` : combat entre deux pokémons jusqu'au K.O.
- `pokedex_gen1.csv` : données des 151 pokémons

## Règles de combat
- Le plus rapide attaque en premier, l'autre contre-attaque s'il est vivant.
- Dégâts = `att × efficacité − déf` (minimum 1, 0 si le type est inefficace).
- Efficacité : meilleur type de l'attaquant, multipliée sur les deux types du défenseur.

## Partie 2 : jeu d'échecs Pokémon
- Plateau 9x9, 27 pokémons par équipe (3 premières lignes), Mewtwo au centre de la ligne du fond
- Tour par tour : déplacer un pokemon d'une case (8 directions) OU attaquer un pokemon voisin
- La partie s'arrête quand un joueur tue le Mewtwo adverse
- Interface MG2D : accueil, sélection à la souris, ronds = déplacements, case rouge = attaque, journal

## Fichiers ajoutés
- `Plateau.java` : logique du jeu (sans affichage)
- `Jeu.java` : interface graphique
- `MG2D/` : bibliothèque graphique du cours, `images/` : sprites

## Lancer
```
javac -encoding UTF-8 MG2D/*.java MG2D/geometrie/*.java MG2D/audio/*.java *.java
java Jeu     # partie 2 (jeu)
java Main    # partie 1 (combat console)
```
