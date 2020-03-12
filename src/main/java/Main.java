import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Kalenteri kalenteri = new Kalenteri();
        Scanner lukija = new Scanner(System.in);
        Kayttoliittyma liittyma = new Kayttoliittyma(lukija, kalenteri);

        liittyma.kaynnista();
    }
}