/*Toppen i klasstrukturen för karaktärer som sedan ärvs av både PlayerCharacter och Monster.*/

public class Character implements Comparable<Character> {

    private static final int MAX_MANA = 100;

    //En karaktärs basskada och namn är konstant från skapandet och kan därför vara final
    private final int baseDmgMin;
    private final int baseDmgMax;
    private final String name;

    //Alla grundegenskaper som behöver kunna ändras
    private int atkMod;
    private int currentAtkMod;
    private int evsMod;
    private int currentEvsMod;
    private int currentHealth;
    private int maxHealth;
    private int spd;
    private int currentSpd;
    private int initiative;
    private int currentMana;

    //En hjälpklass för att hantera slump
    protected DiceRoller diceRoller = new DiceRoller();

    protected SpellBook spellBook;

    //Dessa har metoder som behöver komma åt dem i subklasserna, därav protected
    protected int gold;
    protected int lvl;

    //Konstruktorn för klassen. Den är fullständigt enorm, men behöver vara det pga alla parametrar
    public Character(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name, int lvl){
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
        if(lvl < 1){
            throw new IllegalArgumentException("lvl must be at least 1");
        }
        this.lvl = lvl;
        this.spellBook = new SpellBook(this);
    }

    //Denna set-metod behövs för att tvinga klassen att använda vårat mock-objekt som tar bort slumpen
    public void setDiceRoller(DiceRoller diceRoller){
        this.diceRoller = diceRoller;
    }

    //Alla get-metoder

    public int getLvl(){
        return lvl;
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

    public DiceRoller getDiceRoller() {
        return diceRoller;
    }

    public SpellBook getSpellBook(){
        return spellBook;
    }

    //Höjer eller sänker den nuvarande hälsan
    public void modifyCurrentHealth(int mod){
        if(currentHealth + mod > maxHealth){
            currentHealth = maxHealth;
            return;
        }
        currentHealth += mod;
    }

    //Höjer eller sänker storleken på karaktärens hälsomätare och hälsa.
    public void modifyMaxHealth(int mod){
        maxHealth += mod;
        modifyCurrentHealth(mod);
        if(maxHealth < 1){
            maxHealth = 1;
        }
        if(currentHealth < 1){
            currentHealth = 1;
        }
    }

    //Höjer eller sänker karaktärens nuvarande- och baschans att träffa med en attack
    public void modifyAtkMod(int mod){
        atkMod += mod;
        modifyCurrentAtkMod(mod);
    }

    //Höjer eller sänker karaktärens nuvarande chans att träffa med en attack
    public void modifyCurrentAtkMod(int mod){
        currentAtkMod += mod;
    }

    //Höjer eller sänker karaktärens nuvarande- och baschans att undvika en attack
    public void modifyEvsMod(int mod){
        evsMod += mod;
        modifyCurrentEvsMod(mod);
    }

    //Höjer eller sänker karaktärens nuvarande chans att undvika en attack
    public void modifyCurrentEvsMod(int mod){
        currentEvsMod += mod;
    }

    //Höjer eller sänker karaktärens nuvarande- och bas-initiativbonus
    public void modifySpd(int mod){
        spd += mod;
        modifyCurrentSpd(mod);
    }

    //Höjer eller sänker karaktärens nuvarande initiativbonus
    public void modifyCurrentSpd(int mod){
        currentSpd += mod;
    }

    //Höjer eller sänker karaktärens nuvarande mana-pool
    public void modifyCurrentMana(int mod){
        currentMana += mod;
        if(currentMana < 0){
            currentMana = 1;
        }
        if(currentMana > MAX_MANA){
            currentMana = MAX_MANA;
        }
    }

    //Genererar karaktärens initiativ med hjälp av DiceRoller-klassen
    public void rollForInitiative(){
        initiative = diceRoller.roll1d100() + currentSpd;
    }

    //Slår karaktärens skada utifrån dess basskada
    public int rollDmg(){
        return diceRoller.rollWithinRange(baseDmgMin, baseDmgMax);
    }

    //Jämför två karaktärer för att beräkna initiativordning
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
