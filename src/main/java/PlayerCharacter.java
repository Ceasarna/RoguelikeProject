public class PlayerCharacter extends Character {

    private static final int STARTING_LEVEL = 1;
    private static final int STARTING_EXP = 1000;

    private int lvl;
    private int exp;

    private PlayerInventory playerInventory;

    public PlayerCharacter(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name) {
        super(atkMod, evsMod, maxHealth, gold, baseDmgMin, baseDmgMax, spd, name);
        playerInventory = new PlayerInventory(this);
        lvl = STARTING_LEVEL;
        exp = STARTING_EXP;
    }

    public PlayerInventory getInventory() {return playerInventory;}

    public void addGold(int newGold){
        if(newGold < 1){
            throw new IllegalArgumentException("New gold must be positive");
        }
        gold += newGold;
    }

    public void loseGold(int lostGold){
        if(lostGold < 0){
            lostGold = lostGold * -1;
        }
        if((gold - lostGold) < 0 ){
            throw new IllegalArgumentException("You cannot lose more gold than you have");
        }
        gold -= lostGold;
    }

    public void gainExp(int newExp){
        if(newExp < 1){
            throw new IllegalArgumentException("Gained exp must be above 0");
        }
        int tempTotalExp = exp + newExp;
        int levelsFromNewTotalExp = tempTotalExp / 1000;
        int newLevelsFromExp = lvl - levelsFromNewTotalExp;

        while(newLevelsFromExp > 0){
            lvlUp();
            newLevelsFromExp--;
        }

        exp += newExp;
    }

    private void lvlUp(){
        changeMaxHealth(150);
        lvl++;
    }

    public int getCurrentExp(){
        return exp;
    }
}
