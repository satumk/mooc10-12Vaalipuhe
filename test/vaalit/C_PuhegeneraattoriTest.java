package vaalit;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

@Points("10-12.5")
public class C_PuhegeneraattoriTest {

    private Puhegeneraattori puhegeneraattori;

    @Before
    public void setup() {
        this.puhegeneraattori = new Puhegeneraattori();
    }

    @Test
    public void lukeeTiedostonJaTuottaaPuhetta() {
        String tiedostonNimi = luoTiedosto("0;a b c d e a;a b c a");
        puhegeneraattori.lue(tiedostonNimi);
        poista(tiedostonNimi);

        String tuotettuPuhe = puhegeneraattori.tuotaPuhetta(12);
        String[] puheenPalat = tuotettuPuhe.split(" ");

        assertEquals(12, puheenPalat.length);
        assertTrue(tuotettuPuhe.contains("a b c d e a b c"));
        assertFalse(tuotettuPuhe.contains("a b c a"));
    }

    @Test
    public void lukeeTiedostonJaTuottaaPuhetta2() {
        String tiedostonNimi = luoTiedosto("0;one two three four five six;a b c a");
        puhegeneraattori.lue(tiedostonNimi);
        poista(tiedostonNimi);

        for (int i = 0; i < 50; i++) {

            String tuotettuPuhe = puhegeneraattori.tuotaPuhetta(5);

            String[] puheenPalat = tuotettuPuhe.split(" ");
            assertTrue(puheenPalat.length <= 5);

            if (puheenPalat.length == 1) {
                return;
            }
        }

        fail("Kun tekstimalli on muodostettu tekstistä \"one two three four five six\", pitäisi puheessa joskus esiintyä _vain_ sana \"six\".");
    }

    @Test
    public void lukeeMonirivisenTiedostonJaTuottaaPuhetta() {
        String tiedostonNimi = luoTiedosto("0;one two three four five;a b c a\n0;five one;a b c a");
        puhegeneraattori.lue(tiedostonNimi);
        poista(tiedostonNimi);

        String tuotettuPuhe = puhegeneraattori.tuotaPuhetta(12);
        String[] puheenPalat = tuotettuPuhe.split(" ");

        assertEquals(12, puheenPalat.length);
        assertTrue(tuotettuPuhe.contains("one two three four five one two"));
    }

    public void poista(String tiedosto) {
        try {
            new File(tiedosto).delete();
        } catch (Throwable t) {

        }
    }

    public String luoTiedosto(String sisalto) {
        String nimi = "tmp-" + UUID.randomUUID().toString().substring(0, 6);

        try {
            Files.write(Paths.get(nimi), rivit(sisalto));
        } catch (IOException ex) {
            fail("Testitiedoston" + nimi + " luominen epäonnistui.");
        }

        return nimi;
    }

    private List<String> rivit(String sisalto) {
        return Arrays.asList(sisalto.split("\n"));
    }

}
