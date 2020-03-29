public class PvmAikaMuotoVaarinException extends Throwable {

    private String viesti;

    public PvmAikaMuotoVaarinException() {
        this.viesti = "Annoit ajankohdan muodon väärin!";
    }

    public PvmAikaMuotoVaarinException(String viesti) {
        this.viesti = viesti;
    }

    public String toString() {
        return this.viesti;
    }
}
