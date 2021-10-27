public class Consumable extends Equipment{
    String type;
    int amount;

    //Specific type of item that can be consumed, and disappears from backpack upon use.
    public Consumable(String name, String type, int amount) {
        super(name);
        this.type = type;
        this.amount = amount;
    }

    //Using the items have an effect on the user based on what type of consumable item it is. Easy to add more types.
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
