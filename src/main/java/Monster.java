// Klass för att skapa konkreta monster som har tillgång till Inventory. M

public class Monster extends Character{

    // Statiska variabler för val av magi
    private static final int USE_DAMAGE_MAGIC = 1;
    private static final int USE_HEALING_MAGIC = 2;
    private static final int USE_UTILITY_MAGIC = 3;

    // Statiska variabler för vad Monster kan göra i strid
    private static final int BASIC_ATTACK = 1;
    private static final int MAGIC_SPELL = 4;

    // SpellBook samt inventory. Kommer inte ändras.
    private final Inventory inventory;

    // Värde för vad nästa val av agerande för monstret ska vara
    private int queuedDecision = 0;

    // Attribut för vilken typ av monster som instansieras i konstruktorn
    protected String monsterType;

    // Konstruktor. Skapar Tomma utrymmen för inventory samt spellBook
    public Monster(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name, int lvl, String monsterType){
        super(atkMod, evsMod, maxHealth, gold, baseDmgMin, baseDmgMax, spd, name, lvl);
        this.monsterType = monsterType;
        this.inventory = new Inventory(this);
    }

    // Get-metod
    public Inventory getInventory(){ return this.inventory; }

    //Get-metod
    public String getMonsterType(){
        return this.monsterType;
    }

    // Lägger till pengar till monstret
    public void addGold(int newGold){
        if(newGold < 1){
            throw new IllegalArgumentException("New gold must be positive");
        }
        gold += newGold;
    }
    /*
    Enkel AI som returnerar det lämpligaste agerandet för monstret
    Prioritet, från högst till lägst:
    - Ifall hälsan är under 50%, använd Heal ifall det finns.
    - Ifall det finns en DamageSpell, använd den.
    - Ifall det finns en utilitySpell, använd den.
    - Annars utförs en vanlig attack.
     */

    public int decisionMaker(){
        if((getCurrentHealth() <= (getMaxHealth() * 0.5)) && this.spellBook.getHealingSlot() != null ){
            queuedDecision = USE_HEALING_MAGIC;
            return MAGIC_SPELL;
        } else if(this.spellBook.getDamageSlot() != null){
            queuedDecision = USE_DAMAGE_MAGIC;
            return MAGIC_SPELL;
        }else if((this.spellBook.getUtilitySlot() != null)){
            queuedDecision = USE_UTILITY_MAGIC;
            return MAGIC_SPELL;
        }else{
            return BASIC_ATTACK;
        }
    }

    // Beroende på vilket val som gjordes tidigare kommer denna metod returnera den
    // valda magin som monstret valt
    public Magic useMagic() {
        return switch (queuedDecision) {
            case 1 -> this.spellBook.useDamageSpell();
            case 2 -> this.spellBook.useHealingSpell();
            case 3 -> this.spellBook.useUtilitySpell();
            default -> throw new IllegalStateException("No queued decision");
        };
    }

    // Metod för att returnera EXP
    public int giveEXP(){
        return 100 * this.getLvl();
    }
}
