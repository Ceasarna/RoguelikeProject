import java.util.*;

public class Combat {

    final PlayerCharacter pc;
    final Monster monster;
    int round = 0;
    final Character[] initiative;
    boolean victory = false;
    boolean defeat;
    DiceRoller diceRoller = new DiceRoller();
    PlayerInput playerInput = new PlayerInput();
    final List <ModChange> modChanges = new ArrayList<>();


    public Combat(PlayerCharacter pc, Monster monster) {
        this.pc = pc;
        this.monster = monster;
        this.initiative = createInitiative();
    }

    //Samling get-metoder.
    public Character getPc() { return pc;}

    public Character getMonster() { return monster;}

    public int getRound(){ return round; }


    //Den här funktionen är till för att kunna använda mock-objekt av dicerollern i CombatTest-klassen.
    public void setDiceRoller(DiceRoller diceRoller){
        this.diceRoller = diceRoller;
    }

    //Den här funktionen är till för att kunna fejka spelarens input via mock-objekt i CombatTest-klassen.
    public void setPlayerInput(PlayerInput playerInput){this.playerInput = playerInput;}

    //Denna funktion ska kallas på av main klassen när en strid initieras.
    //Den börjar med att sortera spelaren och fienden efter initiativ.
    public void startCombat() {
        System.out.println("You are attacked by " + monster.getName());
        if (initiative[0].equals(pc)) {
            System.out.println("You go first.");
        } else {
            System.out.println("The " + monster.getName() + " attacks first.");
        }
        combatRound();
    }

    //Den här funktionen håller koll på hur många rundor som har gått,
    //samt fortsätter striden så länge som ingen har vunnit.
    public void combatRound(){
        while (!victory) {
            round++;
            if(initiative[0].equals(pc)) {
                playerTurn();
            }
            else{
                monsterTurn();
            }
        }
    }

    //Spelarens tur. Med hjälp av PlayerInput-klassen så får spelaren bestämma vad hen ska göra
    //Sedan kallas funktionen turn för att utföra handlingen
    //Sedan återgår den till antingen monsterTurn eller combatTurn beroende på spelarens initiativ.
    public void playerTurn(){
        tempModChangeCheck(pc);
        System.out.println("What do you want to do? Type the number of the action.");
        System.out.println("1. Attack \n2. Dodge \n3. Use Item \n4. Magic");
        int command = playerInput.getInput();
        if (command < 1 || command > 4) {
            throw new IllegalArgumentException("That is not a valid action. Please try again.");
        }
        turn(pc, monster, command);
        if(initiative[0].equals(pc)){
            monsterTurn();
        }
        else{
            combatRound();
        }
    }

    //Samma som playerTurn, fast för monster. Den tar in monstrets handling från Monster klassen.
    //Sedan utför den handlingen, och återgår till playerTurn eller combatRound.
    public void monsterTurn(){
        tempModChangeCheck(monster);
        int command = monster.decisionMaker();
        turn(monster, pc, command);
        if(initiative[0].equals(monster)){
            playerTurn();
        }
        else{
            combatRound();
        }
    }

