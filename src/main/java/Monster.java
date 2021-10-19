public abstract class Monster extends Character{

    protected String monsterType;
    protected SpellBook spellBook;
    protected MonsterInventory inventory;
    public Monster(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name){
        super(atkMod, evsMod, maxHealth, gold, baseDmgMin, baseDmgMax, spd, name);
        spellBook = new SpellBook();
        this.inventory = new MonsterInventory(this);
    }

    public String getMonsterType(){
        return this.monsterType;
    }

    public SpellBook getSpellBook(){
        return spellBook;
    }

    abstract int giveEXP();
}
