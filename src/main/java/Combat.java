import java.util.*;

public class Combat {

    PlayerCharacter pc;
    Monster monster;
    int round = 0;
    Character[] initiative;
    boolean victory = false;
    DiceRoller diceRoller = new DiceRoller();
    PlayerInput playerInput = new PlayerInput();
    List <ModChange> modChanges = new ArrayList<>();


    public Combat(PlayerCharacter pc, Monster monster) {
        this.pc = pc;
        this.monster = monster;
        this.initiative = createInitiative();
    }

    public void setDiceRoller(DiceRoller diceRoller){
        this.diceRoller = diceRoller;
    }

    public void setPlayerInput(PlayerInput playerInput){this.playerInput = playerInput;}

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
            playerTurn();
        }
        else{
            monsterTurn();
        }
    }

    public void secondTurn(Character second){
        if(second.equals(pc)){
            playerTurn();
        }
        else{
            monsterTurn();
        }
    }

    public void playerTurn(){
        int command = 0;
        System.out.println("What do you want to do? Type the number of the action.");
        System.out.println("1. Attack \n2. Dodge \n3. Use Item \n4. Magic");
        PlayerInput input = new PlayerInput();
        input.setInput();
        command = input.getInput();
        if (command < 1 || command > 4) {
            throw new IllegalArgumentException("That is not a valid action. Please try again.");
        }
        turn(pc, monster, command);
    }

    public void monsterTurn(){
        int command = monster.decisionMaker();
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
            if (pc.getInventory().getBackpack().size() < 1) {
                throw new NoSuchElementException("You have no items.");
            }
            System.out.println("What Item would you like to use? Write the number of the item.");
            int i = 0;
            while(i < pc.getInventory().getBackpack().size()){
                System.out.println((i + 1)+ ". " + pc.getInventory().getEquipment(i).getName());
                i++;
            }
            int choice = 0;
            PlayerInput input = new PlayerInput();
            input.setInput();
            choice = input.getInput();
            if (choice < 1 || choice > pc.getInventory().getBackpack().size()) {
                throw new NoSuchElementException("Please chose a valid item.");
            }
            useItem(pc, choice);
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

    public void useItem(PlayerCharacter pc, int command){
        if(pc.getInventory().getEquipment(command - 1) instanceof Consumable){
            Consumable c = (Consumable) pc.getInventory().getEquipment(command - 1);
            c.useItem(pc);
        }

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
        if(gold < 1 && monster.getInventory().getBackpack().size() < 1){
            System.out.println("You find no loot.");
        }
        else {
            System.out.print("You find a ");
            List<Equipment> loot = monster.getInventory().getBackpack();
            int i = 0;
            while (i < loot.size()) {
                Equipment e = monster.getInventory().getEquipment(i);
                pc.getInventory().getBackpack().add(e);
                System.out.print(e.getName() + ", ");
                i++;
            }
            if (gold > 0) {
                pc.addGold(gold);
                System.out.print(gold + " pieces of gold,");
            }
            System.out.println(" and you put it in your bag.");
        }
    }

    public int getRound(){
        return round;
    }
}