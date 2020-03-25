import java.awt.*;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Merkinta implements Serializable {

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

    public void asetaMuistutus(LocalDateTime muistutus) throws ParseException {
        this.muistutus = muistutus;

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // LocalDateTimen toStringissä tulee "T" päivämäärän ja ajan väliin. Korvataan se tyhjällä niin homma pelittää
        Date date = dateFormatter.parse(this.muistutus.toString().replace("T", " "));

        //Now create the time and schedule it
        Timer timer = new Timer();

        //Use this if you want to execute it once
        timer.schedule(new MyTimeTask(), date);
    }

    private static class MyTimeTask extends TimerTask
    {
        Merkinta app = new Merkinta();
        public void run()
        {
            try {
                app.displayTray();
            } catch (AWTException | MalformedURLException e) {
                e.printStackTrace();
            }
            //write your code here
            System.out.println("hei");
        }
    }
    public void displayTray() throws AWTException, MalformedURLException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        String caption = this.nimi + "weeee";
        String text = this.alku + "faef";
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
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

    public String toString() {
        return this.nimi + " " + this.alku.toString();
    }
}