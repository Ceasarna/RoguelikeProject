// Ett utrymme för magi att sparas och påverka karaktären

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellBook {

    // Platser för de olika typer av magi som en karaktär kan erhålla
    private DamageMagic damageSlot;
    private HealingMagic healingSlot;
    private UtilityMagic utilitySlot;

    // Ägaren av detta utrymme
    private final Character owner;

    // Lista som används för magi som spelaren vill behålla
    final ArrayList<Magic> spellBook = new ArrayList<>();

    // Konstruktor
    public SpellBook(Character owner){
        this.owner = owner;
    }

    // Returnerar magi för skada, annars null
    public DamageMagic getDamageSlot(){
        return damageSlot;
    }

    // Returnerar magi för återupphämtning, annars null
    public HealingMagic getHealingSlot(){
        return healingSlot;
    }

    // Returnerar magi för funktionalitet, annars null
    public UtilityMagic getUtilitySlot(){
        return utilitySlot;
    }

    // Lägger till magi till listan av magi
    public void pickUpSpell(Magic spell){
        spellBook.add(spell);
    }

    // Tar bort magi från listan av magi
    public void dropSpell(Magic spell){
        spellBook.remove(spell);
    }

    // Hämtar en omodifierbar lista utav listan av magi
    public List<Magic> getSpellBook() {
        return Collections.unmodifiableList(spellBook);
    }

    // Get-metod
    public Character getOwner(){
        return this.owner;
    }

    // Returnera en damageSlot, i användning för Combat metoder
    public DamageMagic useDamageSpell(){
        checkDamageSlot();
        return getDamageSlot();
    }

    // Kollar att platsen för skademagi inte är null
    private void checkDamageSlot(){
        if(damageSlot == null){
            throw new IllegalStateException("No DamageMagic is Equipped");
        }
    }

    // Returnerar en kopia av magin som återupphämtar, i användning för Combat metoder
    public HealingMagic useHealingSpell(){
        checkHealingSlot();
        return getHealingSlot();
    }

    // Kollar att platsen för återupphämtningsmagi inte är null
    private void checkHealingSlot(){
        if(healingSlot == null){
            throw new IllegalStateException("No HealingMagic is Equipped");
        }
    }

    // Returnerar en kopia av magin för funktionalitet, i användning för Combat metoder
    public UtilityMagic useUtilitySpell(){
        checkUtilitySlot();
        return getUtilitySlot();
    }

    // Kollar att platsen för funktionalitetsmagi inte är null
    private void checkUtilitySlot(){
        if(utilitySlot == null){
            throw new IllegalStateException("No UtilityMagic is Equipped");
        }
    }

    // Sätter vald skademagi i skademagi-platsen, samt lägger undan redan placerad skademagi ifall den existerar
    public void equipDamageSpell(DamageMagic dmgSpell){
        if(damageSlot != null){
            spellBook.add(damageSlot);
        }
        spellBook.remove(dmgSpell);
        damageSlot = dmgSpell;

        //characterLevel sets new values inside the DamageMagic.
        int characterLevel = owner.getLvl();
        damageSlot.setMinDmg(characterLevel);
        damageSlot.setMaxDmg(characterLevel);
    }

    // Tar av nuvarande skademagi
    public void unEquipDamageSpell(){
        checkDamageSlot();
        spellBook.add(damageSlot);

        //characterLevel sätter nytt värde inuti DamageMagic.
        double characterLevel = owner.getLvl();
        damageSlot.setMinDmg( 1 / characterLevel);
        damageSlot.setMaxDmg( 1 / characterLevel);

        damageSlot = null;
    }

    // Sätter vald återhämtningsmagi i återhämtningsmagi-platsen, samt lägger undan redan placerad
    // återhämtningsmagi ifall den existerar
    public void equipHealingSpell(HealingMagic healSpell){
        if(healingSlot != null){
            spellBook.add(healingSlot);
        }
        spellBook.remove(healSpell);
        healingSlot = healSpell;

        //characterLevel sätter nya värdet inuti HealingMagic.
        int characterLevel = owner.getLvl();
        healingSlot.setMinHeal(characterLevel);
        healingSlot.setMaxHeal(characterLevel);
    }

    // Tar av nuvarande återhämtningsmagi
    public void unEquipHealingSpell(){
        checkHealingSlot();
        spellBook.add(healingSlot);

        //characterLevel sätter nya värdet inuti HealingMagic.
        double characterLevel = owner.getLvl();
        healingSlot.setMinHeal( 1 / characterLevel);
        healingSlot.setMaxHeal( 1 / characterLevel);

        healingSlot = null;
    }

    // Sätter vald funktionalitetsmagi i funktionalitetsmagi-platsen, samt lägger undan redan placerad
    // funktionalitetsmagi ifall den existerar
    public void equipUtilitySpell(UtilityMagic utilitySpell){
        if(utilitySlot != null){
            spellBook.add(utilitySlot);
        }
        spellBook.remove(utilitySpell);
        utilitySlot = utilitySpell;

        int characterLevel = owner.getLvl();
        utilitySlot.setUtilityValue(characterLevel);
    }

    // Tar av nuvarande funktionalitetsmagi
    public void unEquipUtilitySpell(){
        checkUtilitySlot();
        spellBook.add(utilitySlot);

        double characterLevel = owner.getLvl();
        utilitySlot.setUtilityValue(1 / characterLevel);

        utilitySlot = null;
    }
}
