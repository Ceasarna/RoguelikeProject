public class Jewelry extends Equipment {

    private final int healthBonus;

    public Jewelry(String name, int healthBonus) {
        super(name);
        this.healthBonus = healthBonus;
    }

    public int getHealthBonus() {
        return healthBonus;
    }

}
