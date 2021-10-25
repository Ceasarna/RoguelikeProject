//Typ av utrustning som ger karaktären en modifikation till initiativ och chans att undvika

public class Armour extends Equipment {

    //Modifikationerna. Ändras aldrig
    private final int spdBonus;
    private final int evsBonus;

    //Konstruktor
    public Armour(int spdBonus, int evsBonus, String name) {
        super(name);
        this.spdBonus = spdBonus;
        this.evsBonus = evsBonus;
    }

    //Get-metoderna för modifikationerna

    public int getSpdBonus() {
        return spdBonus;
    }

    public int getEvsBonus() {
        return evsBonus;
    }

}
