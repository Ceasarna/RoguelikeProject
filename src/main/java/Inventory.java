import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {

    protected Character owner;
    protected ArrayList<Equipment> backpack = new ArrayList<>();

    public Inventory(Character owner){
        this.owner = owner;
    }

    public List<Equipment> getBackpack(){
        return Collections.unmodifiableList(backpack);
    }

    public void pickupItem(Equipment e){
        backpack.add(e);
    }

    public void dropItem(Equipment e){
        backpack.remove(e);
    }

}
