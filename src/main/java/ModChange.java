public class ModChange {
    int round;
    int roundEnd;
    int change;
    Character character;
    String mod;

    public ModChange(int round, int duration, int change, Character character, String mod){
        this.round = round;
        this.roundEnd = round + duration;
        this.change = change;
        this.character = character;
        this.mod = mod;
    }

    public int getRoundEnd(){
        return roundEnd;
    }

    public void changeMod(){
        if(mod.equals("Evs")){
            character.modifyCurrentEvsMod(change);
            return;
        }
        if(mod.equals("Atk")){
            character.modifyCurrentAtkMod(change);
            return;
        }
        if(mod.equals("Spd")){
            character.modifyCurrentSpd(change);
            return;
        }
        else{
            throw new IllegalArgumentException("That is not a real mod");
        }
    }

}
