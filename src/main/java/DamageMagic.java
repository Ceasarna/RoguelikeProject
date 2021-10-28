// Skademagi som ärver från Magi. En magi för att göra skada

import java.util.List;

public class DamageMagic extends Magic{

    // Attribut som identifierar skademagi
    private double minDmg;
    private double maxDmg;
    private String damageType = "";

    // Konstruktor
    public DamageMagic(int spellCost, String type, String spellName, int minDmg, int maxDmg){
        super(spellCost, spellName);

        // Typ av magi kan enbart vara Light eller Dark
        List<String> listOfTypes = List.of(new String[]{"Light", "Dark"});
        for (String element : listOfTypes) {
            if (element.equals(type)) {
                this.damageType = type;
                break;
            }
        }
        if(this.damageType.equals("")){
            throw new IllegalArgumentException("Spelltype must either be Light or Dark");
        }

        this.minDmg = minDmg;


        if(minDmg > maxDmg){
            throw new IllegalArgumentException("MinDmg cannot be higher than MaxDmg");
        }else{
            this.maxDmg = maxDmg;
        }
    }

    // Metod för skada i Combat. Returnerar genomsnittet av min- och max skada
    public int dealDamage(){
        return (int)(this.minDmg + this.maxDmg)/2;
    }

    // Set-metod
    public void setMinDmg(double amount){
        if(amount < 0 ) {
            throw new IllegalArgumentException("Value cannot be lower than zero");
        }
        this.minDmg *= amount;
    }

    // Set-metod
    public void setMaxDmg(double amount){
        if(amount < 0 ) {
            throw new IllegalArgumentException("Value cannot be lower than zero");
        }
        this.maxDmg *= amount;
    }
    // Get-metod
    public double getMinDmg() {
        return minDmg;
    }
     // Get-metod
    public double getMaxDmg() {
        return maxDmg;
    }
    // Get-Metod
    public String getDamageType(){
        return this.damageType;
    }

}
