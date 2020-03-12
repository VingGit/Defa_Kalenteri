import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Merkinta implements Serializable {

    protected String nimi;
    protected LocalDateTime alku;
    protected LocalDateTime muistutus;

    public Merkinta() {}

    public Merkinta(String nimi) {
        this.nimi = nimi;
    }



    public void asetaNimi(String nimi) {
        this.nimi = nimi;
    }

    public String annaNimi() {
        return this.nimi;
    }

    public void asetaAloitus(int vuosi, int kuukausi, int paiva, int tunnit, int minuutit) {
        this.alku = LocalDateTime.of(LocalDate.of(vuosi, kuukausi, paiva), LocalTime.of(tunnit, minuutit));
    }

    public void asetaAloitus(LocalDate pvm, LocalTime aika) {
        this.alku = LocalDateTime.of(pvm, aika);
    }

    public void asetaAloitus(LocalDateTime pvmAika) {
        this.alku = pvmAika;
    }

    public LocalDateTime annaAloitus() {
        return this.alku;
    }

    public void asetaMuistutus(LocalDateTime muistutus) {
        this.muistutus = muistutus;
    }

    public void asetaMuistutus(int vuosi, int kuukausi, int paiva, int tunnit, int minuutit) {
        this.muistutus = LocalDateTime.of(LocalDate.of(vuosi, kuukausi, paiva), LocalTime.of(tunnit, minuutit));
    }

    public void poistaMuistutus() {
        this.muistutus = null;
    }

    public boolean onkoMuistutus() {
        if (this.muistutus == null) {
            return false;
        }
        return true;
    }
}