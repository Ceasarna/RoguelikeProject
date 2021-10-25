/*Spelarens klass som utökar funktionaliteten i Character med saker som ett nytt inventory,
* ett sätt att få och förlora guld samt ett sätt att levla upp genom att få exp*/

public class PlayerCharacter extends Character {

    //Spelarkaraktärer börjar alltid på lvl 1
    private static final int STARTING_LEVEL = 1;
    private static final int STARTING_EXP = 1000;

    //Endast spelarkaraktärer kan levla, så bara de behöver ha en exp-mätare av detta slag
    private int exp;

    //Spelarens inventory som är med avancerat än monsters inventory
    private final PlayerInventory playerInventory;

    //Konstruktorn för spelarkaraktären. Tar in samma värden med undantag för level som alltid börjar på 1
    public PlayerCharacter(int atkMod, int evsMod, int maxHealth, int gold, int baseDmgMin, int baseDmgMax, int spd, String name) {
        super(atkMod, evsMod, maxHealth, gold, baseDmgMin, baseDmgMax, spd, name, STARTING_LEVEL);
        playerInventory = new PlayerInventory(this);
        exp = STARTING_EXP;
    }

    //Get-metoderna

    public PlayerInventory getInventory() {return playerInventory;}

    public int getExp(){
        return exp;
    }

    //Skriver över skademetoden i Character för att hantera faktumet att spelare kan ha vapen
    @Override
    public int rollDmg(){
        Weapon weapon = playerInventory.getWeaponSlot();
        if(weapon == null){
            return diceRoller.rollWithinRange(getBaseDmgMin(), getBaseDmgMax());
        }
        return diceRoller.rollWithinRange(weapon.getDmgMin(), weapon.getDmgMax());
    }

    //Lägger till guld till karaktärens plånbok
    public void addGold(int newGold){
        if(newGold < 1){
            throw new IllegalArgumentException("New gold must be positive");
        }
        gold += newGold;
    }

    //Tar bort guld från karaktärens plånbok
    public void removeGold(int lostGold){
        if(lostGold < 0){
            lostGold = lostGold * -1;
        }
        if((gold - lostGold) < 0 ){
            throw new IllegalArgumentException("You cannot lose more gold than you have");
        }
        gold -= lostGold;
    }

    //Ger karaktären exp och levlar upp den vid behov
    public void addExp(int newExp){
        if(newExp < 1){
            throw new IllegalArgumentException("Gained exp must be above 0");
        }
        exp += newExp;
        int levelsFromNewTotalExp = exp / 1000;
        int newLevelsFromExp = lvl - levelsFromNewTotalExp;

        while(newLevelsFromExp > 0){
            lvlUp();
            newLevelsFromExp--;
        }
    }

    //Hjälpmetod till addExp som hanterar när karaktären får levla upp
    private void lvlUp(){
        modifyMaxHealth(5);
        lvl++;
    }

}
