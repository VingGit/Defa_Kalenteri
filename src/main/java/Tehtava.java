import java.time.LocalDate;

/* Tapahtuma.class ja Tehtava.class ero on nyt käytännössä se, että tapahtumalla on aloitus- ja lopetusaika,
   mutta tehtävällä vain aloitusaika
 */

public class Tehtava extends Tapahtuma {
    
    public Tehtava(LocalDate pvm) {
        super(pvm);
    }


    @Override
    public String toString() {
        String muistutus = "Ei muistutusta";
        if (this.muistutus) {
            muistutus = "Muistutus on päällä";
        }
        
        return this.nimi + ", " + this.alku.toString() + ", " + muistutus;
    }
}
