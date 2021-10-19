import java.util.ArrayList;

public class SpellBook {

    private DamageMagic damageSlot;
    private HealingMagic healingSlot;
    private UtilityMagic utilitySlot;

    ArrayList<Magic> spellBook = new ArrayList<>();

    public DamageMagic getDamageSlot(){
        return damageSlot;
    }

    public HealingMagic getHealingSlot(){
        return healingSlot;
    }

    public UtilityMagic getUtilitySlot(){
        return utilitySlot;
    }

    public void pickUpSpell(Magic spell){
        spellBook.add(spell);
    }

    public void dropSpell(Magic spell){
        spellBook.remove(spell);
    }

    public void equipDamageSpell(DamageMagic dmgSpell){
        if(damageSlot != null){
            spellBook.add(damageSlot);
        }
        spellBook.remove(dmgSpell);
        damageSlot = dmgSpell;
    }

    public void unEquipDamageSpell(){
        if(damageSlot == null){
            throw new IllegalStateException("No DamageMagic is Equipped");
        }
        spellBook.add(damageSlot);
        damageSlot = null;
    }

    public void equipHealingSpell(HealingMagic healSpell){
        if(healingSlot != null){
            spellBook.add(healingSlot);
        }
        spellBook.remove(healSpell);
        healingSlot = healSpell;
    }

    public void unEquipHealingSpell(){
        if(healingSlot == null){
            throw new IllegalStateException("No HealingMagic is Equipped");
        }
        spellBook.add(healingSlot);
        healingSlot = null;
    }

    public void equipUtilitySpell(UtilityMagic utilitySpell){
        if(utilitySlot != null){
            spellBook.add(utilitySlot);
        }
        spellBook.remove(utilitySpell);
        utilitySlot = utilitySpell;
    }

    public void unEquipUtilitySpell(){
        if(utilitySlot == null){
            throw new IllegalStateException("No utilityMagic is Equipped");
        }
        spellBook.add(utilitySlot);
        utilitySlot = null;
    }

    public void writeAllSpells(){

    }
}
