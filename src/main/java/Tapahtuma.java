import java.io.Serializable;
import java.time.LocalDate;

public class Tapahtuma implements Serializable {
    
    protected String nimi;
    protected final LocalDate pvm;
    protected Kellonaika alku;
    private Kellonaika loppu;
    protected boolean muistutus;
    
    public Tapahtuma(LocalDate pvm) {
        this.pvm = pvm;
    }

    
    
    public void asetaNimi(String nimi) {
        this.nimi = nimi;
    }
    
    public void asetaAloitus(int tunnit, int minuutit) {
        this.alku = new Kellonaika(tunnit, minuutit);
    }
    
    public void asetaLopetus(int tunnit, int minuutit) {
        this.loppu = new Kellonaika(tunnit, minuutit);
    }
    
    public void asetaMuistutus(boolean muistutus) {
        this.muistutus = muistutus;
    }
    
    public String annaNimi() {
        return this.nimi;
    }
    
    public LocalDate annaPvm() {
        return this.pvm;
    }
    
    public int annaPaiva() {
        return this.pvm.getDayOfMonth();
    }
    
    public int annaKuukausi() {
        return this.pvm.getMonth().getValue();
    }
    
    public String annaAloitus() {
        return this.alku.toString();
    }
    
    public String annaLopetus() {
        return this.loppu.toString();
    }
    
    public boolean muistutusOnPaalla() {
        return this.muistutus;
    }

    @Override
    public String toString() {
        String muistutus = "Ei muistutusta";
        if (this.muistutus) {
            muistutus = "Muistutus on päällä";
        }
        
        String aikaVali = "klo " + this.alku.toString() + " - " + this.loppu.toString();
        if (this.alku.toString().equals(loppu.toString())) {
            aikaVali = "Koko päivän";
        }
            
        return this.nimi + ", " + aikaVali + ", " + muistutus;
    }
}
