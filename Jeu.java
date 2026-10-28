import MG2D.Couleur;
import MG2D.Fenetre;
import MG2D.Souris;
import MG2D.geometrie.Cercle;
import MG2D.geometrie.Dessin;
import MG2D.geometrie.Point;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Texte;
import MG2D.geometrie.Texture;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/** Interface graphique (MG2D) du jeu d'échecs Pokémon. */
public class Jeu {

    private static final int LARGEUR = 940, HAUTEUR = 680;
    private static final int CASE = 64, X0 = 20, Y0 = 20;
    private static final int PANNEAU_X = 620;

    private static final Couleur FOND = new Couleur(24, 30, 54);
    private static final Couleur CASE_CLAIRE = new Couleur(236, 226, 196);
    private static final Couleur CASE_SOMBRE = new Couleur(150, 170, 120);
    private static final Couleur BLEU_J1 = new Couleur(60, 110, 230);
    private static final Couleur ROUGE_J2 = new Couleur(225, 70, 70);
    private static final Couleur OR = new Couleur(255, 200, 0);
    private static final Couleur CIBLE = new Couleur(240, 120, 120);
    private static final Couleur SELECTION = new Couleur(250, 240, 110);

    private static final Font TITRE = new Font("Arial", Font.BOLD, 44);
    private static final Font GRAND = new Font("Arial", Font.BOLD, 20);
    private static final Font NORMAL = new Font("Arial", Font.PLAIN, 15);
    private static final Font PETIT = new Font("Arial", Font.PLAIN, 13);

    private enum Etat { ACCUEIL, JEU, FIN }

    private final Fenetre fenetre;
    private final Souris souris;
    private Etat etat = Etat.ACCUEIL;
    private Plateau plateau;
    private int selX = -1, selY = -1;
    private List<int[]> accessibles = new ArrayList<>();
    private List<int[]> cibles = new ArrayList<>();
    private final List<String> journal = new ArrayList<>();

    public Jeu() {
        fenetre = new Fenetre("Échecs Pokémon - Génération 1", LARGEUR, HAUTEUR);
        souris = fenetre.getSouris();
        dessiner();
    }

    public Fenetre getFenetre() { return fenetre; }

    // ---------- Boucle principale ----------
    public void boucle() {
        while (true) {
            if (souris.getClicGauche()) {
                Point p = souris.getPosition();
                clic(p.getX(), p.getY());
            }
            try { Thread.sleep(20); } catch (InterruptedException e) { return; }
        }
    }

    // ---------- Gestion des clics ----------
    public void clic(int px, int py) {
        switch (etat) {
            case ACCUEIL:
                if (px >= 350 && px <= 590 && py >= 280 && py <= 350) nouvellePartie();
                break;
            case FIN:
                etat = Etat.ACCUEIL;
                break;
            case JEU:
                int cx = Math.floorDiv(px - X0, CASE), cy = Math.floorDiv(py - Y0, CASE);
                if (Plateau.dansLePlateau(cx, cy)) clicCase(cx, cy); else deselectionner();
                break;
        }
        dessiner();
    }

    private void nouvellePartie() {
        plateau = new Plateau();
        journal.clear();
        journal.add("Bonne partie ! Tuez le Mewtwo adverse.");
        deselectionner();
        etat = Etat.JEU;
    }

    private void clicCase(int x, int y) {
        // 1. un de mes pokemons : je le sélectionne
        if (plateau.getEquipe(x, y) == plateau.getJoueurCourant()) {
            selX = x; selY = y;
            accessibles = plateau.casesAccessibles(x, y);
            cibles = plateau.cibles(x, y);
            return;
        }
        if (selX >= 0) {
            List<String> res = null;
            if (plateau.estVide(x, y)) res = plateau.deplacer(selX, selY, x, y);
            else res = plateau.attaquer(selX, selY, x, y);
            if (res != null) {
                journal.addAll(res);
                deselectionner();
                if (plateau.estTermine()) etat = Etat.FIN;
                return;
            }
        }
        // 2. un pokemon adverse sans action possible : on l'affiche juste
        if (!plateau.estVide(x, y)) { selX = x; selY = y; accessibles.clear(); cibles.clear(); }
        else deselectionner();
    }

    private void deselectionner() {
        selX = -1; selY = -1;
        accessibles = new ArrayList<>();
        cibles = new ArrayList<>();
    }

    private boolean contient(List<int[]> liste, int x, int y) {
        for (int[] c : liste) if (c[0] == x && c[1] == y) return true;
        return false;
    }

