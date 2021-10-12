public class ResoudreSacADos {
    public static void main(String[] args) {
        SacADos sacADos = new SacADos(args[0], Float.parseFloat(args[1]));
        switch (args[2]){
            case "gloutonne":
                sacADos.gloutonne();
                break;
            case "prog.dynamique":
                sacADos.exactesDynamique();
                break;
            case "prog.pse":
                sacADos.exactesPSE();
                break;
            default:
                System.out.println("Parametre non reconnue, voici les options:\n- gloutonne\n- prog.dynamique\n- prog.pse");
        }
    }
}