public class LoppuEnnenAlkuaException extends Throwable {

    private String viesti;

    public LoppuEnnenAlkuaException() {
        this.viesti = "Annoit lopetusajan joka on ennen aloitusaikaa!";
    }

    public LoppuEnnenAlkuaException(String viesti) {
        this.viesti = viesti;
    }

    public String toString() {
        return this.viesti;
    }
}
