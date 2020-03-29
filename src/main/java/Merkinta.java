import java.awt.*;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Merkinta extends TimerTask implements Serializable {

    protected String nimi;
    protected LocalDateTime ajankohta;
    protected LocalDateTime muistutusAika;

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

    public void asetaAjankohta(int vuosi, int kuukausi, int paiva, int tunnit, int minuutit) {
        this.ajankohta = LocalDateTime.of(LocalDate.of(vuosi, kuukausi, paiva), LocalTime.of(tunnit, minuutit));
    }

    public void asetaAjankohta(LocalDate pvm, LocalTime aika) {
        this.ajankohta = LocalDateTime.of(pvm, aika);
    }

    public void asetaAjankohta(LocalDateTime pvmAika) {
        this.ajankohta = pvmAika;
    }

    public LocalDateTime annaAjankohta() {
        return this.ajankohta;
    }

    public void asetaMuistutus(LocalDateTime muistutusAika) throws ParseException {
        this.muistutusAika = muistutusAika;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String muistutus = this.muistutusAika.format(formatter);
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(muistutus);

        Timer timer = new Timer();
        timer.schedule(this, date);
    }

    public boolean onkoMuistutus() {
        if (this.muistutusAika == null) {
            return false;
        }

        return true;
    }

    public String toString() {
        return this.nimi + " " + this.ajankohta.toString();
    }

    @Override
    public void run() {
        Merkinta app = this;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.y  HH:mm");
            app.displayTray(this.nimi, this.ajankohta.format(formatter));
        } catch (AWTException | MalformedURLException e) {
            e.printStackTrace();
        }
        //System.out.println("hei");
    }

    public void displayTray(String caption, String text) throws AWTException, MalformedURLException {
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

        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO);
    }
}