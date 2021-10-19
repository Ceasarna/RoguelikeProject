public class Slime extends Monster{

    private static final int SLIME_EXP_DROP = 400;


    public Slime(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name){
        super(atkMod, evsMod, maxHealth, gold, baseDmgMin, baseDmgMax, spd, name);
        this.monsterType = "Slime";

    }

    @Override
    int giveEXP() {
        return SLIME_EXP_DROP;
    }

    @Override
    void basicAttack() {

    }
}
