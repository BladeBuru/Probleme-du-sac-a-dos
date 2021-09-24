import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SacADos {
    private float poidsMaximal;
    ArrayList<Items> objetArrayList = new ArrayList<Items>();
    private ArrayList<Items> objetDansSac = new ArrayList<>();

    public SacADos() {
    }

    /**
     *
     * @param chemin fichier avec les items a charger
     * @param poidsMaximal poids max du sac a dos
     */
    public SacADos(String chemin, float poidsMaximal) throws FileNotFoundException {
        this.poidsMaximal = poidsMaximal;
        loadValue(chemin);
    }

    /**
     *
     * @param chemin chemin vers le fichier avec les items a charger
     */
    public void loadValue(String chemin) throws FileNotFoundException {
        FileInputStream file = new FileInputStream(chemin);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
        {
            String[] split = scanner.nextLine().split(";");
            objetArrayList.add(new Items(split[0], Float.parseFloat(split[1]), Float.parseFloat(split[2])));
        }
        scanner.close();
    }

    /**
     * Fonction de trie rapide pours les objets
     */
    public ArrayList<Items> quickSort(ArrayList<Items> objetDansSac) {
        // code

        return objetDansSac;
    }


    public static  void main(String[] args) throws IOException {
        SacADos sacADos = new SacADos("src/items.txt", 14);

        for (int i = 0; i < sacADos.objetArrayList.size(); i++) {
            System.out.println(sacADos.objetArrayList.get(i));
        }

        sacADos.gloutonne();

    }

    /**
     * Affichage dans la console
     * @param poidsDirect poids contenu dans le sac
     * @param methode nom de la methode utilise
     */
    public void affichage(float poidsDirect, String methode) {
        System.out.println("La méthode " + methode + " peu mettre " + objetDansSac.size() + " dans le sac avec un poids total de " + poidsDirect + "kg.");
        for (Items box : objetDansSac)
            System.out.println(box);
    }

    //faire une methode qui trie quicksort 



    /**
     * Resoudre gloutonne
     */
    public void gloutonne() {
        //objetArrayList = quickSort(objetArrayList); //remplace le sort
        Collections.sort(objetArrayList);
        float poidsDirect = 0;
        int i = 0;
        while (poidsDirect < poidsMaximal){
            objetDansSac.add(objetArrayList.get(i));
            poidsDirect += objetArrayList.get(i).getPoids();
            System.out.println(poidsDirect);
            i++;
        }
        affichage(poidsDirect, "glouton");
    }

    /**
     * Resoudre exacte dynamique
     */
    public void exactesDynamique() {

    }

    /**
     * Resoudre exactes PSE
     */
    public void exactesPSE() {

    }


    @Override
    public String toString() {
        return super.toString();
    }
}
