// Återhämtningsmagi som ärver från Magi. En magi för att återta sin hälsa

public class HealingMagic extends Magic{

    // Attribut unika för återhämtningsmagi
    private double minHeal;
    private double maxHeal;

    // Konstruktor
    public HealingMagic(int spellCost, String spellName, int minHeal, int maxHeal){
        super(spellCost, spellName);
        if(minHeal < 0){
            throw new IllegalArgumentException("MinHeal cannot be below Zero");
        }
        this.minHeal = minHeal;

        if(minHeal > maxHeal){
            throw new IllegalArgumentException("MinHeal cannot be higher than MaxHeal");
        }
        this.maxHeal = maxHeal;

    }

    //Copy Constructor. Används för Combat
    public HealingMagic(HealingMagic healingMagic){
        super(healingMagic.getSpellCost(), healingMagic.getSpellName());
        this.minHeal = healingMagic.minHeal;
        this.maxHeal = healingMagic.maxHeal;
    }

    //Återhämtnings metod för Combat. Returnerar medelvärdet av min- och max Healing.
    public int restoreHealth(){
        return (int)(this.minHeal + this.maxHeal) / 2;
    }

    // Get-metod
    public double getMinHeal() {
        return minHeal;
    }

    // Get-metod
    public double getMaxDmg() {
        return maxHeal;
    }

    // Set-metod
    public void setMinHeal(double amount){
        if(amount < 0 ) {
            throw new IllegalArgumentException("Value cannot be lower than zero");
        }
        this.minHeal *= amount;
    }

    // Set-metod
    public void setMaxHeal(double amount){
        if(amount < 0 ) {
            throw new IllegalArgumentException("Value cannot be lower than zero");
        }
        this.maxHeal *= amount;
    }

}
