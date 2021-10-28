// Funktionalitetsmagi. Används för att ändra på standard attribut.

public class UtilityMagic extends Magic {

    // Värde
    private int utilityValue;
    private final String utilityType;

    // Konstruktor
    public UtilityMagic(int spellCost, String spellName, int utilityValue, String type){
        super(spellCost, spellName);
        this.utilityType = type;
        this.utilityValue = utilityValue;

    }

    // Set-metod.
    public void setUtilityValue(double amount){
        if(amount < 0 ) {
            throw new IllegalArgumentException("Value cannot be lower than zero");
        }
        this.utilityValue *= amount;
    }

    // Get-metod
    public int getUtilityValue() {
        return utilityValue;
    }

    // Get-metod
    public String getUtilityType(){
        return utilityType;
    }
}
