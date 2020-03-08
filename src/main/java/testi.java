import javax.swing.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mikko
 */

public class testi {

    public static void main (String args[]) {

        JComponent myComponent = null;
        myComponent.getInputMap().put(KeyStroke.getKeyStroke("a"), "myAction");
        //myComponent.getActionMap().put("myAction", action);


    }

}




/*public class testi {
    public static void main (String args[]){
    
        while(true){
            System.out.println("asoudihas");
            Scanner lukija = new Scanner(System.in);

            String a = lukija.nextLine();

            if(a.equals("poista")){
                clrscr();
            }
        }
    }

    public static void clrscr(){

        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
}*/

