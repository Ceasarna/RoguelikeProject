public abstract class Character implements Comparable<Character> {

    private DiceRoller diceRoller = new DiceRoller();

    private int atkMod;
    private int currentAtkMod;
    private int evsMod;
    private int currentEvsMod;
    private int currentHealth;
    private int maxHealth;
    private int gold;
    private int baseDmgMin;
    private int baseDmgMax;
    private int spd;
    private int currentSpd;
    private int initiative;
    private String name;
    private final static int MAX_MANA = 100;
    private int currentMana;

    public Character(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name){
        this.atkMod = atkMod;
        currentAtkMod = atkMod;
        this.evsMod = evsMod;
        currentEvsMod = evsMod;
        this.maxHealth = maxHealth;
        this.gold = gold;
        this.baseDmgMin = baseDmgMin;
        if(baseDmgMin > baseDmgMax){
            throw new IllegalArgumentException("BaseDmgMin cannot be higher than BaseDmgMax");
        }else{
            this.baseDmgMax = baseDmgMax;
        }
        this.spd = spd;
        currentSpd = spd;
        this.name = name;
        currentMana = MAX_MANA;
    }

    public int getAtkMod() {
        return atkMod;
    }

    public int getEvsMod() {
        return evsMod;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getGold() {
        return gold;
    }

    public int getCurrentAtkMod() {
        return currentAtkMod;
    }

    public int getCurrentEvsMod() {
        return currentEvsMod;
    }

    public int getBaseDmgMin() {
        return baseDmgMin;
    }

    public int getBaseDmgMax() {
        return baseDmgMax;
    }

    public int getSpd() {
        return spd;
    }

    public int getCurrentSpd() {
        return currentSpd;
    }

    public int getInitiative() {
        return initiative;
    }

    public String getName() {
        return name;
    }

    public int getMana(){
        return currentMana;
    }

    public void changeCurrentHealth(int mod){
        if(currentHealth + mod > maxHealth){
            currentHealth = maxHealth;
            return;
        }
        currentHealth += mod;
    }

    public void increaseMaxHealth(int mod){
        if(mod <= 0){
            throw new IllegalArgumentException("mod must be >0");
        }
        maxHealth += mod;
        currentHealth += mod;
    }

    public void changeAtkMod(int mod){
        atkMod += mod;
        changeCurrentAtkMod(mod);
    }

    public void changeEvsMod(int mod){
        evsMod += mod;
        changeCurrentEvsMod(mod);
    }

    public void changeCurrentAtkMod(int mod){
        currentAtkMod += mod;
    }

    public void changeCurrentEvsMod(int mod){
        currentEvsMod += mod;
    }

    public void changeSpd(int mod){
        spd += mod;
        changeCurrentSpd(mod);
    }

    public void changeCurrentSpd(int mod){
        currentSpd += mod;
    }

    public void changeCurrentMana(int mod){
        currentMana += mod;
    }

    public void rollForInitiative(){
        initiative = diceRoller.roll1d100() + currentSpd;
    }

    public int rollBaseDmg(){
        return diceRoller.rollWithinRange(baseDmgMin, baseDmgMax);
    }

    @Override
    public int compareTo(Character o) {
        if(initiative > o.getInitiative()){
            return 1;
        } else if (initiative < o.getInitiative()){
            return -1;
        } else if (currentSpd > o.getCurrentSpd()){
            return 1;
        } else if (currentSpd < o.getCurrentSpd()){
            return -1;
        } else {
            return 0;
        }
    }
}
