import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Un pokemon : espèce (numéro du pokedex), surnom, un ou deux types
 * et quatre caractéristiques (PV, attaque, défense, vitesse).
 */
public class Pokemon {

    private int numero;
    private String nom;
    private int type1;
    private int type2;
    private int pv;
    private int pvMax;
    private int att;
    private int def;
    private int vit;

    /** Constructeur par défaut : MissingNo. */
    public Pokemon() {
        this(0, "MissingNo", Type.NORMAL, Type.SANS, 33, 136, 0, 29);
    }

    /** Constructeur complet. */
    public Pokemon(int numero, String nom, int type1, int type2, int pv, int att, int def, int vit) {
        this.numero = numero;
        this.nom = nom;
        this.type1 = type1;
        this.type2 = type2;
        this.pv = pv;
        this.pvMax = pv;
        this.att = att;
        this.def = def;
        this.vit = vit;
    }

    /** Constructeur simplifié : lit les caractéristiques dans pokedex_gen1.csv. */
    public Pokemon(int numero, String nom) {
        this(numero, nom, "pokedex_gen1.csv");
    }

    public Pokemon(int numero, String nom, String fichierCsv) {
        this.numero = numero;
        this.nom = nom;
        try (BufferedReader lecteur = Files.newBufferedReader(Paths.get(fichierCsv), StandardCharsets.UTF_8)) {
            String ligne = lecteur.readLine(); // on saute l'en-tête
            boolean trouve = false;
            while (!trouve && (ligne = lecteur.readLine()) != null) {
                // Format : Numéro;Nom;Type1;Type2;PV;Force;Défense;Vitesse;Spécial;Total
                String[] c = ligne.split(";", -1);
                if (Integer.parseInt(c[0].trim()) == numero) {
                    this.type1 = Type.getIndiceType(c[2]);
                    this.type2 = Type.getIndiceType(c[3]);
                    this.pv = Integer.parseInt(c[4].trim());
                    this.pvMax = this.pv;
                    this.att = Integer.parseInt(c[5].trim());
                    this.def = Integer.parseInt(c[6].trim());
                    this.vit = Integer.parseInt(c[7].trim());
                    trouve = true;
                }
            }
            if (!trouve) {
                throw new IllegalArgumentException("Pokemon n°" + numero + " introuvable dans " + fichierCsv);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Impossible de lire " + fichierCsv, e);
        }
    }

    // ---------- Accesseurs ----------
    public int getNumero() { return numero; }
    public String getNom() { return nom; }
    public int getType1() { return type1; }
    public int getType2() { return type2; }
    public int getPv() { return pv; }
    public int getPvMax() { return pvMax; }
    public int getAtt() { return att; }
    public int getDef() { return def; }
    public int getVit() { return vit; }

    // ---------- Modificateurs (seulement ce qui a du sens) ----------
    public void setNom(String nom) { this.nom = nom; }
    public void setPv(int pv) { this.pv = Math.max(0, pv); }

    public String getEspece() { return Type.getEspece(numero); }

    public boolean estVivant() { return pv > 0; }

    /**
     * Efficacité de ce pokemon contre un défenseur : on prend son meilleur type
     * en attaque, et on multiplie les efficacités sur les deux types du défenseur.
     */
    public double efficaciteContre(Pokemon defenseur) {
        double meilleure = 0;
        int[] typesAtt = {type1, type2};
        for (int ta : typesAtt) {
            if (ta == Type.SANS) continue;
            double e = Type.getEfficacite(ta, defenseur.type1);
            if (defenseur.type2 != Type.SANS) {
                e *= Type.getEfficacite(ta, defenseur.type2);
            }
            meilleure = Math.max(meilleure, e);
        }
        return meilleure;
    }

    /**
     * Frappe un pokemon : dégâts = att * efficacité - def (minimum 1,
     * sauf si le type est totalement inefficace : 0). Retourne les dégâts.
     */
    public int frappe(Pokemon cible) {
        double eff = efficaciteContre(cible);
        int degats = 0;
        if (eff > 0) {
            degats = Math.max(1, (int) (att * eff) - cible.def);
        }
        cible.setPv(cible.pv - degats);
        System.out.println(nom + " (" + getEspece() + ") attaque " + cible.nom
                + " [x" + eff + "] : -" + degats + " PV -> " + cible.nom + " : " + cible.pv + " PV");
        return degats;
    }

    /**
     * Un tour d'attaque : le plus rapide frappe en premier, puis l'autre
     * contre-attaque s'il est encore vivant. (Égalité de vitesse : celui qui attaque commence.)
     */
    public void attaque(Pokemon autre) {
        Pokemon premier = (autre.vit > this.vit) ? autre : this;
        Pokemon second = (premier == this) ? autre : this;
        premier.frappe(second);
        if (second.estVivant()) {
            second.frappe(premier);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pokemon)) return false;
        Pokemon p = (Pokemon) o;
        return numero == p.numero && nom.equals(p.nom) && type1 == p.type1 && type2 == p.type2
                && pv == p.pv && att == p.att && def == p.def && vit == p.vit;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(numero, nom, type1, type2, pv, att, def, vit);
    }

    @Override
    public String toString() {
        String types = Type.getNomType(type1) + (type2 != Type.SANS ? "/" + Type.getNomType(type2) : "");
        return "#" + numero + " " + getEspece() + " \"" + nom + "\" [" + types + "] PV:" + pv
                + " Att:" + att + " Def:" + def + " Vit:" + vit;
    }
}
