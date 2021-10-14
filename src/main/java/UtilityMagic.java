public class UtilityMagic extends Magic {

    private final int utilityValue;

    public UtilityMagic(int spellCost, String spellName, int utilityValue){
        super(spellCost, spellName);
        this.utilityValue = utilityValue;
    }

    public int getUtilityValue() {
        return utilityValue;
    }
}
