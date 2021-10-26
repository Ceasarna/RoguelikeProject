import java.util.*;

public class Combat {

    PlayerCharacter pc;
    Monster monster;
    int round = 0;
    Character[] initiative;
    boolean victory = false;
    DiceRoller diceRoller = new DiceRoller();
    List <ModChange> modChanges = new ArrayList<ModChange>();


    public Combat(PlayerCharacter pc, Monster monster) {
        this.pc = pc;
        this.monster = monster;
        this.initiative = createInitiative();
    }

    public void setDiceRoller(DiceRoller diceRoller){
        this.diceRoller = diceRoller;
    }

    public void startCombat() {
        System.out.println("You are attacked by " + monster.getName());
        if (initiative[0].equals(pc)) {
            System.out.println("You go first.");
        } else {
            System.out.println("The " + monster.getName() + " attacks first.");
        }
        while (!victory) {
            firstTurn(initiative[0]);
            secondTurn(initiative[1]);
            round++;
        }
    }

    public void firstTurn(Character first){
        if(first.equals(pc)){
            characterTurn();
        }
        else{
            monsterTurn();
        }
    }

    public void secondTurn(Character second){
        if(second.equals(pc)){
            characterTurn();
        }
        else{
            monsterTurn();
        }
    }

    public void characterTurn(){
        int command = 0;
        System.out.println("What do you want to do? Type the number of the action.");
        System.out.println("1. Attack /n 2. Dodge /n 3. Use Item /n 4. Magic");
        Scanner sc = new Scanner(System.in);
        while (command == 0){
            try {
                command = sc.nextInt();
                if (command < 1 || command > 4) {
                    throw new IllegalArgumentException("That is not a valid action. Please try again.");
                }
            }
            catch(IllegalArgumentException i){
                characterTurn();
            }
            turn(pc, monster, command);
        }
    }

    public void monsterTurn(){
        //Getting the monsters next move here
        int command = 1;
        turn(monster, pc, command);
    }

    public void turn(Character user, Character opponent, int command){
        if(command == 1){
            attack(user, opponent);
        }
        if(command == 2){
            dodge(user);
        }
        if(command == 3){
            //use item
        }
        if(command == 4){
            //use magic
        }
    }

    public Character getPc() {
        return pc;
    }

    public Character getMonster() {
        return monster;
    }

    public Character[] createInitiative() {
        Character[] initiative = new Character[2];
        pc.rollForInitiative();
        monster.rollForInitiative();
        int result = pc.compareTo(monster);
        if (result >= 0) {
            initiative[0] = pc;
            initiative[1] = monster;
        } else{
            initiative[0] = monster;
            initiative[1] = pc;
        }
        return initiative;
    }

    public void attack(Character attacker, Character defender){
        System.out.println(attacker.getName() + " attacks.");
        int toHit = diceRoller.roll1d100() + attacker.getAtkMod();
        if(toHit >= defender.getEvsMod()){
            int damage = -1 * attacker.rollDmg();
            System.out.println("Hit! " + defender.getName() + " takes " + damage*-1 + " points of damage.");
            takeDamage(attacker, defender, damage);
        }
        else{
            System.out.println("Miss! " + defender.getName() + " dodges the attack.");
        }
    }

    public void dodge(Character dodger){
        System.out.println(dodger.getName() + " takes an evasive stance.");
        ModChange modChange = new ModChange(getRound(), 1, dodger.getEvsMod(), dodger, "Evs");
        modChanges.add(modChange);
        modChange.changeMod();
    }

    public boolean isAlive(Character character){
        int health = character.getCurrentHealth();
        if(health < 1){
            return false;
        }
        return true;
    }

    public void takeDamage(Character attacker, Character defender, int damage){
        if(damage > 0){
            throw new IllegalArgumentException("Damage can't be positive");
        }
        defender.modifyCurrentHealth(damage);
        if(!isAlive(defender)){
            wins(attacker);
        }
    }

    public void wins(Character winner){
        if( winner == pc){
            System.out.println("You have defeated " + monster.getName());
            int exp = monster.giveEXP();
            pc.addExp(exp);
            getLoot(pc, monster);
            victory = true;
        }
        else{
            System.out.println("Your journey ends.");
            victory = true;
            //endGame();
        }
    }

    public void getLoot(PlayerCharacter pc, Monster monster){
        int gold = monster.getGold();
        pc.addGold(gold);
    }

    public int getRound(){
        return round;
    }
}