    // ---------- Affichage ----------
    private void dessiner() {
        ArrayList<Dessin> d = new ArrayList<>();
        d.add(new Rectangle(FOND, new Point(0, 0), LARGEUR, HAUTEUR, true));
        if (etat == Etat.ACCUEIL) dessinerAccueil(d);
        else {
            dessinerPlateau(d);
            dessinerPanneau(d);
            if (etat == Etat.FIN) dessinerFin(d);
        }
        fenetre.effacer();
        for (Dessin dessin : d) fenetre.ajouter(dessin);
        fenetre.rafraichir();
    }

    private void texte(List<Dessin> d, Couleur c, String s, Font f, int x, int y, int align) {
        d.add(new Texte(c, s, f, new Point(x, y), align));
    }

    private void dessinerAccueil(List<Dessin> d) {
        texte(d, OR, "ÉCHECS POKÉMON", TITRE, LARGEUR / 2, 520, Texte.CENTRE);
        texte(d, Couleur.BLANC, "Génération 1 - 9x9 - 27 pokémons par équipe", GRAND, LARGEUR / 2, 460, Texte.CENTRE);
        d.add(new Rectangle(new Couleur(60, 180, 90), new Point(350, 280), 240, 70, 20, 20, true));
        texte(d, Couleur.BLANC, "JOUER", GRAND, LARGEUR / 2, 315, Texte.CENTRE);
        texte(d, Couleur.GRIS_CLAIR, "Projet Java 2025/2026 - Majda Chligui", PETIT, LARGEUR / 2, 60, Texte.CENTRE);
    }

    private void dessinerPlateau(List<Dessin> d) {
        for (int x = 0; x < Plateau.TAILLE; x++) {
            for (int y = 0; y < Plateau.TAILLE; y++) {
                Point coin = new Point(X0 + x * CASE, Y0 + y * CASE);
                Couleur fond = ((x + y) % 2 == 0) ? CASE_SOMBRE : CASE_CLAIRE;
                if (x == selX && y == selY) fond = SELECTION;
                else if (contient(cibles, x, y)) fond = CIBLE;
                d.add(new Rectangle(fond, coin, CASE, CASE, true));

                Pokemon p = plateau.getPokemon(x, y);
                if (p != null) dessinerPokemon(d, p, plateau.getEquipe(x, y), coin);
                if (contient(accessibles, x, y)) {
                    d.add(new Cercle(Couleur.GRIS_FONCE, new Point(coin.getX() + CASE / 2, coin.getY() + CASE / 2), 11, true));
                }
            }
        }
        for (int i = 0; i < Plateau.TAILLE; i++) { // repères A-I et 1-9
            texte(d, Couleur.GRIS_CLAIR, "" + (char) ('A' + i), PETIT, X0 + i * CASE + CASE / 2, Y0 - 12, Texte.CENTRE);
            texte(d, Couleur.GRIS_CLAIR, "" + (i + 1), PETIT, X0 - 10, Y0 + i * CASE + CASE / 2, Texte.CENTRE);
        }
    }

    private void dessinerPokemon(List<Dessin> d, Pokemon p, int joueur, Point coin) {
        Couleur c = (joueur == 1) ? BLEU_J1 : ROUGE_J2;
        d.add(new Rectangle(c, new Point(coin.getX() + 2, coin.getY() + 2), CASE - 4, CASE - 4, false));
        d.add(new Rectangle(c, new Point(coin.getX() + 3, coin.getY() + 3), CASE - 6, CASE - 6, false));
        if (p.getNumero() == Plateau.MEWTWO) {
            d.add(new Rectangle(OR, new Point(coin.getX() + 5, coin.getY() + 5), CASE - 10, CASE - 10, false));
            d.add(new Rectangle(OR, new Point(coin.getX() + 6, coin.getY() + 6), CASE - 12, CASE - 12, false));
        }
        d.add(new Texture("images/" + p.getNumero() + ".png", new Point(coin.getX() + 8, coin.getY() + 12), 48, 48));
        int largeurBarre = CASE - 16;
        d.add(new Rectangle(Couleur.GRIS_FONCE, new Point(coin.getX() + 8, coin.getY() + 7), largeurBarre, 4, true));
        double part = (double) p.getPv() / p.getPvMax();
        Couleur cv = part > 0.5 ? Couleur.VERT : (part > 0.25 ? Couleur.ORANGE : Couleur.ROUGE);
        d.add(new Rectangle(cv, new Point(coin.getX() + 8, coin.getY() + 7), Math.max(1, (int) (largeurBarre * part)), 4, true));
    }

