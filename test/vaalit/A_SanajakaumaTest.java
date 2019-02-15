package vaalit;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class A_SanajakaumaTest {

    private Sanajakauma sanajakauma;

    @Before
    public void setup() {
        this.sanajakauma = new Sanajakauma();
    }

    @Points("10-12.1")
    @Test
    public void esiintymiskertojaAluksiNolla() {
        assertEquals(0, this.sanajakauma.esiintymiskertoja("sana"));
    }

    @Points("10-12.1")
    @Test
    public void esiintymiskertojaMuuttuuYhdeksiKunSanaLisataan() {
        this.sanajakauma.lisaaSana("sana");
        assertEquals(1, this.sanajakauma.esiintymiskertoja("sana"));
    }

    @Points("10-12.1")
    @Test
    public void esiintymiskertojaRiippuvainenLisaystenMaarasta() {
        int lisayskertoja = 5 + new Random().nextInt(5);
        for (int i = 0; i < lisayskertoja; i++) {
            this.sanajakauma.lisaaSana("sana");
        }

        assertEquals(lisayskertoja, this.sanajakauma.esiintymiskertoja("sana"));
    }

    @Points("10-12.1")
    @Test
    public void useammanSananLisaaminenOnnistuu() {
        int lisayskertojaEka = 5 + new Random().nextInt(5);
        int lisayskertojaToka = 5 + new Random().nextInt(5);
        for (int i = 0; i < lisayskertojaEka; i++) {
            this.sanajakauma.lisaaSana("eka");
        }

        for (int i = 0; i < lisayskertojaToka; i++) {
            this.sanajakauma.lisaaSana("toka");
        }

        assertEquals(lisayskertojaEka, this.sanajakauma.esiintymiskertoja("eka"));
        assertEquals(lisayskertojaToka, this.sanajakauma.esiintymiskertoja("toka"));
    }

    @Points("10-12.1")
    @Test
    public void annaSanaPalauttaaNullJosEiSanoja() {
        assertEquals(null, this.sanajakauma.annaSana());
    }

    @Points("10-12.1")
    @Test
    public void annaSanaPalauttaaLisatynSanan() {
        this.sanajakauma.lisaaSana("sana");
        assertEquals("sana", this.sanajakauma.annaSana());
    }

    @Points("10-12.1")
    @Test
    public void annaSanaPalauttaaLisatytSanatJollainTodennakoisyydella() {
        this.sanajakauma.lisaaSana("eka");
        this.sanajakauma.lisaaSana("toka");
        this.sanajakauma.lisaaSana("kolmas");

        Set<String> nahdytSanat = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            nahdytSanat.add(this.sanajakauma.annaSana());
        }

        assertEquals(3, nahdytSanat.size());
        nahdytSanat.remove("eka");
        nahdytSanat.remove("toka");
        nahdytSanat.remove("kolmas");
        assertEquals(0, nahdytSanat.size());
    }

    @Points("10-12.2")
    @Test
    public void annaSanaPalauttaaLisatytSanatEsiintymistaRiippuvallaTodennakoisyydella1() {
        this.sanajakauma.lisaaSana("eka");
        this.sanajakauma.lisaaSana("toka");
        this.sanajakauma.lisaaSana("kolmas");

        Map<String, Integer> esiintymat = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String sana = this.sanajakauma.annaSana();
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }
        
        assertTrue(esiintymat.get("eka") > 250);
        assertTrue(esiintymat.get("toka") > 250);
        assertTrue(esiintymat.get("kolmas") > 250);
    }
    
    @Points("10-12.2")
    @Test
    public void annaSanaPalauttaaLisatytSanatEsiintymistaRiippuvallaTodennakoisyydella2() {
        this.sanajakauma.lisaaSana("eka");
        this.sanajakauma.lisaaSana("toka");

        Map<String, Integer> esiintymat = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String sana = this.sanajakauma.annaSana();
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }
        
        assertTrue(esiintymat.get("eka") > 400);
        assertTrue(esiintymat.get("toka") > 400);
        assertFalse(esiintymat.containsKey("kolmas"));
    }
    
    @Points("10-12.2")
    @Test
    public void annaSanaPalauttaaLisatytSanatEsiintymistaRiippuvallaTodennakoisyydella3() {
        this.sanajakauma.lisaaSana("eka");
        this.sanajakauma.lisaaSana("toka");
        this.sanajakauma.lisaaSana("toka");

        Map<String, Integer> esiintymat = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String sana = this.sanajakauma.annaSana();
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }
        
        assertTrue(esiintymat.get("eka") > 270);
        assertTrue(esiintymat.get("toka") > 550);
    }
    
    @Points("10-12.2")
    @Test
    public void annaSanaPalauttaaLisatytSanatEsiintymistaRiippuvallaTodennakoisyydella4() {
        this.sanajakauma.lisaaSana("yksi-viidesosa-1");
        this.sanajakauma.lisaaSana("yksi-viidesosa-2");
        this.sanajakauma.lisaaSana("yksi-viidesosa-3");
        this.sanajakauma.lisaaSana("kaksi-viidesosaa");
        this.sanajakauma.lisaaSana("kaksi-viidesosaa");

        Map<String, Integer> esiintymat = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            String sana = this.sanajakauma.annaSana();
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }
        
        assertTrue(esiintymat.get("yksi-viidesosa-1") > 1700);
        assertTrue(esiintymat.get("yksi-viidesosa-2") > 1700);
        assertTrue(esiintymat.get("yksi-viidesosa-3") > 1700);
        assertTrue(esiintymat.get("kaksi-viidesosaa") > 3500);
    }
    
    @Points("10-12.2")
    @Test
    public void annaSanaPalauttaaLisatytSanatEsiintymistaRiippuvallaTodennakoisyydella5() {
        this.sanajakauma.lisaaSana("yksi-kymmenesosa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");
        this.sanajakauma.lisaaSana("yhdeksan-kymmenesosaa");

        Map<String, Integer> esiintymat = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            String sana = this.sanajakauma.annaSana();
            esiintymat.put(sana, esiintymat.getOrDefault(sana, 0) + 1);
        }
        
        assertTrue(esiintymat.get("yksi-kymmenesosa") > 850);
        assertTrue(esiintymat.get("yhdeksan-kymmenesosaa") > 8500);
    }

}
