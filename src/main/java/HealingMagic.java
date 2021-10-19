public class HealingMagic extends Magic{

    private final int minHeal;
    private final int maxHeal;

    public HealingMagic(int spellCost, String spellName, int minHeal, int maxHeal){
        super(spellCost, spellName);
        this.minHeal = minHeal;

        if(minHeal > maxHeal){
            throw new IllegalArgumentException("MinHeal cannot be higher than MaxHeal");
        }else{
            this.maxHeal = maxHeal;
        }
    }

    public int getMinHeal() {
        return minHeal;
    }

    public int getMaxDmg() {
        return maxHeal;
    }
}
