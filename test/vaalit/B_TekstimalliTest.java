package vaalit;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class B_TekstimalliTest {

    private Tekstimalli tekstimalli;

    @Before
    public void setup() {
        this.tekstimalli = new Tekstimalli();
    }

    @Test
    @Points("10-12.3")
    public void sanatListaEiNullJosEiAineistoa() {
        assertNotNull(this.tekstimalli.sanat());
    }

    @Test
    @Points("10-12.3")
    public void sanatListaTyhjaJosEiAineistoa() {
        assertEquals(0, this.tekstimalli.sanat().size());
    }

    @Test
    @Points("10-12.3")
    public void yhdenSananAineistoNakyyYhtenaSananaListassa() {
        this.tekstimalli.lisaaAineisto("sana");
        assertEquals(1, this.tekstimalli.sanat().size());
        assertEquals("sana", this.tekstimalli.sanat().get(0));
    }

    @Test
    @Points("10-12.3")
    public void yhdenToistuvanSananAineistoNakyyOikeanaSananaListassa() {
        this.tekstimalli.lisaaAineisto("sana sana");
        assertEquals(1, this.tekstimalli.sanat().size());
        assertEquals("sana", this.tekstimalli.sanat().get(0));
    }

    @Test
    @Points("10-12.3")
    public void sanatListassaKukinSanaVainKerran1() {
        this.tekstimalli.lisaaAineisto("sana");
        this.tekstimalli.lisaaAineisto("sana sana");
        assertEquals(1, this.tekstimalli.sanat().size());
    }

    @Test
    @Points("10-12.3")
    public void sanatListassaKukinSanaVainKerran2() {
        this.tekstimalli.lisaaAineisto("sana");
        this.tekstimalli.lisaaAineisto("sana sana");
        assertEquals(1, this.tekstimalli.sanat().size());
        assertTrue(this.tekstimalli.sanat().contains("sana"));
    }

    @Test
    @Points("10-12.3")
    public void sanatListassaKukinSanaVainKerran3() {
        this.tekstimalli.lisaaAineisto("sana toinen");
        this.tekstimalli.lisaaAineisto("toinen sana");
        this.tekstimalli.lisaaAineisto("sana sana");
        this.tekstimalli.lisaaAineisto("toinen toinen");
        assertEquals(2, this.tekstimalli.sanat().size());
        assertTrue(this.tekstimalli.sanat().contains("sana"));
        assertTrue(this.tekstimalli.sanat().contains("toinen"));
    }

    @Test
    @Points("10-12.3")
    public void sanatListassaKukinSanaVainKerran4() {
        this.tekstimalli.lisaaAineisto("sana toinen");
        this.tekstimalli.lisaaAineisto("toinen kolmas");
        this.tekstimalli.lisaaAineisto("kolmas sana");
        assertEquals(3, this.tekstimalli.sanat().size());
        assertTrue(this.tekstimalli.sanat().contains("sana"));
        assertTrue(this.tekstimalli.sanat().contains("toinen"));
        assertTrue(this.tekstimalli.sanat().contains("kolmas"));
    }

    @Test
    @Points("10-12.3")
    public void pitkanSanastonKasittely() {
        this.tekstimalli.lisaaAineisto("eka toka kolmas viides kuudes");
        assertEquals(5, this.tekstimalli.sanat().size());
        assertTrue(this.tekstimalli.sanat().contains("eka"));
        assertTrue(this.tekstimalli.sanat().contains("toka"));
        assertTrue(this.tekstimalli.sanat().contains("kolmas"));
        assertTrue(this.tekstimalli.sanat().contains("viides"));
        assertTrue(this.tekstimalli.sanat().contains("kuudes"));
    }

    @Test
    @Points("10-12.4")
    public void annaSanaPalauttaaNullViitteenJosParametrinaAnnettuSanaTuntematon1() {
        assertEquals(null, this.tekstimalli.annaSana("sana"));
    }

    @Test
    @Points("10-12.4")
    public void annaSanaPalauttaaNullViitteenJosParametrinaAnnettuSanaTuntematon2() {
        this.tekstimalli.lisaaAineisto("olipa kerran elämä");
        assertEquals(null, this.tekstimalli.annaSana("sana"));
    }

    @Test
    @Points("10-12.4")
    public void annaSanaPalauttaaSananAineistosta1() {
        this.tekstimalli.lisaaAineisto("olipa kerran elämä");
        assertEquals("kerran", this.tekstimalli.annaSana("olipa"));
    }

    @Test
    @Points("10-12.4")
    public void annaSanaPalauttaaSananAineistosta2() {
        this.tekstimalli.lisaaAineisto("eka toka kolmas viides kuudes");
        assertEquals("toka", this.tekstimalli.annaSana("eka"));
        assertEquals("kolmas", this.tekstimalli.annaSana("toka"));
        assertEquals("viides", this.tekstimalli.annaSana("kolmas"));
        assertEquals("kuudes", this.tekstimalli.annaSana("viides"));
        assertEquals(null, this.tekstimalli.annaSana("kuudes"));
    }

    @Test
    @Points("10-12.4")
    public void annaSanaPalauttaaSananAineistostaSananTodennakoisyyksienPerusteella1() {
        this.tekstimalli.lisaaAineisto("eka toka eka kolmas");

        Map<String, Integer> esiintymat = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String sana = this.tekstimalli.annaSana("eka");
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }

        assertTrue(esiintymat.get("toka") > 400);
        assertTrue(esiintymat.get("kolmas") > 400);
    }

    @Test
    @Points("10-12.4")
    public void annaSanaPalauttaaSananAineistostaSananTodennakoisyyksienPerusteella2() {
        this.tekstimalli.lisaaAineisto("eka toka eka kolmas eka viides");

        Map<String, Integer> esiintymat = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String sana = this.tekstimalli.annaSana("eka");
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }

        assertTrue(esiintymat.get("toka") > 270);
        assertTrue(esiintymat.get("kolmas") > 270);
        assertTrue(esiintymat.get("viides") > 270);
    }

    @Test
    @Points("10-12.4")
    public void annaSanaPalauttaaSananAineistostaSananTodennakoisyyksienPerusteella3() {
        this.tekstimalli.lisaaAineisto("a b c d e d c b a");

        Map<String, Integer> esiintymat = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String sana = this.tekstimalli.annaSana("a");
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }

        for (int i = 0; i < 1000; i++) {
            String sana = this.tekstimalli.annaSana("b");
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }

        assertEquals(new Integer(1000), esiintymat.get("b"));
        assertTrue(esiintymat.get("a") > 400);
        assertTrue(esiintymat.get("c") > 400);
    }

}
