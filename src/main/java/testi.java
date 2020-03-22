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

        LocalDateTime date1 = LocalDateTime.of(LocalDate.of(2019,3,8), LocalTime.of(12,0));
        LocalDateTime date = LocalDateTime.of(LocalDate.of(2021,3,8), LocalTime.of(12,1));
        LocalDateTime date2 = LocalDateTime.of(LocalDate.of(2021,3,8), LocalTime.of(12,0));

        if ( date.isAfter(date1)  &&  date.isBefore(date2)   ||
                date.isEqual(date1)  ||
                date.isEqual(date2)               ) {
            System.out.println("totta");
        }
    }

}

