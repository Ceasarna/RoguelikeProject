public abstract class Magic {
    private final int spellCost;
    private final String spellName;

    public Magic(int spellCost, String spellName){
        if(spellCost < 0 || spellCost > 100){
            throw new IllegalArgumentException("Cost of spell cannot be under 0 or over 100");
        } else{
            this.spellCost = spellCost;
        }
        this.spellName = spellName;
    }

    public int getSpellCost() {
        return spellCost;
    }

    public String getSpellName() {
        return spellName;
    }
}
