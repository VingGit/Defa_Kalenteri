import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Kalenteri {

    private LocalDate pvm;
    private HashMap juhlat;
    private ArrayList<Tapahtuma> tapahtumat;
    private ArrayList<Tehtava> tehtavat;

    public Kalenteri() {
        this.pvm = LocalDate.now();
        this.juhlat = new HashMap<LocalDate, String>();
        Juhlapyhat.asetaJuhlat(juhlat, pvm.getYear());

        // Yritetään hakea tallennettuja tapahtumia tiedostostosta TapahtumaData. Jos tiedostoa ei ole, luodaa uusi tyhjä lista. TapahtumaData tiedosto luodaan kun ohjelma suljetaan.
        try {
            FileInputStream fis = new FileInputStream("TapahtumaData");
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.tapahtumat = (ArrayList) ois.readObject();

            ois.close();
            fis.close();

        } catch (Exception e) {
            this.tapahtumat = new ArrayList<>();
        }

        // Yritetään hakea tallennettuja tehtäviä tiedostostosta TehtavaData. Jos tiedostoa ei ole, luodaa uusi tyhjä lista. TehtavaData tiedosto luodaan kun ohjelma suljetaan.
        try {
            FileInputStream fis = new FileInputStream("TehtavaData");
            ObjectInputStream ois = new ObjectInputStream(fis);

            this.tehtavat = (ArrayList) ois.readObject();

            ois.close();
            fis.close();

        } catch (Exception e) {
            this.tehtavat = new ArrayList<>();
        }
    }



    public LocalDate annaPvm() {
        return this.pvm;
    }


    /**___________________TAPAHTUMIIN/TEHTÄVIIN/JUHLIIN LIITTYVÄT METODIT______________________________________
     */
    
    public void lisaaTapahtuma(Tapahtuma tapahtuma) {
        this.tapahtumat.add(tapahtuma);
    }
    
    public boolean poistaTapahtuma(String poistettavanNimi) {
        return this.tapahtumat.removeIf(i -> this.pvm.equals(i.annaPvm()) && poistettavanNimi.equals(i.annaNimi()));

    }
        
    public void poistaPaivanTapahtumat() {
        this.tapahtumat.removeIf(i -> this.pvm.equals(i.annaPvm()));
    }

    public ArrayList<Tapahtuma> annaTapahtumaLista() {
        return this.tapahtumat;
    }
    
    public boolean eiTapahtumia() {
        for (Tapahtuma t : this.tapahtumat) {
            if (this.pvm.equals(t.annaPvm())) {
                return false;
            }
        }
        return true;
    }

    public void lisaaTehtava(Tehtava tehtava) {
        this.tehtavat.add(tehtava);
    }
    
    public boolean poistaTehtava(String poistettavanNimi) {
        return this.tehtavat.removeIf(i -> this.pvm.equals(i.annaPvm()) && poistettavanNimi.equals(i.annaNimi()));
    }
    
    public void poistaPaivanTehtavat() {
        this.tehtavat.removeIf(i -> this.pvm.equals(i.annaPvm()));
    }

    public ArrayList<Tehtava> annaTehtavaLista() {
        return this.tehtavat;
    }
    
    public boolean eiTehtavia() {
        for (Tehtava t : this.tehtavat) {
            if (this.pvm.equals(t.annaPvm())) {
                return false;
            }
        }
        return true;
    }

    public boolean eiJuhla() {
        Iterator it = juhlat.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if ( this.pvm.equals(pair.getKey()) ) {
                return false;
            }
        }
        return true;
    }

    public HashMap getJuhlat() {
        return this.juhlat;
    }

    public ArrayList<Tapahtuma> annaKuukaudenTapahtumat() {
        ArrayList<Tapahtuma> paivanTapahtumat = new ArrayList<>();
        for (Tapahtuma t : this.tapahtumat) {
            if (Integer.valueOf(t.annaKuukausi()) == this.pvm.getMonthValue()) {
                paivanTapahtumat.add(t);
            }
        }
        return paivanTapahtumat;
    }

    public ArrayList<Tehtava> annaKuukaudenTehtavat() {
        ArrayList<Tehtava> paivanTehtavat = new ArrayList<>();
        for (Tehtava t : this.tehtavat) {
            if (t.annaKuukausi() == this.pvm.getMonthValue()) {
                paivanTehtavat.add(t);
            }
        }
        return paivanTehtavat;
    }


    /**_______________________LIIKKUMISMETODIT___________________________________
     * Metodit muuttavat päivämäärää, sekä osa tarkistaa muuttuiko vuosi.
     * Jos vuosi mmuuttui, asetetaan juhlapäivät uudelleen.
     */

    public void liikuVasemmalle() {
        if (this.pvm.getDayOfMonth() == 1 || this.pvm.getDayOfWeek().getValue() == 1) {
            return;
        }
        this.pvm = this.pvm.minusDays(1);
    }
    
    public void liikuOikealle() {
        if (this.pvm.getDayOfMonth() == this.pvm.lengthOfMonth() || this.pvm.getDayOfWeek().getValue() == 7) {
            return;
        }
        this.pvm = this.pvm.plusDays(1);
    }

    public void liikuYlos() {
        if (this.pvm.getDayOfMonth() < 7) {
            return;
        }
        this.pvm = this.pvm.minusDays(7);
    }

    public void liikuAlas() {
        if (this.pvm.getDayOfMonth() > this.pvm.lengthOfMonth() - 7) {
            return;
        }
        this.pvm = this.pvm.plusDays(7);
    }
    
    public void seuraavaKuukausi() {
        int vuosiTallenna = this.pvm.getYear();
        this.pvm = this.pvm.plusMonths(1);
        if (vuosiTallenna != this.pvm.getYear()) {
            this.juhlat.clear();
            Juhlapyhat.asetaJuhlat(this.juhlat, this.pvm.getYear());
        }
    }
    
    public void edellinenKuukausi() {
        int vuosiTallenna = this.pvm.getYear();
        this.pvm = this.pvm.minusMonths(1);
        if (vuosiTallenna != this.pvm.getYear()) {
            this.juhlat.clear();
            Juhlapyhat.asetaJuhlat(this.juhlat, this.pvm.getYear());
        }
    }

    public void seuraavaVuosi() {
        this.pvm = this.pvm.plusYears(1);
        this.juhlat.clear();
        Juhlapyhat.asetaJuhlat(this.juhlat, this.pvm.getYear());
    }
    public void edellinenVuosi() {

        this.pvm = this.pvm.minusYears(1);
        this.juhlat.clear();
        Juhlapyhat.asetaJuhlat(this.juhlat, this.pvm.getYear());
    }

    public void muutaPaivamaaraa(int paiva, int kuukausi, int vuosi) {
        int vuosiTallenna = this.pvm.getYear();
        this.pvm = LocalDate.of(vuosi, kuukausi, paiva);
        if(vuosiTallenna != this.pvm.getYear()) {
            this.juhlat.clear();
            Juhlapyhat.asetaJuhlat(this.juhlat, this.pvm.getYear());
        }
    }

    public void nykyinenPaiva() {
        int vuosiTallenna = this.pvm.getYear();
        this.pvm = LocalDate.now();
        if (vuosiTallenna != this.pvm.getYear()) {
            this.juhlat.clear();
            Juhlapyhat.asetaJuhlat(this.juhlat, this.pvm.getYear());
        }
    }


    /**_________________________________TULOSTUSMETODIT__________________________________________________
     * Voidaan tulostaa kuukausinäkymä,
     * juhlapäivä jos kyseinen päivä on juhlapäivä,
     * päivän tapahtumat,
     * päivän tehtävät,
     * kuukauden muistutukset
     */

    public void tulostaKuukausiNakyma() {
        KalenteriNakyma.tulostaKuukausi( this.pvm.getDayOfMonth(), this.pvm.getMonthValue(), this.pvm.getYear(), this.pvm.getDayOfWeek(), this.juhlat);
    }

    public void tulostaJuhlapaiva() {
        LocalDate today = LocalDate.of(this.pvm.getYear(), this.pvm.getMonth(), this.pvm.getDayOfMonth());
        Iterator it = juhlat.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if ( today.equals(pair.getKey()) ) {

                System.out.println("   " + pair.getValue() + Varit.YELLOW);
                System.out.print(Varit.RESET);
            }
        }
    }

    public void tulostaTapahtumat() {
        System.out.println("!---------- " + this.pvm.getDayOfMonth() + ". p\u00E4iv\u00E4n tapahtumat ------------!");

        tulostaJuhlapaiva();

        if (eiTapahtumia() && eiJuhla()) {
            System.out.println("  Ei tapahtumia");
        }

        for (Tapahtuma t : this.tapahtumat) {
            if (this.pvm.equals(t.annaPvm())) {
                System.out.print(Varit.BLUE);
                System.out.println("   " + t.toString());
                System.out.print(Varit.RESET);
            }
        }
    }

    public void tulostaTehtavat() {
        System.out.println("!---------- " + this.pvm.getDayOfMonth() + ". p\u00E4iv\u00E4n teht\u00E4v\u00E4t --------------!");
        if (eiTehtavia()) {
            System.out.println("  Ei teht\u00E4vi\u00E4");
        }
        for (Tehtava t : this.tehtavat) {
            if (this.pvm.equals(t.annaPvm())) {
                System.out.println("  - " + t.toString());
            }
        }
    }

    public void tulostaMuistutukset() {
        System.out.println("!---------- " + Kuukaudet.annaKuukausi(this.pvm.getMonthValue()) + "n muistutukset ----------!");
        ArrayList<Tapahtuma> kuukaudenTapahtumat = annaKuukaudenTapahtumat();
        ArrayList<Tehtava> kuukaudenTehtavat = annaKuukaudenTehtavat();
        
        if (kuukaudenTapahtumat.isEmpty() && kuukaudenTehtavat.isEmpty()) {
            System.out.println("  Ei muistettavaa, chill :)");
            return;
        }
        
        for (Tapahtuma t : kuukaudenTapahtumat) {
            if (t.muistutusOnPaalla()) {
                System.out.println("   " + t.annaNimi() + " " + t.annaPaiva() + ". p\u00E4iv\u00E4");
            }
        }
        for (Tehtava t : kuukaudenTehtavat) {
            if (t.muistutusOnPaalla()) {
                System.out.println("   " + t.annaNimi() + " " + t.annaPaiva() + ". p\u00E4iv\u00E4");
            }
        }

    }    
}