import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Tapahtuma extends Merkinta {

    private LocalDateTime lopetusAjankohta;
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
        this.lopetusAjankohta = LocalDateTime.of(LocalDate.of(vuosi, kuukausi, paiva), LocalTime.of(tunnit, minuutit));
    }

    public void asetaLopetus(LocalDate pvm, LocalTime aika) {
        this.lopetusAjankohta = LocalDateTime.of(pvm, aika);
    }

    public void asetaLopetus(LocalDateTime pvmAika) {
        this.lopetusAjankohta = pvmAika;
    }

    public LocalDateTime annaLopetus() {
        return this.lopetusAjankohta;
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
        if (this.ajankohta.getDayOfMonth() != this.lopetusAjankohta.getDayOfMonth()) {
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
            aikaVali = this.ajankohta.format(formatter) + " - " + this.lopetusAjankohta.format(formatter);
        } else {
            aikaVali = this.ajankohta.format(formatter2) + " - " + this.lopetusAjankohta.format(formatter2);

        }
        if (this.ajankohta.isEqual(this.lopetusAjankohta)) {
            aikaVali = "Koko p\u00E4iv\u00E4n";
        }

        String paikka = this.paikka;
        if (paikka == null) {
            paikka = "-";
        }

        String osallistujat = this.osallistujat.toString();
        if (this.osallistujat == null || this.osallistujat.isEmpty()) {
            osallistujat = "-";
        } else {
            osallistujat = osallistujat.replace("[", "");
            osallistujat = osallistujat.replace("]", "");
        }

        String kuvaus = this.kuvaus;
        if (kuvaus == null) {
            kuvaus = "-";
        }

        String muistutus;
        if (this.muistutusAika != null) {
            if (ulottuukoToiseenPaivaan()) {
                muistutus = "Muistutetaan " + this.muistutusAika.format(formatter);
            } else {
                muistutus = "Muistutetaan " + this.muistutusAika.format(formatter2);
            }
        } else {
            muistutus = "Ei p\u00E4\u00E4ll\u00E4";
        }

        return this.nimi + "\n  Ajankohta: " + aikaVali + "\n  " + "Paikka: " + paikka + "\n  Osallistujat: " + osallistujat + "\n  Kuvaus: " + kuvaus + "\n  Muistutus: " + muistutus;
    }

    public String toStringLyhyt() {
        DateTimeFormatter formatter;
        if (ulottuukoToiseenPaivaan()) {
            formatter = DateTimeFormatter.ofPattern("d.M. HH:mm");
        } else {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        }

        String aikaVali = this.ajankohta.format(formatter) + " - " + this.lopetusAjankohta.format(formatter);

        if (this.ajankohta.isEqual(this.lopetusAjankohta)) {
            aikaVali = "Koko p\u00E4iv\u00E4n";
        }

        return this.nimi + ", " + aikaVali;
    }
}