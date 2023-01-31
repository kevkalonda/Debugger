package dbg;

public class JDISimpleDebuggee {

    public static void main(String[] args) {
        String description = "Simple power " +
                "printer";
        System.out.println(description + " -- starting");
        int x = 40;
        int power = 2;
        boolean sortie = false;
        int compteur = 1;
        /*while(!sortie){
            System.out.println("Compteur : "+compteur);
            if(compteur==5){
                sortie = true;
            }
            compteur++;
        }*/
        printPower(x, power);
    }

    public static double power(int x, int power) {
        double powerX = Math.pow(x, power);
        return powerX;
    }

    public static void printPower(int x, int power) {
        double powerX = power(x, power);
        System.out.println(powerX);
    }
}