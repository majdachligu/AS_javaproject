public class Main {
    public static void main(String[] args) {
        // Deux pokemons créés avec le constructeur simplifié (lecture du CSV)
        Pokemon p1 = new Pokemon(57, "Robert");   // Colossinge
        Pokemon p2 = new Pokemon(94, "Gustave");  // Ectoplasma

        System.out.println("=== Combat Pokemon ===");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println();

        int tour = 1;
        while (p1.estVivant() && p2.estVivant()) {
            System.out.println("--- Tour " + tour++ + " ---");
            p1.attaque(p2);
        }

        Pokemon vainqueur = p1.estVivant() ? p1 : p2;
        Pokemon perdant = p1.estVivant() ? p2 : p1;
        System.out.println();
        System.out.println(perdant.getNom() + " est K.O. et retourne dans sa pokeball !");
        System.out.println("Victoire de " + vainqueur.getNom() + " !");
    }
}
