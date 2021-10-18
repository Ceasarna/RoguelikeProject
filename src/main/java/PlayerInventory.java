import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerInventory extends Inventory {

    private Weapon weaponSlot;
    private Armour armourSlot;
    private Jewelry jewelrySlot;

    public PlayerInventory(Character owner) {
        super(owner);
    }

    public Weapon getWeaponSlot() {
        return weaponSlot;
    }

    public Armour getArmourSlot() {
        return armourSlot;
    }

    public Jewelry getJewelrySlot() {
        return jewelrySlot;
    }

    public void equipWeapon(Weapon e){
        if(weaponSlot != null){
            backpack.add(weaponSlot);
        }
        backpack.remove(e);
        weaponSlot = e;
        owner.changeCurrentAtkMod(weaponSlot.getAtkBonus());
    }

    public void unEquipWeapon(){
        if(weaponSlot == null){
            throw new IllegalStateException("No Weapon is Equipped");
        }
        backpack.add(weaponSlot);
        owner.changeCurrentAtkMod(-weaponSlot.getAtkBonus());
        weaponSlot = null;
    }

    public void equipArmour(Armour e){
        if(armourSlot != null){
            backpack.add(armourSlot);
        }
        backpack.remove(e);
        armourSlot = e;
        owner.changeCurrentEvsMod(armourSlot.getEvsBonus());
        owner.changeCurrentSpd(armourSlot.getSpdBonus());
    }

    public void unEquipArmour(){
        if(armourSlot == null){
            throw new IllegalStateException("No Armour is Equipped");
        }
        backpack.add(armourSlot);
        owner.changeCurrentEvsMod(-armourSlot.getEvsBonus());
        owner.changeCurrentSpd(-armourSlot.getSpdBonus());
        armourSlot = null;
    }

    public void equipJewelry(Jewelry e){
        if(jewelrySlot != null){
            backpack.add(jewelrySlot);
        }
        backpack.remove(e);
        jewelrySlot = e;
        owner.changeMaxHealth(jewelrySlot.getHealthBonus());
    }

    public void unEquipJewelry(){
        if(jewelrySlot == null){
            throw new IllegalStateException("No Jewelry is Equipped");
        }
        backpack.add(jewelrySlot);
        owner.changeMaxHealth(-jewelrySlot.getHealthBonus());
        jewelrySlot = null;
    }

}
