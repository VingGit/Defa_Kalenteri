public class Kellonaika {
    
    private int tunnit;
    private int minuutit;

    public Kellonaika(int tunnit, int minuutit) {
        if ( tunnit > 24 || tunnit < 0 ) {
            throw new IllegalArgumentException("Parametri tunnit tulisi olla luku väliltä 0 - 24");
        }
        if ( minuutit > 59 || minuutit < 0 ) {
            throw new IllegalArgumentException("Parametri minuutit tulisi olla väliltä 0 - 59 ");
        }
        
        this.tunnit = tunnit;
        this.minuutit = minuutit;
    }
    

    
    public void asetaTunnit(int tunnit) {
        if ( tunnit > 24 || tunnit < 0 ) {
            throw new IllegalArgumentException("Parametri tunnit tulisi olla luku väliltä 0 - 24");
        }
        
        this.tunnit = tunnit;
    }
    
    public void asetaMinuutit(int minuutit) {
        if ( minuutit > 59 || minuutit < 0 ) {
            throw new IllegalArgumentException("Parametri minuutit tulisi olla väliltä 0 - 59 ");
        }
        
        this.minuutit = minuutit;
    } 
    
    // metodi lisää nollan tuntien tai minuuttien eteen, jos ne ovat alle 10
    public String toString() {
        if (this.tunnit < 10 && this.minuutit < 10) {
            String tunnit = "0" + this.tunnit;
            String minuutit = "0" + this.minuutit;
            return tunnit + ":" + minuutit;
        }
        
        if (this.tunnit < 10) {
            String tunnit = "0" + this.tunnit;
            return tunnit + ":" + this.minuutit;
        }
        
        if (this.minuutit < 10) {
            String minuutit = "0" + this.minuutit;
            return this.tunnit + ":" + minuutit;
        }
        
        return this.tunnit + ":" + this.minuutit;
    }
    
}
