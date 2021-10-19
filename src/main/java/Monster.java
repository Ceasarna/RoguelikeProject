public abstract class Monster extends Character{

    protected String monsterType;
    protected SpellBook spellBook;
    public Monster(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name){
        super(atkMod, evsMod, maxHealth, gold, baseDmgMin, baseDmgMax, spd, name);
        spellBook = new SpellBook();

        //funktion som random. guld.
        //funktion som returnerar namn på typen av monstret
        //typer av monster med konkreta nummer
        //Inventory som ska kunnas droppas, kolla inventory klassen som erik skapade;
    }

    public String getMonsterType(){
        return this.monsterType;
    }

    public SpellBook getSpellBook(){
        return spellBook;
    };

    abstract int giveEXP();

    abstract void basicAttack();
}
