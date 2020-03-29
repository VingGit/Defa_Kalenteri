import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;

/** Staattinen luokka, jonka avulla voidaan tulostaa kuukausi tai päivä näkymä.
 */

public abstract class KalenteriNakyma {

    public static int getDay(int year, int month) {
        LocalDate localDate = LocalDate.of(year, Month.of(month), 1);
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
        return dayOfWeek.getValue();
    }

    public static void tulostaKuukausi(int paiva, int kuukausi, int vuosi, DayOfWeek vkpaiva, HashMap juhlat, ArrayList<Tapahtuma> tapahtumat, ArrayList<Tehtava> tehtavat, boolean onkoKuukausi) {

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
            System.out.println("           " + Kuukaudet.annaKuukausi(M)  + " " +vuosi);

            // Display the lines
            System.out.println(" ____________________________________");
            System.out.println("   Ma   Ti   Ke   To   Pe   La   Su");

            // print the calendar
            for (int i = 0; i < spaces; i++) {
                System.out.print("     ");
            }
            for (int j = 1; j <= days; j++) {

                // Onko päivässä juhla?
                if (juhlat.containsKey((LocalDate.of(vuosi, kuukausi, j)))) {
                    System.out.print(Varit.RED);
                }

                if (!juhlat.containsKey((LocalDate.of(vuosi, kuukausi, j)))) {
                    System.out.print(Varit.RESET);

                }

                // Onko päivässä tapahtuma?
                LocalDate pvm = (LocalDate.of(vuosi, kuukausi, j));
                for (Tapahtuma t : tapahtumat) {
                    if (pvm.isAfter(t.annaAjankohta().toLocalDate())  &&  (pvm.isBefore(t.annaLopetus().toLocalDate()))  ||
                            pvm.isEqual(t.annaAjankohta().toLocalDate())  ||
                            pvm.isEqual(t.annaLopetus().toLocalDate())) {
                        System.out.print(Varit.GREEN);

                    }
                }

                // Onko päivässä tehtävä?
                for (Tehtava t : tehtavat) {
                    if (pvm.isEqual(t.annaAjankohta().toLocalDate())) {
                        System.out.print(Varit.GREEN);
                    }
                }

                System.out.print("   ");

                if (j == paiva && !onkoKuukausi&& M == Kalenteri.eteenTaakseKuukausia) {
                    System.out.print(Varit.CYAN_BACKGROUND);
                }
                else if(j == paiva && onkoKuukausi){
                    System.out.print(Varit.CYAN_BACKGROUND);
                }

                if (j < 10) {
                    System.out.print(" " + j);
                } else {
                    System.out.print(j);
                }

                if (((j + spaces) % 7 == 0) || (j == days)) {
                    System.out.println(Varit.RESET);
                }
            }
            System.out.println(" ____________________________________");
        }
    }

    public static void tulostaVuosi(int paiva, int kuukausi, int vuosi, DayOfWeek vkpaiva, HashMap juhlat, ArrayList < Tapahtuma > tapahtumat, ArrayList < Tehtava > tehtavat) {
        for (int kk = 1; kk <= 12; kk++) {
            if (kk > 1) {
                System.out.println();
            }

            tulostaKuukausi( paiva, kk , vuosi, vkpaiva, juhlat, tapahtumat, tehtavat, false );
        }


    }

}
