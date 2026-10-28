class Type{
    /*************** 1G *******************/

    // Noms des espèces des pokemons dans le même ordre que le pokedex
    public static final String[] espece = {"Bulbizarre","Herbizarre","Florizarre","Salamèche","Reptincel","Dracaufeu","Carapuce","Carabaffe","Tortank","Chenipan","Chrysacier","Papilusion","Aspicot","Coconfort","Dardargnan","Roucool","Roucoups","Roucarnage","Rattata","Rattatac","Piafabec","Rapasdepic","Abo","Arbok","Pikachu","Raichu","Sabelette","Sablaireau","Nidoran♀","Nidorina","Nidoqueen","Nidoran♂","Nidorino","Nidoking","Mélofée","Mélodelfe","Goupix","Feunard","Rondoudou","Grodoudou","Nosferapti","Nosferalto","Mystherbe","Ortide","Rafflesia","Paras","Parasect","Mimitoss","Aéromite","Taupiqueur","Triopikeur","Miaouss","Persian","Psykokwak","Akwakwak","Férosinge","Colossinge","Caninos","Arcanin","Ptitard","Têtarte","Tartard","Abra","Kadabra","Alakazam","Machoc","Machopeur","Mackogneur","Chétiflor","Boustiflor","Empiflor","Tentacool","Tentacruel","Racaillou","Gravalanch","Grolem","Ponyta","Galopa","Ramoloss","Flagadoss","Magnéti","Magnéton","Canarticho","Doduo","Dodrio","Otaria","Lamantine","Tadmorv","Grotadmorv","Kokiyas","Crustabri","Fantominus","Spectrum","Ectoplasma","Onix","Soporifik","Hypnomade","Krabby","Krabboss","Voltorbe","Électrode","Noeunoeuf","Noadkoko","Osselait","Ossatueur","Kicklee","Tygnon","Excelangue","Smogo","Smogogo","Rhinocorne","Rhinoféros","Leveinard","Saquedeneu","Kangourex","Hypotrempe","Hypocéan","Poissirène","Poissoroy","Stari","Staross","M. Mime","Insécateur","Lippoutou","Élektek","Magmar","Scarabrute","Tauros","Magicarpe","Léviator","Lokhlass","Métamorph","Évoli","Aquali","Voltali","Pyroli","Porygon","Amonita","Amonistar","Kabuto","Kabutops","Ptéra","Ronflex","Artikodin","Électhor","Sulfura","Minidraco","Draco","Dracolosse","Mewtwo","Mew"};

    // Noms des différents types possibles pour les pokemons. Voir l'image pokemon_table_des_types_1G.png
    public static final String[] nomsType= {"NORMAL", "FEU", "EAU", "PLANTE", "ELECTRIK", "GLACE", "COMBAT", "POISON", "SOL", "VOL", "PSY", "INSECTE", "ROCHE", "SPECTRE", "DRAGON", "SANS"};

    // Constantes pour une utilisation plus pratique des types
    public static final int NORMAL = 0;
    public static final int FEU = 1;
    public static final int EAU = 2;
    public static final int PLANTE = 3;
    public static final int ELECTRIK = 4;
    public static final int GLACE = 5;
    public static final int COMBAT = 6;
    public static final int POISON = 7;
    public static final int SOL = 8;
    public static final int VOL = 9;
    public static final int PSY = 10;
    public static final int INSECTE = 11;
    public static final int ROCHE = 12;
    public static final int SPECTRE = 13;
    public static final int DRAGON = 14;
    public static final int SANS = 15;

    // Valeur possibles des différentes efficacités.
    public static final double NEUTRE = 1.0;
    public static final double INEFFICACE = 0.0;
    public static final double PAS_EFFICACE = 0.5;
    public static final double SUPER_EFFICACE = 2.0;

    // Tableau des efficacités de type. Voir l'image pokemon_table_des_types_1G.png.
    // Utilisation efficacite[typeAtt][typeDef].
    // Exemple : efficacite[Type.EAU][Type.FEU] retournera Type.SUPER_EFFICACE.
    private static final double efficacite[][] = {
	{NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, PAS_EFFICACE, INEFFICACE, NEUTRE}, // NORMAL
	{NEUTRE, PAS_EFFICACE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, PAS_EFFICACE}, // FEU
	{NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE}, // EAU
	{NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE}, // PLANTE
	{NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE, NEUTRE, INEFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, PAS_EFFICACE}, // ELECTRIK
	{NEUTRE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE, NEUTRE, NEUTRE, SUPER_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE}, // GLACE
	{SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE, NEUTRE, PAS_EFFICACE, PAS_EFFICACE, PAS_EFFICACE, SUPER_EFFICACE, INEFFICACE, NEUTRE}, // COMBAT
	{NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, PAS_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, PAS_EFFICACE, NEUTRE}, // POISON
	{NEUTRE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE, INEFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE}, // SOL
	{NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, NEUTRE}, // VOL
	{NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, PAS_EFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE}, // PSY
	{NEUTRE, PAS_EFFICACE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, NEUTRE, PAS_EFFICACE, NEUTRE}, // INSECTE
	{NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE, PAS_EFFICACE, NEUTRE, PAS_EFFICACE, SUPER_EFFICACE, NEUTRE, SUPER_EFFICACE, NEUTRE, NEUTRE, NEUTRE}, // ROCHE
	{INEFFICACE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, INEFFICACE, NEUTRE, NEUTRE, SUPER_EFFICACE, NEUTRE}, // SPECTRE
	{NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, NEUTRE, SUPER_EFFICACE} // DRAGON
    };

    // Doit retourner une chaine des caractères contenant le nom de l'espèce du pokemon en fonction de son numero dans le pokedex.
    // Exemple :
    // Type.getEspece(94) doit retourner "Ectoplasma"
    public static String getEspece(int numPokedex){
		if (numPokedex < 1 || numPokedex > espece.length) return "MissingNo";
		return espece[numPokedex - 1];
    }

    // Doit retourner une chaine de caractères contenant le nom du type en fonction de son indice (comprendre constantes qui défini le type).
    // Exemple :
    // Type.getNomType(Type.EAU) doit retourner "eau"
    public static String getNomType(int type){
		if (type < 0 || type >= nomsType.length) return "";
		return nomsType[type].toLowerCase();
    }

    // Doit retourner l'efficacité lorsqu'un pokemon de type typeAtt attaque un pokemon de type typeDef.
    // Exemple :
    // Type.getEfficacite(Type.EAU, Type.FEU) doit retourner Type.SUPER_EFFICACE
    public static double getEfficacite(int typeAtt, int typeDef){
		// Le type SANS (ou tout indice hors tableau) ne change rien : efficacité neutre
		if (typeAtt < 0 || typeAtt >= efficacite.length || typeDef < 0 || typeDef >= efficacite[typeAtt].length) return NEUTRE;
		return efficacite[typeAtt][typeDef];
    }

    // Doit retourner l'indice du type passé en apramètre sous forme de chaine de caractère.
    // Exemple :
    // Type.getIndiceType("spectre") doit retourner Type.SPECTRE ou 13 plus simplement.
    // Attention à la casse ! 
    public static int getIndiceType(String type){
		if (type == null) return SANS;
		String t = type.trim();
		for (int i = 0; i < nomsType.length; i++) {
			if (nomsType[i].equalsIgnoreCase(t)) return i;
		}
		return t.isEmpty() ? SANS : -1;
    }
}
