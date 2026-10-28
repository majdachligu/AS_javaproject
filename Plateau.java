import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Logique du jeu d'échecs Pokémon : plateau 9x9, deux équipes de 27 pokémons.
 * Coordonnées (x, y) : x = colonne (0 à gauche), y = ligne (0 en bas).
 * Aucune dépendance à l'affichage : cette classe est testable seule.
 */
public class Plateau {

    public static final int TAILLE = 9;
    public static final int MEWTWO = 150;      // le "roi" de chaque équipe
    private static final int[][] DIRECTIONS = {
        {-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}
    };

    private final Pokemon[][] grille = new Pokemon[TAILLE][TAILLE];
    private final int[][] equipe = new int[TAILLE][TAILLE]; // 0 = vide, 1 ou 2 = joueur
    private int joueurCourant = 1;
    private int vainqueur = 0;

    public Plateau() {
        this(new Random());
    }

    public Plateau(Random hasard) {
        placerEquipe(1, hasard);
        placerEquipe(2, hasard);
    }

    /** Joueur 1 : lignes 0 à 2 (bas). Joueur 2 : lignes 6 à 8 (haut). Mewtwo au centre du fond. */
    private void placerEquipe(int joueur, Random hasard) {
        int premiereLigne = (joueur == 1) ? 0 : TAILLE - 3;
        int ligneFond = (joueur == 1) ? 0 : TAILLE - 1;

        List<Integer> numeros = new ArrayList<>();
        for (int n = 1; n <= 151; n++) {
            if (n != MEWTWO) numeros.add(n);
        }
        Collections.shuffle(numeros, hasard);

        int suivant = 0;
        for (int y = premiereLigne; y < premiereLigne + 3; y++) {
            for (int x = 0; x < TAILLE; x++) {
                int numero = (x == TAILLE / 2 && y == ligneFond) ? MEWTWO : numeros.get(suivant++);
                grille[x][y] = new Pokemon(numero, Type.getEspece(numero));
                equipe[x][y] = joueur;
            }
        }
    }

    // ---------- Consultation ----------
    public Pokemon getPokemon(int x, int y) { return grille[x][y]; }
    public int getEquipe(int x, int y) { return equipe[x][y]; }
    public boolean estVide(int x, int y) { return grille[x][y] == null; }
    public int getJoueurCourant() { return joueurCourant; }
    public int getVainqueur() { return vainqueur; }
    public boolean estTermine() { return vainqueur != 0; }

    public static boolean dansLePlateau(int x, int y) {
        return x >= 0 && x < TAILLE && y >= 0 && y < TAILLE;
    }

    /** Nom de case façon échecs : colonne A-I, ligne 1-9. */
    public static String nomCase(int x, int y) {
        return "" + (char) ('A' + x) + (y + 1);
    }

    /** Un pokemon se déplace d'une seule case, dans les 8 directions. */
    public static int portee(Pokemon p) {
        return 1;
    }

    public int compter(int joueur) {
        int n = 0;
        for (int x = 0; x < TAILLE; x++)
            for (int y = 0; y < TAILLE; y++)
                if (equipe[x][y] == joueur) n++;
        return n;
    }

    // ---------- Déplacements ----------

    /** Distance (en pas, 8 directions, sans traverser de pokemon) depuis (x, y), -1 si inaccessible. */
    private int[][] distances(int x, int y, int max) {
        int[][] dist = new int[TAILLE][TAILLE];
        for (int[] ligne : dist) java.util.Arrays.fill(ligne, -1);
        dist[x][y] = 0;
        List<int[]> file = new ArrayList<>();
        file.add(new int[]{x, y});
        for (int i = 0; i < file.size(); i++) {
            int[] c = file.get(i);
            if (dist[c[0]][c[1]] == max) continue;
            for (int[] d : DIRECTIONS) {
                int nx = c[0] + d[0], ny = c[1] + d[1];
                if (dansLePlateau(nx, ny) && estVide(nx, ny) && dist[nx][ny] == -1) {
                    dist[nx][ny] = dist[c[0]][c[1]] + 1;
                    file.add(new int[]{nx, ny});
                }
            }
        }
        return dist;
    }

