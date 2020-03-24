import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class  testi2 {
   /* private static class MyTimeTask extends TimerTask
    {

        public void run()
        {
            //write your code here
            System.out.println("hei");
        }
    }*/

    public static void main(String[] args) throws ParseException {
        File aani = new File("haukku.WAV");
        PLaySound(aani);
        /*the Date and time at which you want to execute
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormatter.parse("2020-03-23 20:0:45");

        //Now create the time and schedule it
        Timer timer = new Timer();

        //Use this if you want to execute it once
        timer.schedule(new MyTimeTask(), date);

        //Use this if you want to execute it repeatedly
        //int period = 10000;//10secs
        //timer.schedule(new MyTimeTask(), date, period );*/
    }
        static void PLaySound(File Sound){
            try{
                Clip clip= AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(Sound));
                clip.start();
                Thread.sleep(clip.getMicrosecondLength()/1000);
            }catch(Exception e){


            }

        }
}

