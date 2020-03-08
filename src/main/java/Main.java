import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Kalenteri kalenteri = new Kalenteri();
        Scanner lukija = new Scanner(System.in);
        LocalDate pvm = LocalDate.now();
        Kayttoliittyma liittyma = new Kayttoliittyma(lukija, kalenteri);
       
        // Koneen käyttäjä merkkijonoksi, muutetaan ensimmäinen kirjain isoksi.
        String kayttaja = System.getProperty("user.name");
        char nimenEkaKirjain = Character.toUpperCase(kayttaja.charAt(0));
        String kayttajaEkaIso = nimenEkaKirjain + kayttaja.substring(1);

        // Tervehditään käyttäjää, kerrotaan viikonpäivä sekä päiväys.
        System.out.println("Hei " + kayttajaEkaIso + "!");                       
        System.out.println("Tänään on " + Viikonpaivat.annaViikonpaiva(pvm.getDayOfWeek().getValue()) + " " + localDateStringMuutos(pvm));    // tähän perään vois printata myös kellonajan
        System.out.println("");
        
        // Käynnistetään käyttöliittymä.
        liittyma.kaynnista();
        liittyma.kasitteleKomento("d");
    }

    // Metodi muuttaa LocalDate luokan päivämäärän muodosta 2020-03-05 muotoon 5.3.2020
    public static String localDateStringMuutos(LocalDate pvm) {
        String[] osat = pvm.toString().split("-");
        String paiva = osat[2];
        String kk = osat[1];
        String vuosi = osat[0];


        if (paiva.charAt(0) == '0') {
            paiva = osat[2].substring(1);
        }
        if (kk.charAt(0) == '0') {
            kk = osat[1].substring(1);
        }
        if (vuosi.charAt(0) == '0' && vuosi.charAt(1) == '0' && vuosi.charAt(2) == '0') {
            vuosi = osat[0].substring(3);
        }
        if (vuosi.charAt(0) == '0' && vuosi.charAt(1) == '0') {
            vuosi = osat[0].substring(2);
        }
        if (vuosi.charAt(0) == '0') {
            vuosi = osat[0].substring(1);
        }

        return paiva + "." + kk + "." + vuosi;
    }
}