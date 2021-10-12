import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
     * Constructeur a partir d'un sac
     * @param sacADos
     */
    public SacADos(SacADos sacADos) {
        objetList = null;
        objetDansSac = sacADos.getObjetDansSac();
        poidsMaximal = sacADos.getPoidsMaximal();
    }

    /**
     *
     * @param chemin fichier avec les items a charger
     * @param poidsMaximal poids max du sac a dos
     */
    public SacADos(String chemin, float poidsMaximal)  {
        this.poidsMaximal = poidsMaximal;
        loadValue(chemin);
    }


    /**
     *
     * @param chemin chemin vers le fichier avec les items a charger
     */
    public void loadValue(String chemin) {
        try {
            FileInputStream file = new FileInputStream(chemin);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(";");
                objetList.add(new Items(split[0], Float.parseFloat(split[1]), Float.parseFloat(split[2])));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("erreur file not found");
        }
    }

    public static  void main(String[] args) throws IOException {
        SacADos sacADos = new SacADos("src/items.txt", 8);

        //sacADos.exactesDynamique();
        //sacADos.gloutonne();
        sacADos.exactesPSE(sacADos);
    }

    /**
     *
     * @return Le poids du sac a dos
     */
    public float poidDuSac(){
        float pmax =0;
        for (Items box : objetDansSac)
            pmax += box.getPoids();
        return pmax;
    }

    /**
     *
     * @return Le Prix du sac a dos
     */
    public float prixDuSac(){
        float pmax =0;
        for (Items box : objetDansSac)
            pmax += box.getPrix();
        return pmax;
    }

    /**
     * Affichage dans la console
     * @param methode nom de la methode utilise
     */
    public void affichage(String methode) {
       System.out.println("La méthode " + methode + " peu mettre " + objetDansSac.size() + " objets dans le sac avec un poids total de " + this.poidDuSac() + "kg sur " + poidsMaximal + "Kg.");
        for (Items box : objetDansSac)
            System.out.println(box);
    }

    /**
     * Resoudre gloutonne
     */
    public void gloutonne() {
        //objetList = Quicksort.affiche(objetList); //remplace le sort
        Collections.sort(objetList);
        float poidsDirect = 0;
        int i = 0;
        while (objetList.get(i).getPoids() + poidsDirect < poidsMaximal && objetList.size()-1 <= i){
            objetDansSac.add(objetList.get(i));
            poidsDirect += objetList.get(i).getPoids();
            System.out.println(poidsDirect);
            i++;
        }
        affichage( "glouton");
    }


    /**
     * Resoudre exactes dynamique
     */
    public void exactesDynamique() {
        int intPoidsMax = (int)poidsMaximal;

        float[][] items = new float[objetList.size()][intPoidsMax+1];
        for (int i = 0; i < intPoidsMax+1; i++) {
            if (objetList.get(0).getPoids() > i)
                items[0][i] = 0;
            else
                items [0][i] = objetList.get(0).getPrix();
        }

        for (int i = 1; i < objetList.size(); i++) {
            for (int j =0; j < intPoidsMax+1; j++) {
                if (objetList.get(i).getPoids() > j)
                    items[i][j] = items[i-1][j];
                else
                    items[i][j] = Math.max(items[i-1][j], items[i-1][j-(int)objetList.get(i).getPoids()] + objetList.get(i).getPrix());
            }
        }

        //affiche la matrice items
        for (int i = 0; i < objetList.size(); i++) {
            for (int j =0; j < intPoidsMax +1; j++) {
                System.out.print(items[i][j] + "\t");
            }
            System.out.println();
        }
        float j = poidsMaximal;
        int i = objetList.size()-1;
        while (j > 0 && i >= 0) {
            while (i > 0 && items[i][(int)j] == items[i-1][(int)j])
                i--;
            j = j - objetList.get(i).getPoids();
            if (j > 0)
                objetDansSac.add(objetList.get(i));
            i--;
        }
        affichage( "Exacte dynamique");
    }

    /**
     * Resoudre exactes PSE
     */
    public void exactesPSE(SacADos sacADosOriginel) {
        SacADos sacADos = new SacADos();

        this.objetDansSac = recursive(sacADos,0, sacADosOriginel).objetDansSac;
        affichage("exacte PSE");
    }

    public SacADos recursive(SacADos sacADosHaut,int indice,SacADos sacADosOriginel) {
        SacADos sacADosBas = new SacADos(sacADosHaut);
        sacADosHaut.objetDansSac.add(sacADosOriginel.objetList.get(indice));

        if (indice >=  sacADosOriginel.objetList.size() -1 ){               // cas ou l'on arrive a la fin
            if (sacADosHaut.poidDuSac() > sacADosOriginel.getPoidsMaximal())
                return sacADosBas;
            else
                return sacADosHaut;
        }


                // faut les comparer
        if (sacADosHaut.poidDuSac() > sacADosOriginel.getPoidsMaximal())
            return recursive(sacADosBas, indice + 1,sacADosOriginel);

        SacADos b;
        SacADos a;
        a = recursive(sacADosHaut, indice + 1, sacADosOriginel);
        b = recursive(sacADosBas, indice + 1, sacADosOriginel);

        if (a.prixDuSac() > b.prixDuSac())
            return a;
        else
            return b;
    }

    public float getPoidsMaximal() {
        return poidsMaximal;
    }

    public ArrayList<Items> getObjetDansSac() {
        return objetDansSac;
    }



    @Override
    public String toString() {
        return "SacADos{" +
                "poidsMaximal=" + poidsMaximal +
                ", objetList=" + objetList +
                ", objetDansSac=" + objetDansSac +
                super.toString() +
                '}';
    }
}
