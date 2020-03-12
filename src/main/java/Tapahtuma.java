import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Tapahtuma extends Merkinta {

    private LocalDateTime loppu;
    private  String kuvaus;
    private String paikka;
    private  ArrayList<String> osallistujat;

    public Tapahtuma() {
        super();
    }

    public Tapahtuma(String nimi) {
        super(nimi);
        this.osallistujat = new ArrayList<>();
    }



    public void asetaLopetus(int vuosi, int kuukausi, int paiva, int tunnit, int minuutit) {
        this.loppu = LocalDateTime.of(LocalDate.of(vuosi, kuukausi, paiva), LocalTime.of(tunnit, minuutit));
    }

    public void asetaLopetus(LocalDate pvm, LocalTime aika) {
        this.loppu = LocalDateTime.of(pvm, aika);
    }

    public void asetaLopetus(LocalDateTime pvmAika) {
        this.loppu = pvmAika;
    }

    public LocalDateTime annaLopetus() {
        return this.loppu;
    }

    public void asetaKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public void poistaKuvaus() {
        this.kuvaus = "";
    }

    public void lisaaPaikka(String paikka) {
        this.paikka = paikka;
    }

    public String annaPaikka() {
        return this.paikka;
    }

    public void lisaaOsallistuja(String osallistuja) {
        this.osallistujat.add(osallistuja);
    }

    public void poistaOsallistuja(String osallistuja) {
        this.osallistujat.remove(osallistuja);
    }

    public void poistaKaikkiOsallistujat() {
        this.osallistujat.clear();
    }

    public boolean ulottuukoToiseenPaivaan() {
        if (this.alku.getDayOfMonth() != this.loppu.getDayOfMonth()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M. HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");

        String aikaVali;
        if (ulottuukoToiseenPaivaan()) {
            aikaVali = this.alku.format(formatter) + " - " + this.loppu.format(formatter);
        } else {
            aikaVali = this.alku.format(formatter2) + " - " + this.loppu.format(formatter2);

        }
        if (this.alku.isEqual(this.loppu)) {
            aikaVali = "Koko päivän";
        }


        String muistutus;
        if (this.muistutus != null) {
            muistutus = "Muistutetaan " + this.muistutus.format(formatter2);
        } else {
            muistutus = "Ei muistutusta";
        }

        String paikka = this.paikka;
        if (paikka == null) {
            paikka = "-";
        }

        String osallistujat = this.osallistujat.toString();
        if (osallistujat == null) {
            osallistujat = "-";
        } else {
            osallistujat = osallistujat.replace("[", "");
            osallistujat = osallistujat.replace("]", "");
        }

        String kuvaus = this.kuvaus;
        if (kuvaus == null) {
            kuvaus = "-";
        }

        return this.nimi + ", " + aikaVali + ", " + muistutus + "\n  " + "Paikka: " + paikka + "\n  Osallistujat: " + osallistujat + "\n  Kuvaus: " + kuvaus;
    }

    public String toStringLyhyt() {
        DateTimeFormatter formatter;
        if (ulottuukoToiseenPaivaan()) {
            formatter = DateTimeFormatter.ofPattern("d.M. HH:mm");
        } else {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }

        String aikaVali = this.alku.format(formatter) + " - " + this.loppu.format(formatter);

        if (this.alku.isEqual(this.loppu)) {
            aikaVali = "Koko päivän";
        }

        return this.nimi + ", " + aikaVali;
    }
}