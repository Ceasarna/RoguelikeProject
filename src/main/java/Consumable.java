public class Consumable extends Equipment{
    String type;
    int amount;

    public Consumable(String name, String type, int amount) {
        super(name);
        this.type = type;
        this.amount = amount;
    }

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
