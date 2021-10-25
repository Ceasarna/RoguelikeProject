//En typ av utrustning som ger karaktären en hälsobonus

public class Jewelry extends Equipment {

    //Hälsobonusen. Ändras aldrig
    private final int healthBonus;

    //Konstruktor
    public Jewelry(String name, int healthBonus) {
        super(name);
        this.healthBonus = healthBonus;
    }

    //Get-metod för bonusen
    public int getHealthBonus() {
        return healthBonus;
    }

}
