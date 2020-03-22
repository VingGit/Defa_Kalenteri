import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalKeyListenerExample implements NativeKeyListener {
    private Scanner lukija;
    private Robot r;
    public GlobalKeyListenerExample() throws NativeHookException, AWTException {
        this.lukija= new Scanner(System.in);
        this.r=new Robot();
        // Create custom logger and level.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        GlobalScreen.setEventDispatcher(new VoidDispatchService());
        GlobalScreen.registerNativeHook();

        GlobalScreen.addNativeKeyListener(this);
    }

    private class VoidDispatchService extends AbstractExecutorService {
        private boolean running = false;

        public VoidDispatchService() {
            running = true;
        }

        public void shutdown() {
            running = false;
        }

        public List<Runnable> shutdownNow() {
            running = false;
            return new ArrayList<Runnable>(0);
        }

        public boolean isShutdown() {
            return !running;
        }

        public boolean isTerminated() {
            return !running;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return true;
        }

        public void execute(Runnable r) {
            r.run();
        }
    }



    public void nativeKeyReleased(NativeKeyEvent e) {

        if (e.getKeyCode() == NativeKeyEvent.VC_B) {

            System.out.print("Attempting to consume B event...\t");
            try {
                Field f = NativeInputEvent.class.getDeclaredField("reserved");
                f.setAccessible(true);
                f.setShort(e, (short) 0x01);

                System.out.print("[ OK ]\n");
                String input= "Missä kaupungissa Pasi Viheraho asuu? x";
                String result = input.substring(0, input.indexOf("x"));
                System.out.println(result);


                String syote = this.lukija.nextLine();

                //yllä oleva syote pitäisi pystyä luomaan täysin tyhjänä,
                // nyt se tulostaa scannerin luonnin yhteydessä kaikki kirjaimet
                //joita painoit ennen kun painoit b:tä. bugi ei kuitenkaan vaikuta ohjelman toimintaan.
                syote = syote.substring(syote.indexOf("b")+1);
                System.out.print(syote);
            }
            catch (Exception ex) {
                System.out.print("[ !! ]\n");
                ex.printStackTrace();
            }
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_P) {


            try {
                Field f = NativeInputEvent.class.getDeclaredField("reserved");
                f.setAccessible(true);
                f.setShort(e, (short) 0x01);

                System.out.print("[ OK ]\n");

            }
            catch (Exception ex) {
                System.out.print("[ !! ]\n");
                ex.printStackTrace();
            }
        }
    }
    @Override
    public void nativeKeyTyped(NativeKeyEvent e) { /* Unimplemented */ }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

    }

    public static void main(String [] args) throws NativeHookException, AWTException {
        new GlobalKeyListenerExample();
    }
}