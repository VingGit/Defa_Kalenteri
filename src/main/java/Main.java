import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        Kalenteri kalenteri = new Kalenteri();
        Scanner lukija = new Scanner(System.in,"IBM850");
        Kayttoliittyma liittyma = new Kayttoliittyma(lukija, kalenteri);

        // Estetään värien tulostuksesta syntyvä bugi.
        liittyma.kasitteleKomento("w");
        liittyma.kasitteleKomento("s");

        liittyma.kaynnista();
    }
}