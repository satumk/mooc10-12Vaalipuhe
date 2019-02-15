package vaalit;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Puhegeneraattori {
    private Tekstimalli tekstimalli;
    
    public Puhegeneraattori() {
        this.tekstimalli = new Tekstimalli();
    }

    public void lue(String tiedosto) {
        ArrayList<String> lauseet = new ArrayList<>();
        
        try {
            Files.lines(Paths.get(tiedosto)).map(rivi -> rivi.split(";"))
                    .forEach(palat -> lauseet.add(palat[1]));
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }
        
        for (int i = 0; i < lauseet.size(); i++) {
            this.tekstimalli.lisaaAineisto(lauseet.get(i));
        }
        /* try - catch: lue tiedosto 
        rivi kerrallaan
        split
        jokaisesta osasta annetaan osa 1 annetaan arraylistalle lauseena
        lista annetaan lause kerrallaan tekstimallille: lisää aineisto
        */
    }

    public String tuotaPuhetta(int sanojaEnintaan) {
        String puhe = this.tekstimalli.satunnainenSana();
        String viimeisinSana = puhe;
        int i = 1;
        while (i < sanojaEnintaan) {
            
            if (this.tekstimalli.annaSana(viimeisinSana) == null) {
                return puhe;
            }
            
            viimeisinSana = this.tekstimalli.annaSana(viimeisinSana);
            puhe += " " + viimeisinSana;
            
            i++;
        }
        /* hyödynnä tekstimallin metodia satunnainenSana()
        while-loop hyödyntäen tekstimallin annaSana()metodia
            if lopputulos on null = break
        */
        return puhe;

    }
}
