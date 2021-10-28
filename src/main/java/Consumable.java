public class Consumable extends Equipment{
    private final String type;
    private final int amount;

    //Speciellt sorts Equipment som försvinner efter användning.
    public Consumable(String name, String type, int amount) {
        super(name);
        this.type = type;
        this.amount = amount;
    }

    //Påverkar användaren beroende på vad för typ consumablen har.
    public void useItem(PlayerCharacter user){
        if( type.equals("Health")){
            user.modifyCurrentHealth(amount);
        }
        if(type.equals("Mana")){
            user.modifyCurrentMana(amount);
        }
        user.getInventory().getBackpack().remove(this);
    }
}
