# Échecs Pokémon — Génération 1 (Java)

**Projet Java — LPDIM 2025/2026**  
Réalisé par **Majda Chligui**.

## Présentation du projet

Ce projet consiste à développer un jeu Java inspiré des Pokémon de la première génération. Il comprend un système de combat entre deux Pokémon et un jeu d'échecs Pokémon avec une interface graphique utilisant la bibliothèque MG2D.

## Contenu du projet

- `Type.java` : gestion des types Pokémon et calcul de l'efficacité des attaques.
- `Pokemon.java` : classe représentant un Pokémon, avec ses constructeurs, accesseurs, méthodes `equals`, `toString` et attaque.
- `Main.java` : lancement d'un combat entre deux Pokémon jusqu'au K.O.
- `Plateau.java` : logique du plateau et gestion des déplacements.
- `Jeu.java` : interface graphique du jeu d'échecs Pokémon.
- `pokedex_gen1.csv` : données des 151 Pokémon de la première génération.
- `MG2D/` : bibliothèque graphique utilisée pour l'interface.
- `images/` : images et sprites utilisés dans le jeu.

## Partie 1 : Combat Pokémon

Le combat oppose deux Pokémon jusqu'à ce que l'un d'eux soit mis K.O.

### Règles de combat

- Le Pokémon le plus rapide attaque en premier.
- Le second Pokémon contre-attaque s'il est toujours vivant.
- Les dégâts dépendent de l'attaque, de l'efficacité du type et de la défense.
- Les dégâts sont au minimum de 1, sauf si le type de l'attaque est inefficace, auquel cas ils sont de 0.

## Partie 2 : Jeu d'échecs Pokémon

Cette partie propose un jeu de stratégie sur un plateau de 9 × 9 cases.

### Fonctionnalités

- Deux équipes de Pokémon s'affrontent.
- Chaque équipe dispose de 27 Pokémon.
- Mewtwo occupe une position centrale sur la ligne arrière de chaque équipe.
- Les joueurs jouent à tour de rôle.
- Un Pokémon peut se déplacer d'une case dans l'une des huit directions ou attaquer un Pokémon voisin.
- La partie se termine lorsqu'un joueur élimine le Mewtwo adverse.
- L'interface graphique permet de sélectionner les Pokémon à la souris.
- Les déplacements possibles et les cases d'attaque sont indiqués visuellement.
- Un journal permet de suivre les actions de la partie.

## Compilation et exécution

Ouvrir un terminal dans le dossier du projet, puis exécuter les commandes suivantes.

### Compiler le projet

```bash
javac -encoding UTF-8 MG2D/*.java MG2D/geometrie/*.java MG2D/audio/*.java *.java
```

### Lancer le jeu d'échecs Pokémon

```bash
java Jeu
```

### Lancer le combat Pokémon en console

```bash
java Main
```

## Technologies utilisées

- Java
- Programmation orientée objet
- Interface graphique avec MG2D
- Fichier CSV pour les données des Pokémon
- Git et GitHub pour la gestion du projet et du code source

## Auteur

**Majda Chligui**  
Projet Java — LPDIM 2025/2026
