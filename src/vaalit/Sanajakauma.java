package vaalit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class Sanajakauma {
    private Map<String, Integer> sanatJaEsiintymiskerrat;
    
    public Sanajakauma() {
        this.sanatJaEsiintymiskerrat = new HashMap<>();
    }

    public void lisaaSana(String sana) {
        if (this.sanatJaEsiintymiskerrat.keySet().contains(sana)) {
            int esiintymiskertoja = this.sanatJaEsiintymiskerrat.get(sana);
            esiintymiskertoja++;
            this.sanatJaEsiintymiskerrat.put(sana, esiintymiskertoja);
        } else {
            this.sanatJaEsiintymiskerrat.putIfAbsent(sana, 1);
        }
    }

    public int esiintymiskertoja(String sana) {
        if (!this.sanatJaEsiintymiskerrat.keySet().contains(sana)) {
            return 0;
        }
        return this.sanatJaEsiintymiskerrat.get(sana);
    }

    public String annaSana() {
        
        if (this.sanatJaEsiintymiskerrat.isEmpty()) {
            return null;
        }
        Random satunnainen = new Random();
        List<String> avainSanat = new ArrayList<>();
        
        int esiintymiskertojenSumma = 0;
        
        for (String sana : this.sanatJaEsiintymiskerrat.keySet()) {
            esiintymiskertojenSumma += this.sanatJaEsiintymiskerrat.get(sana);
            for (int i = 0; i < this.sanatJaEsiintymiskerrat.get(sana); i++) {
                avainSanat.add(sana);
            }
        }
        
        int luku = new Random().nextInt(esiintymiskertojenSumma);

        return avainSanat.get(luku);
    }
    
    public List<String> annaAvainSanat() {
        ArrayList<String> sanat = this.sanatJaEsiintymiskerrat.keySet().stream()
                .collect(Collectors.toCollection(ArrayList::new));
        return sanat;
    }
    
    public int getSize() {
        return this.sanatJaEsiintymiskerrat.size();
    }
}
