import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kayttoliittyma implements NativeKeyListener {

    private  Scanner lukija;
    private  Kalenteri kalenteri;
    private boolean tulostaKomentoOhje;
    private boolean tulostaKuukausinakyma;
    
    public Kayttoliittyma(Scanner lukija, Kalenteri kalenteri) {
        this.kalenteri = kalenteri;
        this.lukija = lukija;

        // Yritetään hakea tallennettua asetusta ohjeen tulostamisesta.
        try {
            Scanner tiedostonLukija = new Scanner(Paths.get("OhjeAsetus.txt"));
            while (tiedostonLukija.hasNextLine()) {
                this.tulostaKomentoOhje = Boolean.valueOf(tiedostonLukija.nextLine());
            }
        } catch (NoSuchFileException e) {
            this.tulostaKomentoOhje = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.tulostaKuukausinakyma = true;
    }



    public void kaynnista() {
        //while (true) {

            this.kalenteri.tulostaTervehdysJaKello();
            System.out.println("");

            if (this.tulostaKuukausinakyma) {
                this.kalenteri.tulostaKuukausiNakyma();
                System.out.println("");
            } else {
                this.kalenteri.tulostaVuosiNakyma();
                System.out.println("");
            }

            this.kalenteri.tulostaMerkinnat();
            System.out.println("");

            this.kalenteri.tulostaMuistutukset();
            System.out.println("");

            if (this.tulostaKomentoOhje) {
                tulostaKomentoOhje();
                System.out.println("");
            }

            String aloitus = "  Paina kirjainta tai numeroa ";
            if (!tulostaKomentoOhje) {
                aloitus = "  Paina kirjainta tai numeroa (v - n\u00E4yt\u00E4 komennot)";
            }
            System.out.print(aloitus);
            /*
            String syote = this.lukija.nextLine();
            if (syote.equals("x")) {
                // Lopetetaaan ohjelma ja tallennetaan käyttäjän asettamat tapahtumat, tehtävät sekä asetus komento-ohjeiden näyttämisestä.
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

                try {
                    PrintWriter writer = new PrintWriter("OhjeAsetus.txt", "UTF-8");
                    writer.println(this.tulostaKomentoOhje);
                    writer.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                System.out.println("  Mukavaa p\u00E4iv\u00E4njatkoa! :)");
                break;
            }
*/
            //kasitteleKomento(syote);

            System.out.println("");
        //}
    }

    private void kaynnistaPaivanakyma() {
        while (true) {
            this.kalenteri.tulostaPaivaNakyma();
            System.out.println();

            tulostaPaivanakymaOhje();
            System.out.println();

            System.out.print("  Sy\u00F6t\u00E4 komento: ");
            String syote = lukija.nextLine();
            if (syote.equals("c")) {
                clrscr();
                break;
            }

            kasitteleKomentoPaivanakyma(syote);
        }

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        kasitteleKomento(NativeKeyEvent.getKeyText(e.getKeyCode()));


        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void kasitteleKomento(String komento) {
        switch (komento) {
            case "D":
                clrscr();
                GlobalScreen.removeNativeKeyListener(this);
                this.kalenteri.liikuOikealle();
                GlobalScreen.addNativeKeyListener(this);
                kaynnista();
                break;

            case "A":
                clrscr();
                this.kalenteri.liikuVasemmalle();
                kaynnista();
                break;

            case "W":
                clrscr();
                this.kalenteri.liikuYlos();
                kaynnista();
                break;

            case "S":
                clrscr();
                this.kalenteri.liikuAlas();
                kaynnista();
                break;

            case "E":
                clrscr();
                this.kalenteri.seuraavaKuukausi();
                kaynnista();
                break;

            case "Q":
                clrscr();
                this.kalenteri.edellinenKuukausi();
                kaynnista();
                break;

            case "R":
                clrscr();
                this.kalenteri.seuraavaVuosi();
                kaynnista();
                break;

            case "F":
                clrscr();
                this.kalenteri.edellinenVuosi();
                kaynnista();
                break;

            case "P":
                clrscr();
                GlobalScreen.removeNativeKeyListener(this);
                vaihdaPaivamaaraa();
                GlobalScreen.addNativeKeyListener(this);
                kaynnista();
                break;

            case "T":
                clrscr();
                this.kalenteri.nykyinenPaiva();
                kaynnista();
                break;

            case "1":
                clrscr();
                lisaaTapahtuma();
                break;

            case "2":
                clrscr();
                lisaaTehtava();
                break;

            case "3":
                clrscr();
                kaynnistaPaivanakyma();
                break;

            case "4":
                clrscr();
                this.tulostaKuukausinakyma = false;
                this.kalenteri.tulostaVuosiNakyma();
                break;

            case "V":
                clrscr();
                vaihdaKomentojenTulostusAsetusta();
                kaynnista();
                break;

            case "C":
                clrscr();
                System.out.println("  Et voi menn\u00E4 t\u00E4st\u00E4 n\u00E4kym\u00E4st\u00E4 taaksep\u00E4in.");
                kaynnista();
                break;

            case "X":
                clrscr();

                break;

            default:
                clrscr();
                System.out.println("  Komentoa ei ole olemassa. Yrit\u00E4 uudelleen.");
                kaynnista();
                break;
        }
    }

    public void kasitteleKomentoPaivanakyma(String komento) {
        switch (komento) {
            case "1":
                clrscr();
                lisaaTapahtuma();
                break;

            case "2":
                clrscr();
                lisaaTehtava();
                break;

            case "3":
                clrscr();
                poistaTapahtuma();
                break;

            case "4":
                clrscr();
                poistaTehtava();
                break;

            default:
                clrscr();
                System.out.println("  Komentoa ei ole olemassa. Yrit\u00E4 uudelleen.");
                break;
        }
    }

    private void vaihdaPaivamaaraa() {
        //while (true) {
            System.out.print("  Anna p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4 (d ohje): ");
            String syote = this.lukija.nextLine();
            if (syote.equals("c")) {
                //break;
            }

            if (syote.equals("d")) {
                tulostaPvmVaihtoOhje();
                //continue;
            }

            LocalDate pvm = syotePaivamaaraksi(syote);
            this.kalenteri.asetaPaivamaaraa(pvm);

        //}
    }

    private void lisaaTapahtuma() {
        String syote;
        while (true) {
            System.out.print("  Nime\u00E4 tapahtuma: ");
            syote = lukija.nextLine();
            if (syote.equals("c")) {
                break;
            }

            // Luodaan uusi tapahtuma syötetyllä nimellä.
            Tapahtuma tapahtuma = new Tapahtuma(syote);

            // aloitussajan asetus
            LocalDateTime aloitus = LocalDateTime.of(this.kalenteri.annaPvm().getYear(), this.kalenteri.annaPvm().getMonth().getValue(), this.kalenteri.annaPvm().getDayOfMonth(), 0, 0);
            while (true) {
                System.out.print("  Aseta alkamisaika. Sy\u00F6t\u00E4 a jos tapahtuma kest\u00E4\u00E4 koko p\u00E4iv\u00E4n: ");
                syote = lukija.nextLine();
                if (syote.equals("a")) {
                    tapahtuma.asetaAloitus(aloitus);
                    tapahtuma.asetaLopetus(aloitus);
                    break;
                }

                LocalTime aloitusKellonaika = syoteKellonajaksi(syote);

                aloitus = LocalDateTime.of(this.kalenteri.annaPvm().getYear(), this.kalenteri.annaPvm().getMonth().getValue(), this.kalenteri.annaPvm().getDayOfMonth(), aloitusKellonaika.getHour(), aloitusKellonaika.getMinute());

                tapahtuma.asetaAloitus(aloitus);

                // lopetusajan asetus
                LocalDateTime lopetus;
                while (true) {
                    System.out.print("  Kest\u00E4\u00E4k\u00F6 tapahtuma nykyisen p\u00E4iv\u00E4n yli? (k/e): ");
                    syote = lukija.nextLine();
                    if (syote.equals("k")) {
                        System.out.print("  Anna lopetuspäivä: ");
                        syote = lukija.nextLine();
                        LocalDate lopetusPvm = syotePaivamaaraksi(syote);

                        System.out.print("  Anna lopetusaika: ");
                        syote = lukija.nextLine();
                        LocalTime lopetusAika = syoteKellonajaksi(syote);

                        lopetus = LocalDateTime.of(lopetusPvm, lopetusAika);

                        if (lopetus.isBefore(aloitus)) {
                            System.out.println("  Lopetusaika on ennen aloitusaikaa! Syötö kelvollinen lopetusaika!");
                            continue;
                        }

                        tapahtuma.asetaLopetus(lopetus);
                        break;

                    } else if (syote.equals("e")) {
                        System.out.print("  Anna lopetusaika: ");
                        syote = lukija.nextLine();
                        LocalTime lopetusAika = syoteKellonajaksi(syote);

                        LocalDate lopetusPvm = LocalDate.of(this.kalenteri.annaPvm().getYear(), this.kalenteri.annaPvm().getMonth().getValue(), this.kalenteri.annaPvm().getDayOfMonth());
                        lopetus = LocalDateTime.of(lopetusPvm, lopetusAika);

                        tapahtuma.asetaLopetus(lopetus);
                        break;
                    }
                }
                break;
            }

            // kuvauksen asetus
            while (true) {
                System.out.print("  Haluatko asettaa tapahtumalle kuvauksen? (k/e): ");
                syote = this.lukija.nextLine();
                if (syote.equals("k")) {
                    System.out.print("  Kirjoita kuvaus: ");
                    syote = this.lukija.nextLine();
                    tapahtuma.asetaKuvaus(syote);
                    break;
                } else if (syote.equals("e")) {
                    break;
                }
            }

            // osallistujien asetus
            boolean flag= true;
            while (flag) {
                System.out.print("  Haluatko lis\u00E4t\u00E4 osallistuvat henkil\u00F6t? (k/e): ");
                syote = this.lukija.nextLine();
                if (syote.equals("k")) {
                    while (true) {
                        System.out.print("  Anna osallistujan nimi (e lopeta): ");
                        syote = this.lukija.nextLine();
                        if (syote.equals("e")) {
                            flag = false;
                            break;
                        }

                        tapahtuma.lisaaOsallistuja(syote);
                    }
                } else if (syote.equals("e")) {
                    break;
                }
            }

            // paikan asetus
            while (true) {
                System.out.print("  Haluatko lis\u00E4t\u00E4 paikan? (k/e): ");
                syote = this.lukija.nextLine();
                if (syote.equals("k")) {
                    System.out.print("  Sy\u00F6t\u00E4 paikan nimi: ");
                    syote = this.lukija.nextLine();
                    tapahtuma.lisaaPaikka(syote);
                    break;

                } else if (syote.equals("e")) {
                    break;
                }
            }

            // muistutuksen asetus
            while (true) {
                System.out.print("  Haluatko muistutuksen? (k/e): ");
                String vastaus = this.lukija.nextLine();
                if (vastaus.equals("k")) {
                    System.out.print("  Milloin haluat muistutuksen? Asetetaan ensin p\u00E4iv\u00E4: ");
                    syote = this.lukija.nextLine();
                    LocalDate pvm = syotePaivamaaraksi(syote);

                    System.out.print("  Seuraavaksi kellonaika: ");
                    syote = this.lukija.nextLine();
                    LocalTime aika = syoteKellonajaksi(syote);

                    tapahtuma.asetaMuistutus(LocalDateTime.of(pvm,aika));
                    break;

                } else if (vastaus.equals("e")) {
                    break;
                }
            }

            this.kalenteri.lisaaTapahtuma(tapahtuma);
            break;
        }
    }

    private void lisaaTehtava() {
        String syote;
        while (true) {
            System.out.print("  Nime\u00E4 teht\u00E4v\u00E4: ");
            syote = lukija.nextLine();
            if (syote.equals("c")) {
                break;
            }

            Tehtava tehtava = new Tehtava(syote);

            System.out.print("  Aseta kellonaika: ");
            syote = lukija.nextLine();
            LocalTime aika = syoteKellonajaksi(syote);
            tehtava.asetaAloitus(this.kalenteri.annaPvm().getYear(), this.kalenteri.annaPvm().getMonthValue(), this.kalenteri.annaPvm().getDayOfMonth(), aika.getHour(), aika.getMinute());

            while (true) {
                System.out.print("  Haluatko asettaa muistiinpanoja (k/e): ");
                String vastaus = lukija.nextLine();
                if (vastaus.equals("k")) {
                    System.out.print("  Kirjoita muistiinpanot: ");
                    String muistiinpanot = lukija.nextLine();
                    tehtava.asetaMuistiinpano(muistiinpanot);
                    break;
                } else if (vastaus.equals("e")) {
                    break;
                }
            }

            while (true) {
                System.out.print("  Haluatko muistutuksen? (k/e): ");
                String vastaus = this.lukija.nextLine();
                if (vastaus.equals("k")) {
                    System.out.print("  Milloin haluat muistutuksen? Asetetaan ensin p\u00E4iv\u00E4: ");
                    syote = this.lukija.nextLine();
                    LocalDate pvm = syotePaivamaaraksi(syote);

                    System.out.print("  Seuraavaksi kellonaika: ");
                    syote = this.lukija.nextLine();
                    aika = syoteKellonajaksi(syote);

                    tehtava.asetaMuistutus(LocalDateTime.of(pvm,aika));
                    break;

                } else if (vastaus.equals("e")) {
                    break;
                }
            }

            this.kalenteri.lisaaTehtava(tehtava);
            break;
        }
    }

    private void poistaTapahtuma() {
        while (true) {
            System.out.print("  Kirjoita poistettavan tapahtuman nimi (sy\u00F6t\u00E4 d poistaaksesi kaikki): ");
            String tapahtuma = lukija.nextLine();
            if (tapahtuma.equals("c")) {
                break;
            }

            if (tapahtuma.equals("d")) {
                this.kalenteri.poistaPaivanTapahtumat();
                System.out.println("  Kaikki tapahtumat poistettu!");
                break;
            }

            if (this.kalenteri.poistaTapahtuma(tapahtuma)) {
                System.out.println("  Tapahtuma poistettiin.");
            } else {
                System.out.println("  Tapahtumaa ei ole olemassa. Tarkista kirjoitusvirheet!");
            }
            break;
        }
    }

    private void poistaTehtava() {
        while (true) {
            System.out.print("  Kirjoita poistettavan teht\u00E4v\u00E4n nimi (syötä d poistaaksesi kaikki): ");
            String tehtava = lukija.nextLine();
            if (tehtava.equals("c")) {
                break;
            }

            if (tehtava.equals("d")) {
                this.kalenteri.poistaPaivanTehtavat();
                System.out.println("  Kaikki teht\u00E4v\u00E4t poistettu!");
                break;
            }

            if (this.kalenteri.poistaTehtava(tehtava)) {
                System.out.println("  Teht\u00E4v\u00E4 poistettiin.");
            } else {
                System.out.println("  Tehtav\u00E4\u00E4 ei ole olemassa. Tarkista kirjoitusvirheet!");
            }
            break;
        }
    }

    private LocalTime syoteKellonajaksi(String syote) {
        LocalTime aika = null;
        boolean flag = true;
        while (flag) {
            try {
                String[] osat = syote.split("\\.");
                int tunnit = Integer.valueOf(osat[0]);
                int minuutit = Integer.valueOf(osat[1]);
                aika = LocalTime.of(tunnit, minuutit);
                flag = false;

            } catch (NumberFormatException e) {
                System.out.print("  Aseta kelvollinen kellonaika (esim 7.45): ");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("  Aseta kelvollinen kellonaika (esim 7.45): ");
            } catch (DateTimeException e) {
                System.out.print("  Aseta kelvollinen kellonaika (esim 7.45): ");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (flag) {
                    syote = this.lukija.nextLine();
                }
            }
        }
        return aika;
    }

    private LocalDate syotePaivamaaraksi(String syote) {
        LocalDate pvm = null;
        boolean flag = true;
        while (flag) {
            try {
                String[] osat = syote.split("\\.");
                int paiva = Integer.valueOf(osat[0]);
                int kuukausi = Integer.valueOf(osat[1]);
                int vuosi = Integer.valueOf(osat[2]);
                pvm = LocalDate.of(vuosi, kuukausi, paiva);
                flag = false;

            } catch (NumberFormatException e) {
                System.out.print("  Aseta kelvollinen p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4 (esim 4.10.2021): ");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.print("  Aseta kelvollinen p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4 (esim 4.10.2021): ");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (flag) {
                    syote = this.lukija.nextLine();
                }
            }
        }
        return pvm;
    }

    public static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) { }
    }

    public void vaihdaKomentojenTulostusAsetusta() {
        if (this.tulostaKomentoOhje == true) {
            this.tulostaKomentoOhje = false;
        } else if (this.tulostaKomentoOhje == false) {
            this.tulostaKomentoOhje = true;
        }
    }

    public void tulostaKomentoOhje() {
        System.out.println("  ---- Liikkumiskomennot -----         ----------- Muut komennot -----------");
        System.out.println("   d - liiku oikealle                    1 - lis\u00E4\u00E4 tapahtuma");
        System.out.println("   a - liiku vasemmalle                  2 - lis\u00E4\u00E4 teht\u00E4v\u00E4");
        System.out.println("   w - liiku yl\u00F6s                        3 - tarkastele p\u00E4iv\u00E4\u00E4");
        System.out.println("   s - liikus alas                       4 - n\u00E4yt\u00E4 vuosin\u00E4kym\u00E4 - KESKEN");
        System.out.println("   e - seuraava kuukausi                 c - mene taaksep\u00E4in");
        System.out.println("   q - edellinen kuukausi                x - lopeta");
        System.out.println("   r - seuraava vuosi                    v - piilota komennot");
        System.out.println("   f - edellinen vuosi");
        System.out.println("   p - vaihda p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4");
        System.out.println("   t - t\u00E4m\u00E4 p\u00E4iv\u00E4");

    }

    public void tulostaPvmVaihtoOhje() {
        System.out.println("  Voit vaihtaa koko p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n sy\u00F6tt\u00E4m\u00E4ll\u00E4 esim. 10.12.2016");
        System.out.println("  Jos haluat vaihtaa vain p\u00E4iv\u00E4\u00E4 ja kuukautta, sy\u00F6t\u00E4 esim 10.12.");
        System.out.println("  Jos haluat vaihtaa vain p\u00E4iv\u00E4\u00E4, sy\u00F6t\u00E4 esim. 10");
    }

    public void tulostaPaivanakymaOhje() {
        System.out.println("  ------- Komennot ---------");
        System.out.println("  1 - lis\u00E4\u00E4 tapahtuma");
        System.out.println("  2 - lis\u00E4\u00E4 teht\u00E4v\u00E4");
        System.out.println("  3 - poista tapahtuma");
        System.out.println("  4 - poista tehtava");
        System.out.println("  c - takaisin kuukausin\u00E4kym\u00E4\u00E4n");
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}