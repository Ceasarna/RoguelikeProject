/*  Testar:
* SpellBook
* Magic */

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpellBookTest {

    Character character;
    SpellBook correctSpellBook;

    @BeforeEach
    void setUp(){
        character = new Character(50, 40,100, 10, 1, 5, 10, "TestChar", 5);
        correctSpellBook = new SpellBook(character);
    }

    @Test
    public void testSpellCostBoundaryLower(){
        Magic utilityMagic = new UtilityMagic(0, "Util", 35);
        int expectedSpellCost = 0;
        assertThat(utilityMagic.getSpellCost(), is(equalTo(expectedSpellCost)));

    }

    @Test
    public void testSpellCostBoundaryUpper(){
        Magic utilityMagic = new UtilityMagic(100, "Util", 35);
        int expectedSpellCost = 100;
        assertThat(utilityMagic.getSpellCost(), is(equalTo(expectedSpellCost)));

    }

    @Test
    public void testSpellCost(){
        Magic utilityMagic = new UtilityMagic(50, "Util", 35);
        int expectedSpellCost = 50;
        assertThat(utilityMagic.getSpellCost(), is(equalTo(expectedSpellCost)));
    }

    @Test
    public void testSpellName(){
        Magic utilityMagic = new UtilityMagic(10, "Util", 35);
        String expectedSpellName = "Util";
        assertThat(utilityMagic.getSpellName(), is(equalTo(expectedSpellName)));
    }

    @Test
    public void testTooLowSpellCostException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new UtilityMagic(-5, "util", 50));

        assertThat(thrown.getMessage(), is(equalTo("Cost of spell cannot be under 0 or over 100")));
    }

    @Test
    public void testTooHighSpellCostException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new UtilityMagic(150, "util", 50));

        assertThat(thrown.getMessage(), is(equalTo("Cost of spell cannot be under 0 or over 100")));
    }

    @Test
    public void testOwnership(){
        assertThat(correctSpellBook.getOwner(), is(equalTo(character)));
    }

    @Test
    public void testPickUpSpell(){
        UtilityMagic utilityMagic = new UtilityMagic(10, "h", 50);
        correctSpellBook.pickUpSpell(utilityMagic);

        assertThat(correctSpellBook.getSpellBook().size(), is(1));
        assertThat(correctSpellBook.getSpellBook().get(0), is(utilityMagic));
    }

    @Test
    public void testDropSpell(){
        UtilityMagic utilityMagic = new UtilityMagic(10, "h", 50);
        correctSpellBook.pickUpSpell(utilityMagic);
        correctSpellBook.dropSpell(utilityMagic);

        assertThat(correctSpellBook.getSpellBook().size(), is(0));
    }

    @Test
    public void testThatThereExistsASpellBookInsideSpellBook(){
        assertThat(correctSpellBook.getSpellBook(), is(notNullValue()));
    }

    @Test
    public void testEquipDamageSpellAndGetDamageSlot(){

        DamageMagic damageMagic = new DamageMagic(10, "Dark", "DarkMgaic", 5, 10);

        correctSpellBook.equipDamageSpell(damageMagic);

        assertThat(correctSpellBook.getDamageSlot(), is(equalTo(damageMagic)));
    }

    @Test
    public void testEquipHealingSpellAndGetHealingSlot(){
        HealingMagic healingMagic = new HealingMagic(35, "HealingSpell", 10, 20);

        correctSpellBook.equipHealingSpell(healingMagic);

        assertThat(correctSpellBook.getHealingSlot(), is(equalTo(healingMagic)));
    }

    @Test
    public void testEquipUtilitySpellAndGetUtilitySlot(){
        UtilityMagic utilityMagic = new UtilityMagic(25, "Utility", 30);

        correctSpellBook.equipUtilitySpell(utilityMagic);

        assertThat(correctSpellBook.getUtilitySlot(), is(equalTo(utilityMagic)));
    }

    @Test
    public void testUnequipDamageSpellAndDamageSlotIsNull(){
        DamageMagic damageMagic = new DamageMagic(10, "Dark", "DarkMgaic", 5, 10);
        correctSpellBook.equipDamageSpell(damageMagic);

        correctSpellBook.unEquipDamageSpell();

        assertThat(correctSpellBook.getDamageSlot(), is(equalTo(null)));
    }

    @Test
    public void testUnequipHealingSpellAndHealingslotIsNull(){
        HealingMagic healingMagic = new HealingMagic(10, "HealingSpell", 10, 15);
        correctSpellBook.equipHealingSpell(healingMagic);

        correctSpellBook.unEquipHealingSpell();

        assertThat(correctSpellBook.getHealingSlot(), is(equalTo(null)));}

    @Test
    public void testUnequipUtilitySpellAndUtilitySlotIsNull(){
        UtilityMagic utilityMagic = new UtilityMagic(25, "Utility", 35);
        correctSpellBook.equipUtilitySpell(utilityMagic);

        correctSpellBook.unEquipUtilitySpell();

        assertThat(correctSpellBook.getUtilitySlot(), is(equalTo(null)));
    }

    @Test
    public void testEquipDamageSpellWhenDamageSpellAlreadyEquippedAndPutFirstSpellIntoSpellBook(){
        DamageMagic damageMagic1 = new DamageMagic(10, "Dark", "DarkMgaic", 5, 10);
        DamageMagic damageMagic2 = new DamageMagic(15, "Light", "LightMagic", 10, 15);

        correctSpellBook.equipDamageSpell(damageMagic1);
        correctSpellBook.equipDamageSpell(damageMagic2);

        List<Magic> listOfMagic = correctSpellBook.getSpellBook();

        assertThat(listOfMagic.get(0), is(equalTo(damageMagic1)));
        assertThat(correctSpellBook.getDamageSlot(), is(equalTo(damageMagic2)));
    }

    @Test
    public void testEquipHealingSpellWhenHealingSpellAlreadyEquippedAndPutFirstSpellIntoSpellBook(){
        HealingMagic healingMagic1 = new HealingMagic(10, "HealingSpellFirst", 10, 15);
        HealingMagic healingMagic2 = new HealingMagic(15, "HealingSpellSecond", 15, 30);

        correctSpellBook.equipHealingSpell(healingMagic1);
        correctSpellBook.equipHealingSpell(healingMagic2);

        List<Magic> listOfMagic = correctSpellBook.getSpellBook();

        assertThat(listOfMagic.get(0), is(equalTo(healingMagic1)));
        assertThat(correctSpellBook.getHealingSlot(), is(equalTo(healingMagic2)));

    }

    @Test
    public void testEquipUtilitySpellWhenUtilitySpellAlreadyEquippedAndPutFirstSpellIntoSpellBook(){
        UtilityMagic utilityMagic1 = new UtilityMagic(25, "UtilityFirst", 50);
        UtilityMagic utilityMagic2 = new UtilityMagic(40, "UtilitySecond", 60);

        correctSpellBook.equipUtilitySpell(utilityMagic1);
        correctSpellBook.equipUtilitySpell(utilityMagic2);

        List<Magic> listOfMagic = correctSpellBook.getSpellBook();

        assertThat(listOfMagic.get(0), is(equalTo(utilityMagic1)));
        assertThat(correctSpellBook.getUtilitySlot(), is(equalTo(utilityMagic2)));
    }

    @Test
    public void unEquippingDamageSpellWhenThereIsNoDamageSpellEquippedException(){

        Throwable thrown = assertThrows(IllegalStateException.class, () -> correctSpellBook.unEquipDamageSpell());

        assertThat(thrown.getMessage(), is(equalTo("No DamageMagic is Equipped")));
    }

    @Test
    public void unEquippingHealingSpellWhenThereIsNoHealingSpellEquippedException(){

        Throwable thrown = assertThrows(IllegalStateException.class, () -> correctSpellBook.unEquipHealingSpell());

        assertThat(thrown.getMessage(), is(equalTo("No HealingMagic is Equipped")));
    }

    @Test
    public void unEquippingUtilitySpellWhenThereIsNoUtilitySpellEquippedException(){

        Throwable thrown = assertThrows(IllegalStateException.class, () -> correctSpellBook.unEquipUtilitySpell());

        assertThat(thrown.getMessage(), is(equalTo("No UtilityMagic is Equipped")));
    }

    @Test
    public void testCorrectAmountOfSpellsInSpellBook(){
        DamageMagic damageMagic1 = new DamageMagic(31, "Dark", "DarkDamageMagic", 15, 40);
        DamageMagic damageMagic2 = new DamageMagic(32, "Dark", "DarkDamageMagic", 15, 40);
        DamageMagic damageMagic3 = new DamageMagic(33, "Dark", "DarkDamageMagic", 15, 40);
        DamageMagic damageMagic4 = new DamageMagic(33, "Dark", "DarkDamageMagic", 15, 40);

        HealingMagic healingMagic1 = new HealingMagic(10, "HealingSpellFirst", 10, 15);
        HealingMagic healingMagic2 = new HealingMagic(15, "HealingSpellSecond", 15, 30);

        UtilityMagic utilityMagic1 = new UtilityMagic(41, "UtilitySecond", 60);
        UtilityMagic utilityMagic2 = new UtilityMagic(42, "UtilitySecond", 60);
        UtilityMagic utilityMagic3 = new UtilityMagic(43, "UtilitySecond", 60);

        correctSpellBook.equipDamageSpell(damageMagic1);
        correctSpellBook.equipDamageSpell(damageMagic2);
        correctSpellBook.equipDamageSpell(damageMagic3);
        correctSpellBook.equipDamageSpell(damageMagic4);

        correctSpellBook.equipHealingSpell(healingMagic1);
        correctSpellBook.equipHealingSpell(healingMagic2);

        correctSpellBook.equipUtilitySpell(utilityMagic1);
        correctSpellBook.equipUtilitySpell(utilityMagic2);
        correctSpellBook.equipUtilitySpell(utilityMagic3);

        correctSpellBook.unEquipDamageSpell();

        assertThat(correctSpellBook.getSpellBook().size(), is(equalTo(7)));
    }

    @Test
    public void testUseDamageSpell(){
        DamageMagic damageMagic1 = new DamageMagic(10, "Dark", "DarkMagic", 5, 10);
        correctSpellBook.equipDamageSpell(damageMagic1);

        DamageMagic damageMagicCopy = correctSpellBook.useDamageSpell();

        assertThat(correctSpellBook.getDamageSlot(), is(equalTo(damageMagicCopy)));
    }

    @Test
    public void testUseHealingSpell(){
        HealingMagic healingMagic = new HealingMagic(10, "HealMagic", 5, 10);

        correctSpellBook.equipHealingSpell(healingMagic);

        HealingMagic healingMagicCopy = correctSpellBook.useHealingSpell();

        assertThat(correctSpellBook.getHealingSlot(), is(equalTo(healingMagicCopy)));
    }

    @Test
    public void testUseUtilitySpell(){
        UtilityMagic utilityMagic2 = new UtilityMagic(40, "UtilitySecond", 60);

        correctSpellBook.equipUtilitySpell(utilityMagic2);

        UtilityMagic utilityMagicCopy = correctSpellBook.useUtilitySpell();

        assertThat(correctSpellBook.getUtilitySlot(), is(equalTo(utilityMagicCopy)));
    }
}