    /** Cases vides où le pokemon en (x, y) peut aller. */
    public List<int[]> casesAccessibles(int x, int y) {
        List<int[]> res = new ArrayList<>();
        if (estVide(x, y)) return res;
        int[][] dist = distances(x, y, portee(grille[x][y]));
        for (int i = 0; i < TAILLE; i++)
            for (int j = 0; j < TAILLE; j++)
                if (dist[i][j] > 0) res.add(new int[]{i, j});
        return res;
    }

    private static boolean voisines(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)) == 1;
    }

    /** Pokemons adverses sur une case voisine de (x, y) : ceux que l'on peut attaquer. */
    public List<int[]> cibles(int x, int y) {
        List<int[]> res = new ArrayList<>();
        if (estVide(x, y)) return res;
        for (int i = 0; i < TAILLE; i++)
            for (int j = 0; j < TAILLE; j++)
                if (equipe[i][j] != 0 && equipe[i][j] != equipe[x][y] && voisines(x, y, i, j))
                    res.add(new int[]{i, j});
        return res;
    }

    // ---------- Actions (retournent les lignes du journal) ----------

    /** Déplace le pokemon de (x, y) en (nx, ny). Retourne null si le coup est refusé. */
    public List<String> deplacer(int x, int y, int nx, int ny) {
        if (estTermine() || estVide(x, y) || equipe[x][y] != joueurCourant) return null;
        boolean ok = false;
        for (int[] c : casesAccessibles(x, y)) ok |= (c[0] == nx && c[1] == ny);
        if (!ok) return null;

        List<String> journal = new ArrayList<>();
        journal.add("J" + joueurCourant + " : " + grille[x][y].getNom() + " " + nomCase(x, y) + " -> " + nomCase(nx, ny));
        poser(x, y, nx, ny);
        finDeTour();
        return journal;
    }

    /** Le pokemon de (x, y) attaque le pokemon voisin en (cx, cy). Retourne null si le coup est refusé. */
    public List<String> attaquer(int x, int y, int cx, int cy) {
        if (estTermine() || estVide(x, y) || equipe[x][y] != joueurCourant) return null;
        if (estVide(cx, cy) || equipe[cx][cy] == joueurCourant) return null;
        if (!voisines(x, y, cx, cy)) return null;

        Pokemon att = grille[x][y];
        Pokemon def = grille[cx][cy];
        int pvAtt = att.getPv(), pvDef = def.getPv();

        att.attaque(def); // le plus rapide frappe d'abord, l'autre riposte s'il survit

        List<String> journal = new ArrayList<>();
        journal.add(att.getNom() + " (J" + joueurCourant + ") attaque " + def.getNom());
        journal.add("  " + def.getNom() + " : " + pvDef + " -> " + def.getPv() + " PV");
        journal.add("  " + att.getNom() + " : " + pvAtt + " -> " + att.getPv() + " PV");
        if (!def.estVivant()) retirer(cx, cy, journal);
        if (!att.estVivant()) retirer(x, y, journal);

        finDeTour();
        return journal;
    }

    private void poser(int x, int y, int nx, int ny) {
        grille[nx][ny] = grille[x][y];
        equipe[nx][ny] = equipe[x][y];
        grille[x][y] = null;
        equipe[x][y] = 0;
    }

    private void retirer(int x, int y, List<String> journal) {
        Pokemon mort = grille[x][y];
        int proprietaire = equipe[x][y];
        journal.add("  K.O. : " + mort.getNom() + " (J" + proprietaire + ")");
        grille[x][y] = null;
        equipe[x][y] = 0;
        if (mort.getNumero() == MEWTWO) {
            vainqueur = 3 - proprietaire;
            journal.add("Mewtwo de J" + proprietaire + " est tombé !");
        }
    }

    private void finDeTour() {
        if (!estTermine()) joueurCourant = 3 - joueurCourant;
    }
}
