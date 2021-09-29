import java.util.ArrayList;

public class Quicksort {

    public int partitionner(ArrayList<Integer> tab, int premier, int dernier, int pivot) {
        echanger(tab, pivot, dernier);
        int j = premier;
        for (int i = premier; i <= dernier - 1; i++) {
            if (tab.get(i) <= tab.get(dernier)) {
                echanger(tab, i, j);
                j++;
            }
        }
        echanger(tab, dernier, j);
        return j;
    }

    public void echanger(ArrayList<Integer> tab, int pivot, int dernier) {
        int tmp = tab.get(pivot);
        tab.set(pivot,tab.get(dernier));
        tab.set(dernier, tmp);
    }

    public void tri_rapide(ArrayList<Integer> tab, int premier, int dernier) {
        if (premier < dernier) {
            int pivot = (premier+dernier)/2;
            pivot = partitionner(tab, premier, dernier, pivot);
            tri_rapide(tab, premier, pivot-1);
            tri_rapide(tab, pivot+1, dernier);
        }
    }

    public void affiche(ArrayList<Integer> tab){
        for (int j : tab) {
            System.out.print(j + ";");
        }
    }
}