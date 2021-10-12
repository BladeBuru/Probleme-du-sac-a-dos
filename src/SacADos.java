import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SacADos {
    private float poidsMaximal;
    private ArrayList<Items> listObjets = new ArrayList<>();
    private ArrayList<Items> objetDansSac = new ArrayList<>();

    /**
     * Constructeur vide
     */
    public SacADos() {
    }

    /**
     * Constructeur a partir dun sac et en recopie ses items
     * @param sacADos existant
     */
    public SacADos(SacADos sacADos) {
        listObjets = null;
        objetDansSac = new ArrayList<>();
        poidsMaximal = sacADos.getPoidsMaximal();
        for (Items item : sacADos.getObjetDansSac())
            objetDansSac.add(item);
    }

    /**
     * Constructeur
     * @param chemin fichier avec les items a charger
     * @param poidsMaximal poids max du sac a dos
     */
    public SacADos(String chemin, float poidsMaximal)  {
        this.poidsMaximal = poidsMaximal;
        loadValue(chemin);
    }


    /**
     * Permet de charger les items
     * @param chemin chemin vers le fichier avec les items en format texte
     */
    private void loadValue(String chemin) {
        try {
            FileInputStream file = new FileInputStream(chemin);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(";");
                listObjets.add(new Items(split[0], Float.parseFloat(split[1]), Float.parseFloat(split[2])));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error file not found");
        }
    }

    /**
     *
     * @return Le poids du sac a dos
     */
    private float poidsDuSac(){
        float poidsTotal =0;
        for (Items item : objetDansSac)
            poidsTotal += item.getPoids();
        return poidsTotal;
    }

    /**
     *
     * @return Le Prix du sac a dos
     */
    private float prixDuSac(){
        float prixTotal =0;
        for (Items item : objetDansSac)
            prixTotal += item.getPrix();
        return prixTotal;
    }

    /**
     *
     * @return La valeur du sac a dos
     */
    public float valeurDuSac(){
        float valeurTotal =0;
        for (Items item : objetDansSac)
            valeurTotal += item.getValeur();
        return valeurTotal;
    }

    /**
     * Affichage dans la console
     * @param methode nom de la methode utilise
     */
    private void affichage(String methode) {
       System.out.println("La méthode " + methode + " peu mettre " + this.objetDansSac.size() + " objets dans le sac avec un poids total de " +
               this.poidsDuSac() + "kg sur " + this.poidsMaximal + " Kg d'une valeur de " + this.valeurDuSac() + ".");
        for (Items item : objetDansSac)
            System.out.println(item);
    }

    /**
     * Resoudre avec la methode gloutonne
     */
    public void gloutonne() {
        Collections.sort(listObjets);
        float poidsDirect = 0;
        int i = 0;
        while (listObjets.get(i).getPoids() + poidsDirect < poidsMaximal && listObjets.size()-1 >= i){
            objetDansSac.add(listObjets.get(i));
            poidsDirect += listObjets.get(i).getPoids();
            i++;
        }

        affichage( "glouton");
    }

    /**
     * Resoudre avec la methode programmation dynamique
     */
    public void exactesDynamique() {
        int intPoidsMax = (int)poidsMaximal;

        float[][] items = new float[listObjets.size()][intPoidsMax+1];
        for (int i = 0; i < intPoidsMax+1; i++) {
            if (listObjets.get(0).getPoids() > i)
                items[0][i] = 0;
            else
                items [0][i] = listObjets.get(0).getPrix();
        }

        for (int i = 1; i < listObjets.size(); i++) {
            for (int j =0; j < intPoidsMax+1; j++) {
                if (listObjets.get(i).getPoids() > j)
                    items[i][j] = items[i-1][j];
                else
                    items[i][j] = Math.max(items[i-1][j], items[i-1][j-(int) listObjets.get(i).getPoids()] + listObjets.get(i).getPrix());
            }
        }

        //affiche la matrice items
        /*
        for (int i = 0; i < objetList.size(); i++) {
            for (int j =0; j < intPoidsMax +1; j++) {
                System.out.print(items[i][j] + "\t");
            }
            System.out.println();
        }
         */
        float j = poidsMaximal;
        int i = listObjets.size()-1;
        while (j > 0 && i >= 0) {
            while (i > 0 && items[i][(int)j] == items[i-1][(int)j])
                i--;
            j = j - listObjets.get(i).getPoids();
            if (j > 0)
                objetDansSac.add(listObjets.get(i));
            i--;
        }

        affichage( "Exacte dynamique");
    }

    /**
     * Resoudre avec la methode de Procedure par separation et evaluation (PSE)
     */
    public void exactesPSE() {
        SacADos sacADos = new SacADos();
        this.objetDansSac = recursive(sacADos,0, this).objetDansSac;

        affichage("exacte PSE");
    }

    /**
     *
     * @param sacADosHaut Represente la branche du haut ou a droite dans un graphe
     * @param indice hauteur par rapport a l'index
     * @param sacADosOriginel de depart avec toutes les données
     * @return un sac a dos remplis le plus efficacement possible avec la Procedure par separation et evaluation
     */
    private SacADos recursive(SacADos sacADosHaut,int indice,SacADos sacADosOriginel) {
        SacADos sacADosBas = new SacADos(sacADosHaut);
        sacADosHaut.objetDansSac.add(sacADosOriginel.listObjets.get(indice));

        if (indice >= sacADosOriginel.listObjets.size() -1) {               // cas ou l'on arrive a la fin
            if (sacADosHaut.poidsDuSac() > sacADosOriginel.getPoidsMaximal()) {
                    return sacADosBas;
            } else
                return sacADosHaut;
        }

        if (sacADosHaut.poidsDuSac() > sacADosOriginel.getPoidsMaximal())
            return recursive(sacADosBas, indice + 1,sacADosOriginel);

        SacADos b;
        SacADos a;
        b = recursive(sacADosBas, indice + 1, sacADosOriginel);
        a = recursive(sacADosHaut, indice + 1, sacADosOriginel);

        if (a.prixDuSac() > b.prixDuSac())
            return a;
        else
            return b;
    }

    private float getPoidsMaximal() {
        return poidsMaximal;
    }

    private ArrayList<Items> getObjetDansSac() {
        return objetDansSac;
    }

    @Override
    public String toString() {
        return "SacADos{" +
                "poidsMaximal=" + poidsMaximal +
                ", objetList=" + listObjets +
                ", objetDansSac=" + objetDansSac +
                super.toString() +
                '}';
    }
}
