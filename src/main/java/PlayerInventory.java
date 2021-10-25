/*Bygger ut Inventory med ytterligare funktionalitet åt spelaren,
* framförallt förmågan att sätta på sig och ta av sig utrustning*/

public class PlayerInventory extends Inventory {

    //De olika platserna spelaren kan ha utrustning på
    private Weapon weaponSlot;
    private Armour armourSlot;
    private Jewelry jewelrySlot;

    //Konstruktor
    public PlayerInventory(Character owner) {
        super(owner);
    }

    //Returnerar vapnet, eller null om sådant inte finns.
    public Weapon getWeaponSlot() {
        return weaponSlot;
    }

    //Returnerar rustningen, eller null om sådan ej finns
    public Armour getArmourSlot() {
        return armourSlot;
    }

    //Returnerar smycket, eller null om sådan ej finns
    public Jewelry getJewelrySlot() {
        return jewelrySlot;
    }

    //Sätter valt vapen i vapen-slotten och av-equip-ar eventuellt existerande vapen
    public void equipWeapon(Weapon e){
        if(weaponSlot != null){
            unEquipWeapon();
        }
        backpack.remove(e);
        weaponSlot = e;
        owner.modifyCurrentAtkMod(weaponSlot.getAtkBonus());
    }

    //Av-equip-ar nuvarande vapen
    public void unEquipWeapon(){
        if(weaponSlot == null){
            throw new IllegalStateException("No Weapon is Equipped");
        }
        backpack.add(weaponSlot);
        owner.modifyCurrentAtkMod(-weaponSlot.getAtkBonus());
        weaponSlot = null;
    }

    //Sätter vald rustning i rustnings-slotten och av-equip-ar eventuell existerande rustning
    public void equipArmour(Armour e){
        if(armourSlot != null){
            unEquipArmour();
        }
        backpack.remove(e);
        armourSlot = e;
        owner.modifyCurrentEvsMod(armourSlot.getEvsBonus());
        owner.modifyCurrentSpd(armourSlot.getSpdBonus());
    }

    //Av-equip-ar nuvarande rustning
    public void unEquipArmour(){
        if(armourSlot == null){
            throw new IllegalStateException("No Armour is Equipped");
        }
        backpack.add(armourSlot);
        owner.modifyCurrentEvsMod(-armourSlot.getEvsBonus());
        owner.modifyCurrentSpd(-armourSlot.getSpdBonus());
        armourSlot = null;
    }

    //Sätter valt smycke i smyckes-slotten och av-equip-ar eventuellt existerande smycke
    public void equipJewelry(Jewelry e){
        if(jewelrySlot != null){
            unEquipJewelry();
        }
        backpack.remove(e);
        jewelrySlot = e;
        owner.modifyMaxHealth(jewelrySlot.getHealthBonus());
    }

    //Av-equip-ar nuvarande smycke
    public void unEquipJewelry(){
        if(jewelrySlot == null){
            throw new IllegalStateException("No Jewelry is Equipped");
        }
        backpack.add(jewelrySlot);
        owner.modifyMaxHealth(-jewelrySlot.getHealthBonus());
        jewelrySlot = null;
    }

}