    private void dessinerPanneau(List<Dessin> d) {
        int j = plateau.getJoueurCourant();
        Couleur cj = (j == 1) ? BLEU_J1 : ROUGE_J2;
        d.add(new Rectangle(cj, new Point(PANNEAU_X, 610), 300, 40, 10, 10, true));
        texte(d, Couleur.BLANC, "Joueur " + j + (j == 1 ? " (bleu)" : " (rouge)") + " - à toi !", GRAND, PANNEAU_X + 150, 630, Texte.CENTRE);
        texte(d, Couleur.GRIS_CLAIR, "Pokémons : J1 = " + plateau.compter(1) + "   J2 = " + plateau.compter(2), PETIT, PANNEAU_X, 590, Texte.GAUCHE);

        d.add(new Rectangle(new Couleur(40, 48, 80), new Point(PANNEAU_X, 370), 300, 205, 10, 10, true));
        if (selX >= 0 && !plateau.estVide(selX, selY)) {
            Pokemon p = plateau.getPokemon(selX, selY);
            d.add(new Texture("images/" + p.getNumero() + ".png", new Point(PANNEAU_X + 10, 465), 96, 96));
            texte(d, Couleur.BLANC, p.getEspece() + " #" + p.getNumero(), GRAND, PANNEAU_X + 120, 540, Texte.GAUCHE);
            String types = Type.getNomType(p.getType1()) + (p.getType2() != Type.SANS ? " / " + Type.getNomType(p.getType2()) : "");
            texte(d, Couleur.CYAN, types, NORMAL, PANNEAU_X + 120, 515, Texte.GAUCHE);
            texte(d, Couleur.BLANC, "PV " + p.getPv() + " / " + p.getPvMax(), NORMAL, PANNEAU_X + 120, 490, Texte.GAUCHE);
            texte(d, Couleur.BLANC, "Attaque " + p.getAtt() + "   Défense " + p.getDef(), NORMAL, PANNEAU_X + 15, 440, Texte.GAUCHE);
            texte(d, Couleur.BLANC, "Vitesse " + p.getVit(), NORMAL, PANNEAU_X + 15, 415, Texte.GAUCHE);
            if (p.getNumero() == Plateau.MEWTWO) texte(d, OR, "Roi : à protéger !", PETIT, PANNEAU_X + 15, 388, Texte.GAUCHE);
        } else {
            texte(d, Couleur.GRIS_CLAIR, "Clique sur un de tes pokémons", NORMAL, PANNEAU_X + 15, 500, Texte.GAUCHE);
            texte(d, Couleur.GRIS_CLAIR, "ronds gris = déplacement (1 case)", PETIT, PANNEAU_X + 15, 460, Texte.GAUCHE);
            texte(d, Couleur.GRIS_CLAIR, "case rouge = voisin à attaquer", PETIT, PANNEAU_X + 15, 435, Texte.GAUCHE);
            texte(d, Couleur.GRIS_CLAIR, "bordure or = Mewtwo (à tuer)", PETIT, PANNEAU_X + 15, 410, Texte.GAUCHE);
        }

        texte(d, Couleur.BLANC, "Journal", GRAND, PANNEAU_X, 345, Texte.GAUCHE);
        d.add(new Rectangle(new Couleur(40, 48, 80), new Point(PANNEAU_X, 20), 300, 310, 10, 10, true));
        int debut = Math.max(0, journal.size() - 14);
        for (int i = debut; i < journal.size(); i++) {
            texte(d, Couleur.BLANC, journal.get(i), PETIT, PANNEAU_X + 10, 310 - (i - debut) * 21, Texte.GAUCHE);
        }
    }

    private void dessinerFin(List<Dessin> d) {
        int v = plateau.getVainqueur();
        d.add(new Rectangle(new Couleur(0, 0, 0), new Point(120, 250), 400, 150, 20, 20, true));
        d.add(new Rectangle(v == 1 ? BLEU_J1 : ROUGE_J2, new Point(120, 250), 400, 150, 20, 20, false));
        texte(d, OR, "VICTOIRE !", TITRE, 320, 350, Texte.CENTRE);
        texte(d, Couleur.BLANC, "Le Joueur " + v + " a éliminé le Mewtwo adverse", NORMAL, 320, 305, Texte.CENTRE);
        texte(d, Couleur.GRIS_CLAIR, "(clique pour revenir à l'accueil)", PETIT, 320, 275, Texte.CENTRE);
    }

    public static void main(String[] args) {
        new Jeu().boucle();
    }
}
