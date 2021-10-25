public class Monster extends Character{

    protected String monsterType;
    protected SpellBook spellBook;
    protected Inventory inventory;
    public Monster(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name){
        super(atkMod, evsMod, maxHealth, gold, baseDmgMin, baseDmgMax, spd, name);
        spellBook = new SpellBook();
        this.inventory = new Inventory(this);
    }

    public String getMonsterType(){
        return this.monsterType;
    }

    public SpellBook getSpellBook(){
        return spellBook;
    }

    public int giveEXP() {
        return 100;
    }
}
