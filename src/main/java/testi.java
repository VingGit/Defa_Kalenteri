import javax.swing.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAccessor;
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

        char escCode = 0x1B;
        int row = 10; int column = 10;
        System.out.println("hei");
        System.out.print(String.format("%c[%d;%df",escCode,row,column));
        System.out.println("moi");
    }

}

