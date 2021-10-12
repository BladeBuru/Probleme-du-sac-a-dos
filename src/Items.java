public class Items implements Comparable{
    private float valeur;
    private float poids;
    private String nom;
    private float moyenne;

    /**
     * Constructeur vide
     */
    public Items(){
    }

    /**
     * Constructeur initialisation
     * @param nom de item
     * @param poids de item
     * @param prix de item
     */
    public Items(String nom, float poids, float prix) {
        this.nom = nom;
        this.poids = poids;
        this.valeur = prix;
        this.moyenne = prix/poids;
    }

    public float getValeur() {
        return valeur;
    }
    public float getPoids() {
        return poids;
    }

    public float getPrix() {
        return valeur;
    }

    public float getMoyenne() {
        return moyenne;
    }

    @Override
    public String toString() {
        return "Objet{" +
                "valeur=" + valeur +
                ", poids=" + poids +
                ", nom='" + nom + '\'' +
                ", moyenne=" + moyenne +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Items b = (Items) o;
        if (this.moyenne < b.moyenne)
            return -1;
        if (this.moyenne > b.moyenne)
            return 1;
        return 0;
    }


}
