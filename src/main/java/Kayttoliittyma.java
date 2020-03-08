import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.DateTimeException;
import java.util.Scanner;

public class Kayttoliittyma {

    private  Scanner lukija;
    private  Kalenteri kalenteri;
    
    public Kayttoliittyma(Scanner lukija, Kalenteri kalenteri) {
        this.kalenteri = kalenteri;
        this.lukija = lukija;
    }



    public void kaynnista() {
        // Kaksi seuraavaa riviä estävät värien tulostukseen liittyvän bugin.
        kasitteleKomento("d");
        kasitteleKomento("a");

        while (true) {
            this.kalenteri.tulostaKuukausiNakyma();
            System.out.println("");

            this.kalenteri.tulostaMuistutukset();
            System.out.println("");

            this.kalenteri.tulostaTapahtumat();
            System.out.println("");

            this.kalenteri.tulostaTehtavat();
            System.out.println("");

            tulostaKomentoOhje();
            System.out.println("");

            System.out.print("Kirjoita komento t\u00E4h\u00E4n ja paina ENTER: ");
            String syote = this.lukija.nextLine();
            if (syote.equals("x")) {
                // Lopetetaaan ohjelma ja tallennetaan käyttäjän asettamat tapahtumat ja tehtävät.
                try {
                    FileOutputStream fos = new FileOutputStream("TapahtumaData");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(this.kalenteri.annaTapahtumaLista());
                    oos.close();
                    fos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                try {
                    FileOutputStream fos = new FileOutputStream("TehtavaData");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(this.kalenteri.annaTehtavaLista());
                    oos.close();
                    fos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                System.out.println("Mukavaa p\u00E4iv\u00E4njatkoa! :)");
                break;
            }

            kasitteleKomento(syote);

            System.out.println("");
        }


    }

    public void kasitteleKomento(String komento) {
        switch (komento) {
            case "d":
                clrscr();
                this.kalenteri.liikuOikealle();
                break;

            case "a":
                clrscr();
                this.kalenteri.liikuVasemmalle();
                break;

            case "w":
                clrscr();
                this.kalenteri.liikuYlos();
                break;

            case "s":
                clrscr();
                this.kalenteri.liikuAlas();
                break;

            case "e":
                clrscr();
                this.kalenteri.seuraavaKuukausi();
                break;

            case "q":
                clrscr();
                this.kalenteri.edellinenKuukausi();
                break;

            case "ee":
                clrscr();
                this.kalenteri.seuraavaVuosi();
                break;

            case "qq":
                clrscr();
                this.kalenteri.edellinenVuosi();
                break;

            case "p":
                clrscr();
                vaihdaPaivamaaraa();
                break;

            case "t":
                clrscr();
                this.kalenteri.nykyinenPaiva();
                break;

            case "1":
                clrscr();
                lisaaTapahtuma();
                break;

            case "2":
                clrscr();
                poistaTapahtuma();
                break;

            case "3":
                clrscr();
                lisaaTehtava();
                break;

            case "4":
                clrscr();
                poistaTehtava();
                break;

            default:
                clrscr();
                System.out.println("Komentoa ei olla viel\u00E4 ohjelmoitu tai kirjoitit siansaksaa. Yrit\u00E4 uudelleen.");
                break;
        }
    }

    private void vaihdaPaivamaaraa() {
        while (true) {
            System.out.print("Anna päivämäärä (d ohje): ");
            String pvm = this.lukija.nextLine();
            if (pvm.equals("c")) {
                break;
            }

            if (pvm.equals("d")) {
                tulostaPvmVaihtoOhje();
                continue;
            }

            String paiva = Integer.toString(this.kalenteri.annaPvm().getDayOfMonth());
            String kuukausi = Integer.toString(this.kalenteri.annaPvm().getMonth().getValue());
            String vuosi = Integer.toString(this.kalenteri.annaPvm().getYear());

            try {
                String[] osat = pvm.split("\\.");
                paiva = osat[0];
                kuukausi = osat[1];
                vuosi = osat[2];
            } catch (ArrayIndexOutOfBoundsException e) {}

            try {
                this.kalenteri.muutaPaivamaaraa(Integer.valueOf(paiva), Integer.valueOf(kuukausi), Integer.valueOf(vuosi));
                break;
            } catch (NumberFormatException e) {
                System.out.println("Anna kelvollinen luku!");
            } catch (DateTimeException e ) {
                System.out.println("Anna päivämäärä oikeassa muodossa!");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void poistaTapahtuma() {
        while (true) {
            System.out.print("Kirjoita poistettavan tapahtuman nimi (paina d poistaaksesi kaikki): ");
            String tapahtuma = lukija.nextLine();
            if (tapahtuma.equals("c")) {
                break;
            }

            if (tapahtuma.equals("d")) {
                this.kalenteri.poistaPaivanTapahtumat();
                System.out.println("Kaikki tapahtumat poistettu!");
                break;
            }

            if (this.kalenteri.poistaTapahtuma(tapahtuma)) {
                System.out.println("Tapahtuma poistettiin.");
            } else {
                System.out.println("Tapahtumaa ei ole olemassa. Tarkista kirjoitusvirheet!");
            }
            break;
        }
    }
    
    // LOPPUMISAJAN PITÄISI OLLA SUUREMPI KUIN ALKAMISAJAN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void lisaaTapahtuma() {
        while (true) {
            System.out.print("Nime\u00E4 tapahtuma: ");
            String nimi = lukija.nextLine();
            if (nimi.equals("c")) {
                break;
            }

            Tapahtuma tapahtuma = new Tapahtuma(this.kalenteri.annaPvm());
            tapahtuma.asetaNimi(nimi);

            while (true) {
                System.out.print("Asetetaan tapahtumalle alkamisaika (esim 07.45). Paina a jos tapahtuma kest\u00E4\u00E4 koko p\u00E4iv\u00E4n: ");
                String aloitus = lukija.nextLine();
                if (aloitus.equals("a")) {
                    tapahtuma.asetaAloitus(0, 0);
                    tapahtuma.asetaLopetus(0, 0);
                    break;
                }

                try {
                    String[] osatAlk = aloitus.split("\\.");
                    int tunnit = Integer.valueOf(osatAlk[0]);
                    int minuutit = Integer.valueOf(osatAlk[1]);
                    tapahtuma.asetaAloitus(tunnit, minuutit);

                    System.out.print("Asetetaan tapahtumalle lopetusaika: ");
                    String lopetus = lukija.nextLine();
                    String[] osatLop = lopetus.split("\\.");
                    tunnit = Integer.valueOf(osatLop[0]);
                    minuutit = Integer.valueOf(osatLop[1]);
                    tapahtuma.asetaLopetus(tunnit, minuutit);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Aseta kelvollinen kellonaika!");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Aseta kelvollinen kellonaika!");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            while (true) {
                System.out.print("Haluatko muistutuksen? (k/e): ");
                String vastaus = lukija.nextLine();
                if (vastaus.equals("k")) {
                    tapahtuma.asetaMuistutus(true);
                    this.kalenteri.lisaaTapahtuma(tapahtuma);
                    break;
                } else if (vastaus.equals("e")) {
                    this.kalenteri.lisaaTapahtuma(tapahtuma);
                    break;
                }
            }
        break;
        }
    }
    
    private void lisaaTehtava() {
        while (true) {
            System.out.print("Nime\u00E4 teht\u00E4v\u00E4: ");
            String nimi = lukija.nextLine();
            if (nimi.equals("c")) {
                break;
            }

            Tehtava tehtava = new Tehtava(this.kalenteri.annaPvm());
            tehtava.asetaNimi(nimi);

            while (true) {
                System.out.print("Asetetaan tehtv\u00E4lle kellonaika (esim 07.45): ");
                String aika = lukija.nextLine();
                try {
                    String[] osat = aika.split("\\.");
                    int tunnit = Integer.valueOf(osat[0]);
                    int minuutit = Integer.valueOf(osat[1]);
                    tehtava.asetaAloitus(tunnit, minuutit);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Aseta kelvollinen kellonaika!");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Aseta kelvollinen kellonaika!");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            System.out.print("Haluatko muistutuksen? (k/e): ");
            String vastaus = lukija.nextLine();
            if (vastaus.equals("k")) {
                tehtava.asetaMuistutus(true);
            }
            this.kalenteri.lisaaTehtava(tehtava);
            break;
        }
    }
    
    private void poistaTehtava() {
        while (true) {
            System.out.print("Kirjoita poistettavan teht\u00E4v\u00E4n nimi (paina d poistaaksesi kaikki): ");
            String tehtava = lukija.nextLine();
            if (tehtava.equals("c")) {
                break;
            }

            if (tehtava.equals("d")) {
                this.kalenteri.poistaPaivanTehtavat();
                System.out.println("Kaikki teht\u00E4v\u00E4t poistettu!");
                break;
            }

            if (this.kalenteri.poistaTehtava(tehtava)) {
                System.out.println("Teht\u00E4v\u00E4 poistettiin.");
            } else {
                System.out.println("Tehtav\u00E4\u00E4 ei ole olemassa. Tarkista kirjoitusvirheet!");
            }
            break;
        }
    }

    public static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) { }
    }

    public void tulostaKomentoOhje() {
        System.out.println("---- Liikkumiskomennot -----         ----------- Muut komennot -----------");
        System.out.println("  d - liiku oikealle                    1 - lis\u00E4\u00E4 tapahtuma");
        System.out.println("  a - liiku vasemmalle                  2 - poista tapahtuma");
        System.out.println("  w - liiku yl\u00F6s                        3 - lis\u00E4\u00E4 teht\u00E4v\u00E4");
        System.out.println("  s - liikus alas                       4 - poista teht\u00E4v\u00E4");
        System.out.println("  e - seuraava kuukausi                 c - mene taaksep\u00E4in");
        System.out.println("  q - edellinen kuukausi                x - lopeta");
        System.out.println("  ee - seuraava vuosi");
        System.out.println("  qq - edellinen vuosi");
        System.out.println("  p - vaihda p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4");
        System.out.println("  t - t\u00E4m\u00E4 p\u00E4iv\u00E4");

    }

    public void tulostaPvmVaihtoOhje() {
        System.out.println("Voit vaihtaa koko p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n sy\u00F6tt\u00E4m\u00E4ll\u00E4 esim. 10.12.2016");
        System.out.println("Jos haluat vaihtaa vain p\u00E4iv\u00E4\u00E4 ja kuukautta, sy\u00F6t\u00E4 esim 10.12.");
        System.out.println("Jos haluat vaihtaa vain p\u00E4iv\u00E4\u00E4, sy\u00F6t\u00E4 esim. 10");
    }
}