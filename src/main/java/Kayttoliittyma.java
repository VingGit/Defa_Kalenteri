import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Kayttoliittyma {

    private  Scanner lukija;
    private  Kalenteri kalenteri;
    private boolean tulostaKomentoOhje;
    private boolean tulostaKuukausinakyma;
    public static int eteenTaakseKuukausia;
    private LocalDate pvm2;

    public Kayttoliittyma(Scanner lukija, Kalenteri kalenteri) {
        this.kalenteri = kalenteri;
        this.lukija = lukija;
        this.pvm2 = LocalDate.now();
        eteenTaakseKuukausia = pvm2.getMonthValue();

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
        while (true) {
            this.kalenteri.tulostaTervehdysJaKello();
            System.out.println("");

            if (this.tulostaKuukausinakyma) {
                this.kalenteri.tulostaKuukausiNakyma();
            } else {
                this.kalenteri.tulostaVuosiNakyma();
            }
            System.out.println("");


            this.kalenteri.tulostaMerkinnat();
            System.out.println("");

            this.kalenteri.tulostaMuistutukset();
            System.out.println("");

            if (this.tulostaKomentoOhje) {
                tulostaKomentoOhje();
                System.out.println("");
            }

            String aloitus = "  Kirjoita komento t\u00E4h\u00E4n ja paina ENTER: ";
            if (!tulostaKomentoOhje) {
                aloitus = "  Kirjoita komento t\u00E4h\u00E4n ja paina ENTER (v - n\u00E4yt\u00E4 komennot): ";
            }
            System.out.print(aloitus);

            String syote = this.lukija.nextLine();
            if (syote.equals("x")) {

                // Lopetetaaan ohjelma ja tallennetaan käyttäjän asettamat tapahtumat, tehtävät, muistutukset sekä asetus komento-ohjeiden näyttämisestä.
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
                    FileOutputStream fos = new FileOutputStream("MuistutusData");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);

                    // Tallennetaan vain ne muistutukset jotka ovat vielä tulossa. Näin estetään jo menneiden muistutusten Windows notificaation popuppaus.
                    ArrayList<Merkinta> muistutukset = new ArrayList<>();
                    for (Merkinta m : this.kalenteri.annaMuistutusLista()) {
                        if (m.muistutusAika.isAfter(LocalDateTime.now())) {
                            muistutukset.add(m);
                        }
                    }

                    oos.writeObject(muistutukset);
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

                System.out.println();
                System.out.println("  Mukavaa p\u00E4iv\u00E4njatkoa! :)");
                break;
            }

            kasitteleKomento(syote);

            System.out.println("");
        }
    }

    private void kaynnistaPaivanakyma() {
        while (true) {
            this.kalenteri.tulostaPaivaNakyma();
            System.out.println();

            tulostaPaivanakymaOhje();
            System.out.println();

            System.out.print("  Kirjoita komento t\u00E4h\u00E4n ja paina ENTER: ");
            String syote = lukija.nextLine();
            if (syote.equals("c")) {
                clrscr();
                break;
            }

            kasitteleKomentoPaivanakyma(syote);
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
                if (eteenTaakseKuukausia < 13){
                    eteenTaakseKuukausia++;
                }
                if (eteenTaakseKuukausia == 13){
                    eteenTaakseKuukausia = 1;
                }
                this.kalenteri.seuraavaKuukausi();
                break;

            case "q":
                clrscr();
                if(eteenTaakseKuukausia > 0){
                    eteenTaakseKuukausia--;
                }
                if(eteenTaakseKuukausia == 0){

                    eteenTaakseKuukausia = 12;
                }
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
                System.out.println();
                vaihdaPaivamaaraa();
                clrscr();
                break;

            case "t":
                clrscr();
                this.kalenteri.nykyinenPaiva();
                break;

            case "1":
                System.out.println();
                lisaaTapahtuma();
                clrscr();
                break;

            case "2":
                System.out.println();
                lisaaTehtava();
                clrscr();
                break;

            case "3":
                clrscr();
                kaynnistaPaivanakyma();
                break;

            case "4":
                clrscr();
                if(!this.tulostaKuukausinakyma){
                    this.tulostaKuukausinakyma = true;
                    break;
                }
                this.tulostaKuukausinakyma = false;
                break;

            case "v":
                clrscr();
                vaihdaKomentojenTulostusAsetusta();
                break;

            default:
                clrscr();
                System.out.println();
                System.out.println("  Komentoa ei ole olemassa. Yrit\u00E4 uudelleen.");
                break;
        }
    }

    public void kasitteleKomentoPaivanakyma(String komento) {
        switch (komento) {
            case "1":
                System.out.println();
                lisaaTapahtuma();
                clrscr();
                break;

            case "2":
                System.out.println();
                lisaaTehtava();
                clrscr();
                break;

            case "3":
                System.out.println();
                poistaTapahtuma();
                break;

            case "4":
                System.out.println();
                poistaTehtava();
                break;

            default:
                clrscr();
                System.out.println();
                System.out.println("  Komentoa ei ole olemassa. Yrit\u00E4 uudelleen.");
                break;
        }
    }

    private void vaihdaPaivamaaraa() {
        while (true) {
            System.out.print("  Anna p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4 (d ohje): ");
            String syote = this.lukija.nextLine();
            if (syote.equals("d")) {
                tulostaPvmVaihtoOhje();
                continue;
            }

            if (syote.matches("([1-9]|(1[0-9])|(2[0-9])|(3[0-1]))")) {
                try {
                    LocalDate pvm = LocalDate.of(this.kalenteri.annaPvm().getYear(), this.kalenteri.annaPvm().getMonth(), Integer.valueOf(syote));
                    this.kalenteri.asetaPaivamaaraa(pvm);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("  Annoit p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n v\u00E4\u00E4r\u00E4ss\u00E4 muodossa tai yritit menn\u00E4 p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4n jota ei ole olemassa.");
                } catch (DateTimeException e) {
                    System.out.println("  Annoit p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n v\u00E4\u00E4r\u00E4ss\u00E4 muodossa tai yritit menn\u00E4 p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4n jota ei ole olemassa.");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            if (syote.matches("([1-9]|(1[0-9])|(2[0-9])|(3[0-1]))\\." +
                                     "([1-9]|(1[0-2]))")) {
                String[] osat = syote.split("\\.");
                try {
                    LocalDate pvm = LocalDate.of(this.kalenteri.annaPvm().getYear(), Integer.valueOf(osat[1]), Integer.valueOf(osat[0]));
                    this.kalenteri.asetaPaivamaaraa(pvm);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("  Annoit p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n v\u00E4\u00E4r\u00E4ss\u00E4 muodossa tai yritit menn\u00E4 p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4n jota ei ole olemassa.");
                } catch (DateTimeException e) {
                    System.out.println("  Annoit p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n v\u00E4\u00E4r\u00E4ss\u00E4 muodossa tai yritit menn\u00E4 p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4n jota ei ole olemassa.");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            if (syote.matches("([1-9]|(1[0-9])|(2[0-9])|(3[0-1]))\\." +
                                     "([1-9]|(1[0-2]))\\." +
                                     "(([1-9][0-9][0-9][0-9])|([1-9][0-9][0-9])|([1-9][0-9])|([0-9]))")) {
                String[] osat = syote.split("\\.");
                try {
                    LocalDate pvm = LocalDate.of(Integer.valueOf(osat[2]), Integer.valueOf(osat[1]), Integer.valueOf(osat[0]));
                    this.kalenteri.asetaPaivamaaraa(pvm);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("  Annoit p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n v\u00E4\u00E4r\u00E4ss\u00E4 muodossa tai yritit menn\u00E4 p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4n jota ei ole olemassa.");
                } catch (DateTimeException e) {
                    System.out.println("  Annoit p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n v\u00E4\u00E4r\u00E4ss\u00E4 muodossa tai yritit menn\u00E4 p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4n jota ei ole olemassa.");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            System.out.println("  Annoit p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n v\u00E4\u00E4r\u00E4ss\u00E4 muodossa tai yritit menn\u00E4 p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4n jota ei ole olemassa.");
        }
    }

    private void lisaaTapahtuma() {
        String syote;
        System.out.print("  Nime\u00E4 tapahtuma (c - takaisin): ");
        syote = lukija.nextLine();
        if (syote.equals("c")) {
            return;
        }

        // Luodaan uusi tapahtuma syötetyllä nimellä.
        Tapahtuma tapahtuma = new Tapahtuma(syote);

        // aloitussajan asetus
        boolean suoritetaanLopetusajanAsetus = true;
        LocalDateTime aloitus = LocalDateTime.of(this.kalenteri.annaPvm().getYear(), this.kalenteri.annaPvm().getMonth().getValue(), this.kalenteri.annaPvm().getDayOfMonth(), 0, 0);
        while (true) {
            System.out.print("  Aseta tapahtuman alkamiskellonaika. Sy\u00F6t\u00E4 a jos tapahtuma kest\u00E4\u00E4 koko p\u00E4iv\u00E4n: ");
            syote = lukija.nextLine();
            if (syote.equals("a")) {
                tapahtuma.asetaAjankohta(aloitus);
                try {
                    tapahtuma.asetaLopetus(aloitus);
                } catch (LoppuEnnenAlkuaException e) { }
                suoritetaanLopetusajanAsetus  = false;
                break;
            }

            try {
                LocalTime aloitusKellonaika = syoteKellonajaksi(syote);
                aloitus = LocalDateTime.of(this.kalenteri.annaPvm().getYear(), this.kalenteri.annaPvm().getMonth().getValue(), this.kalenteri.annaPvm().getDayOfMonth(), aloitusKellonaika.getHour(), aloitusKellonaika.getMinute());
                tapahtuma.asetaAjankohta(aloitus);
                break;
            } catch (PvmAikaMuotoVaarinException e) {
                System.out.println(e);
                continue;
            }
        }

        // lopetusajan asetus
        LocalDateTime lopetus;
        while (suoritetaanLopetusajanAsetus) {
            System.out.print("  Aseta tapahtuman lopetusajankohta muodossa pp.kk.vvvv tt.mm ");
            syote = lukija.nextLine();
            try {
                lopetus = syotePaivamaaraksiJaAjaksi(syote);
                tapahtuma.asetaLopetus(lopetus);
                break;
            } catch (PvmAikaMuotoVaarinException e) {
                System.out.println("  " + e);
                continue;
            } catch (LoppuEnnenAlkuaException e) {
                System.out.println("  " + e);
                continue;
            }
        }

        // kuvauksen asetus
        while (true) {
            System.out.print("  Kirjoita tapahtumalle kuvaus (ENTER ohita): ");
            syote = this.lukija.nextLine();
            if (syote.equals("")) {
                break;
            }

            tapahtuma.asetaKuvaus(syote);
            break;
        }

        // osallistujien asetus
        while (true) {
            System.out.print("  Kirjoita osallistuvien henkil\u00F6iden nimet erotettuna pilkulla (ENTER ohita): ");
            syote = this.lukija.nextLine();
            if (syote.equals("")) {
                break;
            }

            tapahtuma.lisaaOsallistuja(syote);
            break;
        }

        // paikan asetus
        while (true) {
            System.out.print("  Kirjoita tapahtumapaikan nimi (ENTER ohita): ");
            syote = this.lukija.nextLine();
            if (syote.equals("")) {
                break;
            }

            syote = this.lukija.nextLine();
            tapahtuma.lisaaPaikka(syote);

        }

        // muistutuksen asetus
        while (true) {
            System.out.print("  Aseta muistutuksen ajankohta muodossa p.k.v tt.mm (esim. 27.5.2020 07.05). (ENTER ohita): ");
            syote = this.lukija.nextLine();
            if (syote.equals("")) {
                break;
            }

            try {
                LocalDateTime pvmAika = syotePaivamaaraksiJaAjaksi(syote);
                tapahtuma.asetaMuistutus(pvmAika);
                this.kalenteri.lisaaMuistutus(tapahtuma);
                break;
            } catch (PvmAikaMuotoVaarinException | ParseException e) {
                System.out.println(e);
                continue;
            }
        }

        // Lisätään tapahtuma kalenteriin
        this.kalenteri.lisaaTapahtuma(tapahtuma);
    }

    private void lisaaTehtava() {
        String syote;
        System.out.print("  Nime\u00E4 teht\u00E4v\u00E4 (c - takaisin): ");
        syote = lukija.nextLine();
        if (syote.equals("c")) {
            return;
        }

        Tehtava tehtava = new Tehtava(syote);

        while (true) {
            System.out.print("  Aseta kellonaika: ");
            syote = lukija.nextLine();
            try {
                LocalTime aika = syoteKellonajaksi(syote);
                tehtava.asetaAjankohta(this.kalenteri.annaPvm().getYear(), this.kalenteri.annaPvm().getMonthValue(), this.kalenteri.annaPvm().getDayOfMonth(), aika.getHour(), aika.getMinute());
                break;
            } catch (PvmAikaMuotoVaarinException e) {
                System.out.println(e + "Anna kellonaika muodossa tt.mm");
                continue;
            }
        }

        while (true) {
            System.out.print("  Kirjoita muistiinpanot (ENTER ohita): ");
            syote = lukija.nextLine();
            if (syote.equals("")) {
                break;
            }

            tehtava.asetaMuistiinpano(syote);
            break;
        }

        while (true) {
            System.out.print("  Aseta muistutuksen ajankohta muodossa p.k.v tt.mm (ENTER ohita): ");
            syote = lukija.nextLine();
            if (syote.equals("")) {
                break;
            }

            try {
                LocalDateTime pvmAika = syotePaivamaaraksiJaAjaksi(syote);
                tehtava.asetaMuistutus(pvmAika);
                this.kalenteri.lisaaMuistutus(tehtava);
                break;
            } catch (PvmAikaMuotoVaarinException e) {
                System.out.println(e);
                continue;
            } catch (ParseException e) {
                System.out.println(e);
            }
        }

        this.kalenteri.lisaaTehtava(tehtava);
    }

    private void poistaTapahtuma() {
        while (true) {
            System.out.print("  Kirjoita poistettavan tapahtuman nimi. Sy\u00F6t\u00E4 d poistaaksesi kaikki (c - takaisin): ");
            String tapahtuma = lukija.nextLine();
            if (tapahtuma.equals("c")) {
                clrscr();
                break;
            }

            if (tapahtuma.equals("d")) {
                this.kalenteri.poistaPaivanTapahtumat();
                clrscr();
                System.out.println();
                System.out.println("  Kaikki tapahtumat poistettu!");
                break;
            }

            if (this.kalenteri.poistaTapahtuma(tapahtuma)) {
                clrscr();
                System.out.println();
                System.out.println("  Tapahtuma poistettiin.");
            } else {
                clrscr();
                System.out.println();
                System.out.println("  Tapahtumaa ei ole olemassa. Tarkista kirjoitusvirheet!");
            }
            break;
        }
    }

    private void poistaTehtava() {
        while (true) {
            System.out.print("  Kirjoita poistettavan teht\u00E4v\u00E4n nimi. Sy\u00F6t\u00E4 d poistaaksesi kaikki (c - takaisin): ");
            String tehtava = lukija.nextLine();
            if (tehtava.equals("c")) {
                clrscr();
                break;
            }

            if (tehtava.equals("d")) {
                this.kalenteri.poistaPaivanTehtavat();
                clrscr();
                System.out.println();
                System.out.println("  Kaikki teht\u00E4v\u00E4t poistettu!");
                break;
            }

            if (this.kalenteri.poistaTehtava(tehtava)) {
                clrscr();
                System.out.println();
                System.out.println("  Teht\u00E4v\u00E4 poistettiin.");
            } else {
                clrscr();
                System.out.println();
                System.out.println("  Tehtav\u00E4\u00E4 ei ole olemassa. Tarkista kirjoitusvirheet!");
            }
            break;
        }
    }

    private LocalDateTime syotePaivamaaraksiJaAjaksi(String syote) throws PvmAikaMuotoVaarinException {
        if (!syote.matches("([1-9]|(1[0-9])|(2[0-9])|(3[0-1]))\\." +                       // päivä
                "([1-9]|(1[0-2]))\\." +                                                           // kuukausi
                "(([1-9][0-9][0-9][0-9]\\s)|([1-9][0-9][0-9]\\s)|([1-9][0-9]\\s)|([0-9]\\s))" +   // vuosi
                "(((0|1)[0-9])|(2[0-3]))\\.[0-5][0-9]")) {                                        // kellonaika

            throw new PvmAikaMuotoVaarinException();
        }

        String[] pvmJaKellonaika = syote.split(" ");

        return LocalDateTime.of(syotePaivamaaraksi(pvmJaKellonaika[0]), syoteKellonajaksi(pvmJaKellonaika[1]));
    }

    private LocalDate syotePaivamaaraksi(String syote) throws PvmAikaMuotoVaarinException {
        if (!syote.matches("([1-9]|(1[0-9])|(2[0-9])|(3[0-1]))\\." +                       // päivä
                "([1-9]|(1[0-2]))\\." +                                                           // kuukausi
                "(([1-9][0-9][0-9][0-9])|([1-9][0-9][0-9])|([1-9][0-9])|([0-9]))")) {              // vuosi

            throw new PvmAikaMuotoVaarinException();
        }

        String[] pvmOsat = syote.split("\\.");

        return LocalDate.of(Integer.valueOf(pvmOsat[2]), Integer.valueOf(pvmOsat[1]), Integer.valueOf(pvmOsat[0]));
    }

    private LocalTime syoteKellonajaksi(String syote) throws PvmAikaMuotoVaarinException {
        if (!syote.matches("(((0|1)[0-9])|(2[0-3]))\\.[0-5][0-9]")) {
            throw new PvmAikaMuotoVaarinException();
        }

        String[] kelloOsat = syote.split("\\.");

        return LocalTime.of(Integer.valueOf(kelloOsat[0]), Integer.valueOf(kelloOsat[1]));
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
        String kuukausiTaiVuosiNakyma = "4 - n\u00E4yt\u00E4 vuosin\u00E4kym\u00E4";
        if (!this.tulostaKuukausinakyma) {
            kuukausiTaiVuosiNakyma = "4 - n\u00E4yt\u00E4 kuukausin\u00E4kym\u00E4";
        }

        System.out.println("  ---- Liikkumiskomennot -----         ----------- Muut komennot -----------");
        System.out.println("   d - liiku oikealle                    1 - lis\u00E4\u00E4 tapahtuma");
        System.out.println("   a - liiku vasemmalle                  2 - lis\u00E4\u00E4 teht\u00E4v\u00E4");
        System.out.println("   w - liiku yl\u00F6s                        3 - tarkastele p\u00E4iv\u00E4\u00E4");
        System.out.println("   s - liikus alas" + "                       " + kuukausiTaiVuosiNakyma);
        System.out.println("   e - seuraava kuukausi                 x - lopeta");
        System.out.println("   q - edellinen kuukausi                v - piilota komennot");
        System.out.println("   ee - seuraava vuosi");
        System.out.println("   qq - edellinen vuosi");
        System.out.println("   p - vaihda p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4\u00E4");
        System.out.println("   t - t\u00E4m\u00E4 p\u00E4iv\u00E4");

    }

    public void tulostaPvmVaihtoOhje() {
        System.out.println("  Voit vaihtaa koko p\u00E4iv\u00E4m\u00E4\u00E4r\u00E4n sy\u00F6tt\u00E4m\u00E4ll\u00E4 esim. 10.12.2016");
        System.out.println("  Jos haluat vaihtaa vain p\u00E4iv\u00E4\u00E4 ja kuukautta, sy\u00F6t\u00E4 esim 10.12");
        System.out.println("  Jos haluat vaihtaa vain p\u00E4iv\u00E4\u00E4, sy\u00F6t\u00E4 esim. 10");
    }

    public void tulostaPaivanakymaOhje() {
        System.out.println("  ------- Komennot ---------");
        System.out.println("  1 - lis\u00E4\u00E4 tapahtuma");
        System.out.println("  2 - lis\u00E4\u00E4 teht\u00E4v\u00E4");
        System.out.println("  3 - poista tapahtuma");
        System.out.println("  4 - poista teht\u00E4v\u00E4");
        System.out.println("  c - takaisin kuukausin\u00E4kym\u00E4\u00E4n");

    }

}