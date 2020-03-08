public abstract class Kuukaudet {
    
    private static final String[] KUUKAUDET = {"", "tammikuu", "helmikuu", "maaliskuu", "huhtikuu", "toukokuu", "kesäkuu", "heinäkuu", "elokuu", "syyskuu", "lokakuu", "marraskuu", "joulukuu"};

    // huom! helmikuuhun lisättävä päivä lisää jos on karkausvuosi!
    private static final int[] PAIVIEN_LKM = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    
    public static String annaKuukausi(int kk) {
        return KUUKAUDET[kk];
    }
    
    public static int annaPaiviaKuukaudessaLkm(int kk) {
        return PAIVIEN_LKM[kk];
    }
}
