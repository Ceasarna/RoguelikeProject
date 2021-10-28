// Ett utrymme för Monster att ha på sig rustning, samt ha olika typer av equipments inuti en Lista

public class MonsterInventory extends Inventory {

    // Monster kan enbart ha en rustning
    private Armour armourSlot;

    // Konstruktor
    public MonsterInventory(Monster owner) {
        super(owner);
    }

    //Returnerar rustningen, eller null om sådan ej finns
    public Armour getArmourSlot() {
        return armourSlot;
    }

    // Sätter vald Armour i Armour-platsen, samt lägger undan redan placerad Armour ifall den existerar
    public void equipArmour(Armour e){
        if(armourSlot != null){
            unEquipArmour();
        }
        backpack.remove(e);
        armourSlot = e;
        owner.modifyCurrentEvsMod(armourSlot.getEvsBonus());
        owner.modifyCurrentSpd(armourSlot.getSpdBonus());
    }

    //Tar av nuvarande Armour
    public void unEquipArmour(){
        if(armourSlot == null){
            throw new IllegalStateException("No Armour is Equipped");
        }
        backpack.add(armourSlot);
        owner.modifyCurrentEvsMod(-armourSlot.getEvsBonus());
        owner.modifyCurrentSpd(-armourSlot.getSpdBonus());
        armourSlot = null;
    }

    // Monster tar upp items som samtidigt ökar monstrets värde i guld
    @Override
    public void pickupItem(Equipment e){
        backpack.add(e);
        ((Monster)owner).addGold(10);
    }
}
