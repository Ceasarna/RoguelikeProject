public class Character implements Comparable<Character> {

    private final static int MAX_MANA = 100;

    private DiceRoller diceRoller = new DiceRoller();

    private int atkMod;
    private int currentAtkMod;
    private int evsMod;
    private int currentEvsMod;
    private int currentHealth;
    private int maxHealth;
    private int baseDmgMin;
    private int baseDmgMax;
    private int spd;
    private int currentSpd;
    private int initiative;
    private String name;
    private int currentMana;

    protected int gold;

    public Character(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name){
        this.atkMod = atkMod;
        currentAtkMod = atkMod;
        this.evsMod = evsMod;
        currentEvsMod = evsMod;
        if(maxHealth < 1){
            throw new IllegalArgumentException("Max Health cannot be less than 1");
        }
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
        if(gold < 0){
            throw new IllegalArgumentException("Gold cannot be negative");
        }
        this.gold = gold;
        if(baseDmgMin < 0){
            throw new IllegalArgumentException("Minimum base damage cannot be negative");
        }
        this.baseDmgMin = baseDmgMin;
        if(baseDmgMax < baseDmgMin){
            throw new IllegalArgumentException("Maximum base damage must be equal to or higher than minimum base damage!");
        }
        this.baseDmgMax = baseDmgMax;
        this.spd = spd;
        currentSpd = spd;
        if(name == null){
            throw new IllegalArgumentException("Name cannot be null");
        } else if (name.length() == 0){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
        currentMana = MAX_MANA;
    }

    public void setDiceRoller(DiceRoller diceRoller){
        this.diceRoller = diceRoller;
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

    public void changeMaxHealth(int mod){
        maxHealth += mod;
        currentHealth += mod;
        if(maxHealth < 1){
            maxHealth = 1;
        }
        if(currentHealth < 1){
            currentHealth = 1;
        }
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
