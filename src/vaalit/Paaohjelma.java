package vaalit;

public class Paaohjelma {

    public static void main(String[] args) throws Exception {
        // kokeile ohjelman toimintaa täällä
Puhegeneraattori generaattori = new Puhegeneraattori();
generaattori.lue("vaalidata.csv");
System.out.println(generaattori.tuotaPuhetta(15));
    }
}
