package vaalit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tekstimalli {
    private Map<String, Sanajakauma> sanatJaNiidenSeuraajatEsiitymiskertoineen;
    private Sanajakauma sanajakauma;
    
    public Tekstimalli() {
        this.sanatJaNiidenSeuraajatEsiitymiskertoineen = new HashMap<>();
        this.sanajakauma = new Sanajakauma();
    }

    public void lisaaAineisto(String aineisto) {
        String[] sanat = aineisto.split(" ");
        
        String seuraava = "";
        int i = 0;
        while (i < sanat.length) {
            if (i + 1 < sanat.length) {
                seuraava = sanat[i+1];
                this.sanatJaNiidenSeuraajatEsiitymiskertoineen.putIfAbsent(sanat[i], new Sanajakauma());
                this.sanatJaNiidenSeuraajatEsiitymiskertoineen.get(sanat[i]).lisaaSana(seuraava);
                this.sanajakauma.lisaaSana(sanat[i]);
            } else {
                int index = sanat.length - 1;
                this.sanatJaNiidenSeuraajatEsiitymiskertoineen.putIfAbsent(sanat[index], new Sanajakauma());
                this.sanajakauma.lisaaSana(sanat[i]);
            }
            
            i++;
        }
    }

    public String annaSana(String edeltava) {
        if (!this.sanatJaNiidenSeuraajatEsiitymiskertoineen.keySet().contains(edeltava)) {
            return null;
        }
        if (this.sanatJaNiidenSeuraajatEsiitymiskertoineen.get(edeltava).annaSana() == null) {
            return null;
        }
        return this.sanatJaNiidenSeuraajatEsiitymiskertoineen.get(edeltava).annaSana();
    }

    public List<String> sanat() {
        if (this.sanajakauma.getSize() == 0) {
            return new ArrayList<>();
        }
        
        return this.sanajakauma.annaAvainSanat();
    }
    
    public String satunnainenSana() {
        List<String> lista = sanat();
        Collections.shuffle(lista);
        String satunnainen = lista.get(0);
        
        return satunnainen;
    }
}
