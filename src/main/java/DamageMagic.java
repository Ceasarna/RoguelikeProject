public class DamageMagic extends Magic{

    private final int minDmg;
    private final int maxDmg;

    public DamageMagic(int spellCost, String spellName, int minDmg, int maxDmg){
        super(spellCost, spellName);
        this.minDmg = minDmg;
        if(minDmg > maxDmg){
            throw new IllegalArgumentException("MinDmg cannot be higher than MaxDmg");
        }else{
            this.maxDmg = maxDmg;
        }
    }

    public int getMinDmg() {
        return minDmg;
    }

    public int getMaxDmg() {
        return maxDmg;
    }

}
