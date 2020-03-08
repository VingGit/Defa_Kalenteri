import org.w3c.dom.ls.LSOutput;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

/** Staattinen luokka, jonka avulla voidaan tulostaa yksi kuukausi tai koko vuosi.
 */

public abstract class KalenteriNakyma {

    public static int getDay(int year, int month) {
        LocalDate localDate = LocalDate.of(year, Month.of(month), 1);

        // Find the day from the local date
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);

        return dayOfWeek.getValue();
    }

    public static void tulostaKuukausi(int paiva, int kuukausi, int vuosi, DayOfWeek vkpaiva, HashMap juhlat) {

        // spaces required
        int spaces = getDay(vuosi, kuukausi) - 1;

        // number of days in month
        int days = Kuukaudet.annaPaiviaKuukaudessaLkm(kuukausi);

        for (int M = kuukausi; M <= kuukausi; M++) {

            // check for leap year
            if ((((vuosi % 4 == 0) && (vuosi % 100 != 0)) || (vuosi % 400 == 0)) && M == 2)
                days += 1;

            // print calendar header
            // Display the month and year
            System.out.println("    " + Viikonpaivat.annaViikonpaiva(vkpaiva.getValue()) + " " + paiva + ". " + Kuukaudet.annaKuukausi(M) + "ta " + vuosi);

            // Display the lines
            System.out.println("__________________________________");
            System.out.println("  Ma   Ti   Ke   To   Pe   La   Su");

            // print the calendar
            for (int i = 0; i < spaces; i++) {
                System.out.print("     ");
            }
            for (int j = 1; j <= days; j++) {

                if (juhlat.containsKey((LocalDate.of(vuosi, kuukausi, j)))) {
                    System.out.print(Varit.CYAN);
                }

                if (!juhlat.containsKey((LocalDate.of(vuosi, kuukausi, j)))) {
                    System.out.print(Varit.RESET);

                }

                if (j == paiva) {
                    System.out.print(Varit.RED);

                }

                System.out.printf(" %3d ", j);

                if (((j + spaces) % 7 == 0) || (j == days)) {
                    System.out.println(Varit.RESET);
                }

            }
            System.out.println("__________________________________");
        }
    }




    /*
        public void tulostaKokoVuosi() {
            int kuukausi = 1;    // year
            int Y = this.vuosi;
            int spaces = getDay(Y, kuukausi) - 1;

            // number of days in month
            int days = Kuukaudet.annaPaiviaKuukaudessaLkm(kuukausi);

            for (int M = kuukausi; M <= 12; M++) {

                // check for leap year
                if ((((Y % 4 == 0) && (Y % 100 != 0)) || (Y % 400 == 0)) && M == 2)
                    days += 1;


                // print calendar header
                // Display the month and year
                System.out.println("          " + Kuukaudet.annaKuukausi(M) + " " + Y);

                // Display the lines
                System.out.println("__");
                System.out.println("  Ma   Ti   Ke   To   Pe   La   Su");

                // spaces required
                spaces = (days - 1] + spaces) % 7;

                // print the calendar

                for (int i = 0; i < spaces; i++) {
                    System.out.print("     ");
                }
                for (int j = 1; j <= days; j++) {
                    System.out.printf(" %3d ", j);
                    if (((j + spaces) % 7 == 0) || (j == days)) System.out.println();

                }

            }
        }
    */
}
