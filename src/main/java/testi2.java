
import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class testi2 {

    /**
     * Parsing a JSONObject string
     *
     * @param
     */
    private static class MyTimeTask extends TimerTask
    {
        testi2 app = new testi2();
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
    public static void main(String[] args) throws MalformedURLException, AWTException, ParseException {

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        LocalDateTime dt = LocalDateTime.of(2020,3,25,12,07);

        // LocalDateTimen toStringissä tulee "T" päivämäärän ja ajan väliin. Korvataan se tyhjällä niin homma pelittää
        Date date = dateFormatter .parse(dt.toString().replace("T", " "));

        //Now create the time and schedule it
        Timer timer = new Timer();

        //Use this if you want to execute it once
        timer.schedule(new MyTimeTask(), date);
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

        trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.INFO);
    }
}