public class Weapon extends Equipment {

    private final int atkBonus;
    private final int dmgMin;
    private final int dmgMax;

    public Weapon(int atkBonus, int dmgMin, int dmgMax, String name) {
        super(name);
        this.atkBonus = atkBonus;
        if(dmgMin < 0){
            throw new IllegalArgumentException("Minimum damage cannot be negative");
        }
        this.dmgMin = dmgMin;
        if(dmgMax < dmgMin){
            throw new IllegalArgumentException("Maximum damage cannot be lower than Minimum damage");
        }
        this.dmgMax = dmgMax;
    }

    public int getAtkBonus() {
        return atkBonus;
    }

    public int getDmgMin() {
        return dmgMin;
    }

    public int getDmgMax() {
        return dmgMax;
    }

}
