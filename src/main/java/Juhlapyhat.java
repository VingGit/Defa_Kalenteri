import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/** Staattinen luokka, jonka metodia asetaJuhla() kutsutaan Kalenteri olion luomisen yhteydessä,
 *  ja silloin kun Kalenteri olion vuosi muuttuu.
 */

public abstract class Juhlapyhat {

    public static Map asetaJuhlat(HashMap juhlat, int vuosi) {
        Map<LocalDate, String> juhlatpaivat = new HashMap<LocalDate, String>();

        juhlat.put(LocalDate.of(vuosi, 1, 1), "Uuudenvuodenpäivä");
        juhlat.put(LocalDate.of(vuosi, 1, 6), "Loppiainen");
        juhlat.put(laskePitkaperjantai(vuosi), "Pitkäperjantai");
        juhlat.put(laskePaasiainen(vuosi), "Pääsiäispäivä");
        juhlat.put(laske2Pääsiäinen(vuosi), "2. pääsiäispäivä");
        juhlat.put(LocalDate.of(vuosi, 5, 1), "Vappu");
        juhlat.put(laskeHelatorstai(vuosi), "Helatorstai");
        juhlat.put(laskeHelluntai(vuosi), "Helluntaipäivä");
        juhlat.put(laskeJuhannus(vuosi), "Juhannuspäivä");
        juhlat.put(laskePyhainpaiva(vuosi), "Pyhäinpäivä");
        juhlat.put(LocalDate.of(vuosi, 12, 6), "Itsenäisyyspäivä");
        juhlat.put(LocalDate.of(vuosi, 12, 25), "Joulupäivä");
        juhlat.put(LocalDate.of(vuosi, 12, 26), "2. joulupäivä");

        return juhlatpaivat;
    }

    private static LocalDate laskePitkaperjantai(int vuosi) {
        // Pääsiäissunnuntai - 2
        LocalDate paasiainen = laskePaasiainen(vuosi);
        LocalDate pitkaperjantai = paasiainen.minusDays(2);
        return pitkaperjantai;
    }

    private static LocalDate laskePaasiainen(int vuosi) {
        // Pääsiäissunnuntai laskettuna Gaussin algoritmillä.
        int a = vuosi % 19;
        int b = vuosi % 4;
        int c = vuosi % 7;
        int k = vuosi / 100;
        int p = (13 + 8 * k) / 25;
        int q = k / 4;
        int M = (15 - p + k - q) % 30;
        int N = (4 + k - q) % 7;
        int d = (19 * a + M) % 30;
        int e = (2 * b + 4 * c + 6 * d + N) % 7;

        if (d == 29 && e == 6) {
            return LocalDate.of(vuosi, 3, 22).plusDays(d + e).minusDays(7);

        } else {
            return LocalDate.of(vuosi, 3, 22).plusDays(d + e);
        }
    }

    private static LocalDate laske2Pääsiäinen(int vuosi) {
        // Pääsiäissunnuntai + 1
        LocalDate paasiainen = laskePaasiainen(vuosi);
        LocalDate paasiaispaiva2 = paasiainen.plusDays(1);
        return paasiaispaiva2;
    }

    private static LocalDate laskeHelatorstai(int vuosi) {
        // 40. päivänä pääsiäisen jälkeen
        LocalDate paasiainen = laskePaasiainen(vuosi);
        LocalDate helatorstai = paasiainen.plusDays(39);
        return helatorstai;
    }

    private static LocalDate laskeHelluntai(int vuosi) {
        // 50. päivänä pääsiäisen jälkeen
        LocalDate paasiainen = laskePaasiainen(vuosi);
        LocalDate helluntai = paasiainen.plusDays(49);
        return helluntai;
    }

    private static LocalDate laskeJuhannus(int vuosi) {
        // Juhannus on aina lauantai eli 6. viikonpäivä. Välillä 20.6. - 26.6.
        int paiva = 20;
        while (paiva <= 25) {
            LocalDate pvm = LocalDate.of(vuosi, 6, paiva);
            if (pvm.getDayOfWeek().getValue() == 6) {
                return LocalDate.of(vuosi, 6, paiva);
            }
            paiva++;
        }

        // Jos edelle oleva while looppi ei käy toteen, on juhannuksen oltava 26.6.
        return LocalDate.of(vuosi, 6, 26);
    }

    private static LocalDate laskePyhainpaiva(int vuosi) {
        // Pyhäinpäivä on lokakuu 31. - marraskuu 6. välillä.
        int paiva = 1;
        while (paiva <= 6) {
            LocalDate pvm = LocalDate.of(vuosi, 11, paiva);
            if (pvm.getDayOfWeek().getValue() == 6) {
                return LocalDate.of(vuosi, 11, paiva);
            }
            paiva++;
        }

        // Jos edelle oleva while looppi ei käy toteen, on pyhäinpäivän oltava 31.10.
        return LocalDate.of(vuosi, 10, 31);
    }
}