    //Den här funktionen hanterar valet som karaktärerna gör. För tillfället endast attack, dodge, use item eller magic
    //Men kan rätt enkelt lägga till fler handlingar.
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
            int choice = playerInput.getInput();
            if (choice < 1 || choice > pc.getInventory().getBackpack().size()) {
                throw new NoSuchElementException("Please chose a valid item.");
            }
            useItem(pc, choice);
        }
        if(command == 4){
            if(user.equals(pc)){
                if (pc.getSpellBook().getSpellBook().size() < 1 && pc.getSpellBook().getUtilitySlot() == null &&
                pc.getSpellBook().getHealingSlot() == null && pc.getSpellBook().getDamageSlot() == null) {
                    throw new NoSuchElementException("You don't know any spells.");
                }
                System.out.println("What spellslot would you like to use? Write the number of the spellslot.");
                System.out.println("1. Damage\n2. Healing\n3. Utility");
                int choice = playerInput.getInput();
                if (choice < 1 || choice > 3) {
                    throw new NoSuchElementException("Please chose a valid spellslot.");
                }
                useMagic(pc, monster, choice);
            }
            else{
                int choice;
                Magic spell = monster.useMagic();
                if(spell instanceof DamageMagic){ choice = 1; }
                else if(spell instanceof HealingMagic){ choice = 2; }
                else if(spell instanceof UtilityMagic){ choice = 3; }
                else{ throw new IllegalArgumentException("How did you fuck this up?");}
                useMagic(monster, pc, choice);
            }
        }
    }


    //Den här funktionen skapar initiativet som en array. Den ber spelaren och fienden att rollForInitiative.
    //Sedan, baserat på det, skapar den en turordning enligt Character-klassens compareTo-metod.
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

    //En av handlingarna som kan utföras i strid.
    //Först kontrollerar den om attacken träffar, sedan om den träffar applicerar den skadan på fienden.
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

    //Ökar en karaktärs evasion för en runda. Skapar en modchange för ändringen, samt lägger till modchangen i
    //listan på modchanges.
    public void dodge(Character dodger){
        System.out.println(dodger.getName() + " takes an evasive stance.");
        ModChange modChange = new ModChange(getRound(), 1, dodger.getEvsMod(), dodger, "Evs");
        modChanges.add(modChange);
        modChange.changeMod();
    }

    //Använder ett consumable item. Kan än så länge bara användas av spelaren.
    public void useItem(PlayerCharacter pc, int choice){
        if(pc.getInventory().getEquipment(choice - 1) instanceof Consumable){
            Consumable c = (Consumable) pc.getInventory().getEquipment(choice - 1);
            c.useItem(pc);
        }
        else{ throw new IllegalArgumentException("This item cannot be used at this time.");}
    }

    //Funktionen för att utföra magi. Kallar på de olike Magic-klassernas funktioner för att få tillbaka spells.
    //Sedan utför skada, helning eller modchanges beroende på vad för sorts spell det är.
    public void useMagic(Character user, Character target, int choice){
        if(choice == 1){
            DamageMagic dSpell = user.getSpellBook().useDamageSpell();
            System.out.println(user.getName() + " casts " + dSpell.getSpellName() + ".");
            int toHit = diceRoller.roll1d100() + user.getAtkMod();
            if(toHit >= target.getEvsMod()){
                int damage = -1 * diceRoller.rollWithinRange( (int)dSpell.getMinDmg(), (int)dSpell.getMaxDmg());
                System.out.println("Hit! " + target.getName() + " takes " + damage*-1 + " points of damage.");
                takeDamage(user, target, damage);
            }
            else{
                System.out.println("Miss! " + target.getName() + " dodges the attack.");
            }
            user.modifyCurrentMana(dSpell.getSpellCost() *-1);
        }
        if(choice == 2){
            HealingMagic hSpell = user.getSpellBook().useHealingSpell();
            System.out.println(user.getName() + " casts " + hSpell.getSpellName());
            int healing = diceRoller.rollWithinRange((int)hSpell.getMinHeal(), (int)hSpell.getMaxHeal());
            user.modifyCurrentHealth(healing);
            user.modifyCurrentMana(hSpell.getSpellCost() *-1);
        }
        if(choice == 3){
            UtilityMagic uSpell = user.getSpellBook().useUtilitySpell();
            System.out.println(user.getName() + " casts " + uSpell.getSpellName());
            int utilityChange = (int)uSpell.getUtilityValue();
            String utilityMod = uSpell.getUtilityType();
            if(utilityChange > 0){
                target = user;
            }
            ModChange modChange = new ModChange(round, 5, utilityChange, target, utilityMod);
            modChanges.add(modChange);
            modChange.changeMod();
            user.modifyCurrentMana(uSpell.getSpellCost() *-1);
        }
    }

    //Den här funktionen kontrollerar ifall någon modChange har inträffat som ska återställas den här rundan.
    //Används för tillfället bara för dodge.
    public void tempModChangeCheck(Character user){
        int i = 0;
        while(i < modChanges.size()) {
            if (modChanges.get(i).getCharacter().equals(user) && modChanges.get(i).getRoundEnd() == round) {
                String mod = modChanges.get(i).getMod();
                int change = modChanges.get(i).getChange();
                modChanges.get(i).changeMod(mod, change);
                modChanges.remove(i);
            }
            i++;
        }
    }

    //Används för att skada en karaktär, efter att en attack har träffat.
    //Kallar sen på isAlive och wins ifall spelare eller monster har dött.
    public void takeDamage(Character attacker, Character defender, int damage){
        if(damage > 0){
            throw new IllegalArgumentException("Damage can't be positive");
        }
        defender.modifyCurrentHealth(damage);
        if(!isAlive(defender)){
            wins(attacker);
        }
    }

    //Kontrollerar att karaktärens hp är mer än 0.
    public boolean isAlive(Character character){
        int health = character.getCurrentHealth();
        return health >= 1;
    }

    //Hanterar efter-strids situationen, där om spelaren har vunnit så får den loot och exp.
    //Eller om monstret har vunnit så tar spelet slut.
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
            endGame();
        }
    }

    //Kontrollerar att monstret har guld eller loot som spelaren kan plocka på sig.
    //Flyttar guldet och lootet till spelarens inventory.
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

    //Om spelet vore färdigt/större så skulle den här metoden leda till att spelaren fick återgå till menyn,
    //samt att karaktären och progressen raderades.
    public void endGame(){
        defeat = true;
    }

}