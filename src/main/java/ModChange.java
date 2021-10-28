//En klass som används för att hålla koll på tillfälliga ändringar i mods under strider.
public class ModChange {
    final int round;
    final int roundEnd;
    final int change;
    final Character character;
    final String mod;

    public ModChange(int round, int duration, int change, Character character, String mod){
        this.round = round;
        this.roundEnd = round + duration;
        this.change = change;
        this.character = character;
        this.mod = mod;
    }

    //Samling get-metoder
    public int getRoundEnd(){
        return roundEnd;
    }

    public Character getCharacter(){ return character; }

    public String getMod(){ return mod; }

    public int getChange(){ return change; }

    //Används när en modChange skapas, och ser till att karaktärens mods ändras.
    public void changeMod(){
        if(mod.equals("Evs")){
            character.modifyCurrentEvsMod(change);
        }
        else if(mod.equals("Atk")){
            character.modifyCurrentAtkMod(change);
        }
        else if(mod.equals("Spd")){
            character.modifyCurrentSpd(change);
        }
        else{
            throw new IllegalArgumentException("That is not a real mod");
        }
    }

    //Används när modden ska återgå till hur det var innan, använder två argument.
    public void changeMod(String mod, int change) {
        if (mod.equals("Evs")) {
            character.modifyCurrentEvsMod(change);
        }
        else if (mod.equals("Atk")) {
            character.modifyCurrentAtkMod(change);
            return;
        }
        else if (mod.equals("Spd")) {
            character.modifyCurrentSpd(change);
        }
    }
}
