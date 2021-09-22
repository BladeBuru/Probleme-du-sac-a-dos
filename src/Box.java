public class Box implements Comparable{
    private float valeur;
    private float poids;
    private String nom;
    private float moyenne;

    public Box(){
    }

    public Box(String nom, float poids, float prix) {
        this.nom = nom;
        this.poids = poids;
        this.valeur = prix;
        this.moyenne = prix/poids;
    }

    public float getPoids() {
        return poids;
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
        Box b = (Box) o;
        if (this.moyenne < b.moyenne)
            return -1;
        if (this.moyenne > b.moyenne)
            return 1;
        return 0;
    }
}
