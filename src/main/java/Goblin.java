public class Goblin extends Monster{

    private static final int GOBLIN_EXP_DROP = 200;

    public Goblin(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name){
        super(atkMod, evsMod, maxHealth, gold, baseDmgMin, baseDmgMax, spd, name);
        this.monsterType = "Goblin";
    }
    @Override
    public int giveEXP() {
        return GOBLIN_EXP_DROP;
    }

}
