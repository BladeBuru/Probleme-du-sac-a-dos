public class ResoudreSacADos {
    public static void main(String[] args) {
        SacADos sacADos = new SacADos(args[0], Float.parseFloat(args[1]));
        long begin;
        long end;

        switch (args[2]){
            case "gloutonne":
                begin = System.nanoTime();
                sacADos.gloutonne();
                end = System.nanoTime();
                System.out.println("durée d'exécution du programme " +  (end - begin) / 1_000_000.0 +" ms.");
                break;
            case "prog.dynamique":
                begin = System.nanoTime();
                sacADos.exactesDynamique();
                end = System.nanoTime();
                System.out.println("durée d'exécution du programme " +  (end - begin) / 1_000_000.0 +" ms.");
                break;
            case "prog.pse":
                begin = System.nanoTime();
                sacADos.exactesPSE();
                end = System.nanoTime();
                System.out.println("durée d'exécution du programme " +  (end - begin) / 1_000_000.0 +" ms.");
                break;
            default:
                System.out.println("Paramètre non reconnue, voici les options:\n- gloutonne\n- prog.dynamique\n- prog.pse");
        }
    }
}
