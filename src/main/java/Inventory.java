//Klassen som hanterar ryggsäckar och agerar som förälder till spelarens inventory

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    //Både ägaren och ryggsäcken behöver nås av PlayerInventory, därav protected
    protected Character owner;
    protected final ArrayList<Equipment> backpack = new ArrayList<>();

    //Konstruktorn
    public Inventory(Character owner){
        this.owner = owner;
    }

    //Returnerar en omodifierbar lista av ryggsäckens innehåll
    public List<Equipment> getBackpack(){
        return backpack;
        //return Collections.unmodifiableList(backpack);
    }

    //Returnerar utrustningen från ryggsäcken vid given index
    public Equipment getEquipment(int index){
        return backpack.get(index);
    }

    //Returnerar ägaren
    public Character getOwner(){
        return owner;
    }

    //Lägger till ett nytt item i ryggsäcken
    public void pickupItem(Equipment e){
        backpack.add(e);
    }

    //Tar bort ett item ur ryggsäcken
    public void dropItem(Equipment e){
        backpack.remove(e);
    }

}
