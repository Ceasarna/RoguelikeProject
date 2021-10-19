public class Armour extends Equipment {

    private final int spdBonus;
    private final int evsBonus;
    private final String name;

    public Armour(int spdBonus, int evsBonus, String name) {
        super(name);
        this.spdBonus = spdBonus;
        this.evsBonus = evsBonus;
        this.name = name;
    }

    public int getSpdBonus() {
        return spdBonus;
    }

    public int getEvsBonus() {
        return evsBonus;
    }

    public String getName() {
        return name;
    }

}
