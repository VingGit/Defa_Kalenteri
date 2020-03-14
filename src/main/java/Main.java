import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Kalenteri kalenteri = new Kalenteri();
        Scanner lukija = new Scanner(System.in);
        Kayttoliittyma liittyma = new Kayttoliittyma(lukija, kalenteri);

        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF) ;

        logger.setUseParentHandlers(false);
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(liittyma);

        // estetään värien tulostuksesta syntyvä bugi
        liittyma.kasitteleKomento("W");
        liittyma.kasitteleKomento("S");

        liittyma.kaynnista();
    }
}