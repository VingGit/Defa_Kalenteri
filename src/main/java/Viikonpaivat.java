public abstract class Viikonpaivat {
    
    private static final String[] VIIKONPAIVAT = {"", "maanantai", "tiistai", "keskiviikko", "torstai", "perjantai", "lauantai", "sunnuntai"};
    
    public static String annaViikonpaiva(int paiva) {
        return VIIKONPAIVAT[paiva];
    }
}
