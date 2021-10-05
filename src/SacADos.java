import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SacADos {
    private float poidsMaximal;
    private ArrayList<Items> objetList = new ArrayList<>();
    private ArrayList<Items> objetDansSac = new ArrayList<>();

    /**
     * Constructeur vide
     */
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
        while(scanner.hasNextLine()) {
            String[] split = scanner.nextLine().split(";");
            objetList.add(new Items(split[0], Float.parseFloat(split[1]), Float.parseFloat(split[2])));
        }
        scanner.close();
    }

    public static  void main(String[] args) throws IOException {
        SacADos sacADos = new SacADos("src/items.txt", 14);

        for (int i = 0; i < sacADos.objetList.size(); i++) {
            System.out.println(sacADos.objetList.get(i));
        }*/

        sacADos.exactesDynamique();
        //sacADos.gloutonne();
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
        Collections.sort(objetList);
        float poidsDirect = 0;
        int i = 0;
        while (objetList.get(i).getPoids() + poidsDirect < poidsMaximal && objetList.size()-1 <= i){
            objetDansSac.add(objetList.get(i));
            poidsDirect += objetList.get(i).getPoids();
            System.out.println(poidsDirect);
            i++;
        }
        affichage(poidsDirect, "glouton");
    }

    /**
     * Resoudre exacte dynamique
     */
    public void exactesDynamique() {
        int intPoidsMax = (int)poidsMaximal;
        List<List<Integer>> pointsList = new ArrayList<List<Integer>>();

        System.out.println("------- "+objetList.size());//

        float matrice[][] = new float[objetList.size()][intPoidsMax];
        for (int i = 0; i < objetList.size() ; i++) {
              if (objetList.get(i).getPrix() > intPoidsMax)
                  matrice[i][intPoidsMax] = 0;
              else
                  matrice[0][intPoidsMax] = objetList.get(i).getPrix();
            objetDansSac.add(objetList.get(i));

        }

    afficheTab(matrice);
    }
/**
    private float maxPoid(){
       float pMax = 0 ;
        for (Items i: objetDansSac )
            if (i.getPoids() > pMax) pMax = i.getPoids();
        return pMax;
    }*/

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
