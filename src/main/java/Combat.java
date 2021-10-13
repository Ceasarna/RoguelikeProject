import java.util.*;

public class Combat {

    Character pc;
    Character monster;
    int round;
    //static String cmd1 = "attack", cmd2 = "dodge", cmd3 = "use item", cmd4 = "magic";
    boolean victory = false;


    public Combat(Character pc, Character monster) {
        this.pc = pc;
        this.monster = monster;
    }

    public void startCombat(){

        System.out.println("You are attacked by " + monster);
        Character[] initiative = participants(pc, monster);
        Character initiator = initiative[0];
        if(initiator.equals(pc)){
            System.out.println("You go first.");
        }
        else{
            System.out.println("The enemy attacks first.");
        }
        while(!victory){
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
        Scanner sc = new Scanner(System.in);
        int command = 0;
        System.out.println("What do you want to do? Type the number of the action.");
        System.out.println("1. Attack /n 2. Dodge /n 3. Use Item /n 4. Magic");
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
        int command = 0;
        turn(monster, pc, command);
    }
    public Character getPc() {
        return pc;
    }

    public Character getMonster() {
        return monster;
    }

    public int getRound(){
        return round;
    }

    public static Character[] participants(Character pc, Character monster){

        Character[] initiative = new Character[2];
        int pcInitiative = pc.getCurrentSpd() + percent();
        int monsterInitiative = monster.getCurrentSpd() + percent();

        if(pcInitiative > monsterInitiative){
            initiative[0] = pc;
            initiative[1] = monster;
        }
        else if(monsterInitiative > pcInitiative){
            initiative[0] = monster;
            initiative[1] = pc;
        }
        else{
            if(pc.getCurrentSpd() > monster.getCurrentSpd()){
                initiative[0] = pc;
                initiative[1] = monster;
            }
            else if (monster.getCurrentSpd() > pc.getCurrentSpd()){
                initiative[0] = monster;
                initiative[1] = pc;
            }
            else{
                initiative[0] = pc;
                initiative[1] = monster;
            }
        }
        return initiative;
    }

    public static int percent(){
        Random percent = new Random();
        return percent.nextInt(101);
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


    public void attack(Character attacker, Character defender){
        System.out.println("You attack.");
        int chanceToHit = attacker.getCurrentAtkMod() - defender.getCurrentEvsMod();
        int roll = percent();
        if(roll <= chanceToHit){
            int damage = damage(attacker.getBaseDmgMin(), attacker.getBaseDmgMax());
            defender.changeCurrentHealth(damage);
            System.out.println("You hit! " + monster + " takes " + damage + " damage.");
            if (!isAlive(defender)){
                winner(attacker);
            }
        }
        return;
    }

    public int damage(int minDmg, int maxDmg){
        int damage = (int)Math.floor(Math.random()*(maxDmg-minDmg+1)+minDmg);
        return damage;
    }

    public void dodge(Character dodger){
        System.out.println("You take a defensive stance.");
    }

    public boolean isAlive(Character victim){
        int health = victim.getCurrentHealth();
        if(health < 1){
            return false;
        }
        return true;
    }

    public void winner(Character winner){
        if(winner.equals(pc)){
            System.out.println( monster + " has been defeated.");
            //System for looting here
        }
        else{
            System.out.println("You've died.");
        }
        victory = true;
    }
}
