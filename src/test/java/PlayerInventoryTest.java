/*Testar Inventory och PlayerInventory*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerInventoryTest {

    PlayerCharacter player;
    Weapon weapon;
    Weapon weapon2;
    Armour armour;
    Armour armour2;
    Jewelry jewelry;
    Jewelry jewelry2;
    Consumable healingPotion;
    Consumable manaPotion;

    @BeforeEach
    void setUp(){
        player = new PlayerCharacter(50, 50, 15, 10, 1, 5, 10, "Hero");
        weapon = new Weapon(10, 5, 10, "Ice");
        weapon2 = new Weapon(10, 10, 15, "Manta Style");
        armour = new Armour(10, 20, "Kingsguard's Armour");
        armour2 = new Armour(5, 15, "Assault Cuirass");
        jewelry = new Jewelry("The One Ring", 15);
        jewelry2 = new Jewelry("Null Talisman", 50);
        healingPotion = new Consumable("Healing Potion", "Health", 10);
        manaPotion = new Consumable("Mana Potion", "Mana", 15);
        player.getInventory().pickupItem(weapon);
        player.getInventory().pickupItem(armour);
        player.getInventory().pickupItem(jewelry);
        player.getInventory().pickupItem(healingPotion);
        player.getInventory().pickupItem(manaPotion);
    }

    @Test
    public void testOwner(){
        assertEquals(player, player.getInventory().getOwner());
    }

    @Test
    public void testBackpackExists(){
        assertNotNull(player.getInventory().getBackpack());
    }

    @Test
    public void testBackpackCanContainItems(){
        assertTrue(player.getInventory().getBackpack().contains(weapon));
    }

    @Test
    public void testBackpackCanContainMultipleItems(){
        Collection<Equipment> expectedItems = new ArrayList<>(Arrays.asList(weapon, armour));
        assertTrue(player.getInventory().getBackpack().containsAll(expectedItems));
    }

    @Test
    public void testGetEquipment(){
        assertEquals(armour, player.getInventory().getEquipment(1));
    }

    @Test
    public void testDropItem(){
        player.getInventory().dropItem(weapon);
        assertFalse(player.getInventory().getBackpack().contains(weapon));
    }

    @Test
    public void testGetWeaponSlot(){
        player.getInventory().equipWeapon(weapon);
        assertEquals(weapon, player.getInventory().getWeaponSlot());
    }

    @Test
    public void testGetArmourSlot(){
        player.getInventory().equipArmour(armour);
        assertEquals(armour, player.getInventory().getArmourSlot());
    }

    @Test
    public void testGetJewelrySlot(){
        player.getInventory().equipJewelry(jewelry);
        assertEquals(jewelry, player.getInventory().getJewelrySlot());
    }

    @Test
    public void testEquippingWeaponBuffsAtk(){
        player.getInventory().equipWeapon(weapon);
        assertEquals(60, player.getCurrentAtkMod());
    }

    @Test
    public void testEquippingArmourBuffsSpd(){
        player.getInventory().equipArmour(armour);
        assertEquals(20, player.getCurrentSpd());
    }

    @Test
    public void testEquippingArmourBuffsEvs(){
        player.getInventory().equipArmour(armour);
        assertEquals(70, player.getCurrentEvsMod());
    }

    @Test
    public void testEquippingJewelryBuffsHealth(){
        player.getInventory().equipJewelry(jewelry);
        assertEquals(30, player.getMaxHealth());
    }

    @Test
    public void testUnEquippingWeapon(){
        player.getInventory().equipWeapon(weapon);
        player.getInventory().unEquipWeapon();
        assertNull(player.getInventory().getWeaponSlot());
    }

    @Test
    public void testUnEquippingWeaponRemovesAtkBuff(){
        player.getInventory().equipWeapon(weapon);
        player.getInventory().unEquipWeapon();
        assertEquals(50, player.getCurrentAtkMod());
    }

    @Test
    public void testUnEquippingArmour(){
        player.getInventory().equipArmour(armour);
        player.getInventory().unEquipArmour();
        assertNull(player.getInventory().getArmourSlot());
    }

    @Test
    public void testUnEquippingArmourRemovesSpdBuff(){
        player.getInventory().equipArmour(armour);
        player.getInventory().unEquipArmour();
        assertEquals(10, player.getCurrentSpd());
    }

    @Test
    public void testUnEquippingArmourRemovesEvsBuff(){
        player.getInventory().equipArmour(armour);
        player.getInventory().unEquipArmour();
        assertEquals(50, player.getCurrentEvsMod());
    }

    @Test
    public void testUnEquippingJewelry(){
        player.getInventory().equipJewelry(jewelry);
        player.getInventory().unEquipJewelry();
        assertNull(player.getInventory().getJewelrySlot());
    }

    @Test
    public void testUnEquippingJewelryRemovesHealthBuff(){
        player.getInventory().equipJewelry(jewelry);
        player.getInventory().unEquipJewelry();
        assertEquals(15, player.getMaxHealth());
    }

    @Test
    public void testEquippingWeaponWithOneAlreadyEquipped(){
        player.getInventory().equipWeapon(weapon2);
        player.getInventory().equipWeapon(weapon);
        assertEquals(weapon, player.getInventory().getWeaponSlot());
    }

    @Test
    public void testEquippingWeaponWithOneAlreadyEquippedDropsItInBackpack(){
        player.getInventory().equipWeapon(weapon2);
        player.getInventory().equipWeapon(weapon);
        assertTrue(player.getInventory().getBackpack().contains(weapon2));
    }

    @Test
    public void testEquippingWeaponWithOneAlreadyEquippedChangesAtkBuff(){
        player.getInventory().equipWeapon(weapon2);
        player.getInventory().equipWeapon(weapon);
        assertEquals(60, player.getCurrentAtkMod());
    }

    @Test
    public void testEquippingArmourWithOneAlreadyEquipped(){
        player.getInventory().equipArmour(armour2);
        player.getInventory().equipArmour(armour);
        assertEquals(armour, player.getInventory().getArmourSlot());
    }

    @Test
    public void testEquippingArmourWithOneAlreadyEquippedDropsItInBackpack(){
        player.getInventory().equipArmour(armour2);
        player.getInventory().equipArmour(armour);
        assertTrue(player.getInventory().getBackpack().contains(armour2));
    }

    @Test
    public void testEquippingArmourWithOneAlreadyEquippedChangesSpdBuff(){
        player.getInventory().equipArmour(armour2);
        player.getInventory().equipArmour(armour);
        assertEquals(20, player.getCurrentSpd());
    }

    @Test
    public void testEquippingArmourWithOneAlreadyEquippedChangesEvsBuff(){
        player.getInventory().equipArmour(armour2);
        player.getInventory().equipArmour(armour);
        assertEquals(70, player.getCurrentEvsMod());
    }

    @Test
    public void testEquippingJewelryWithOneAlreadyEquipped(){
        player.getInventory().equipJewelry(jewelry2);
        player.getInventory().equipJewelry(jewelry);
        assertEquals(jewelry, player.getInventory().getJewelrySlot());
    }

    @Test
    public void testEquippingJewelryWithOneAlreadyEquippedDropsItInBackpack(){
        player.getInventory().equipJewelry(jewelry2);
        player.getInventory().equipJewelry(jewelry);
        assertTrue(player.getInventory().getBackpack().contains(jewelry2));
    }

    @Test
    public void testEquippingJewelryWithOneAlreadyEquippedChangesHealthBuff(){
        player.getInventory().equipJewelry(jewelry2);
        player.getInventory().equipJewelry(jewelry);
        assertEquals(30, player.getMaxHealth());
    }

    @Test
    public void testUnEquippingWeaponWithNoWeaponEquipped(){
        assertThrows(IllegalStateException.class, () -> player.getInventory().unEquipWeapon());
    }

    @Test
    public void testUnEquippingArmourWithNoArmourEquipped(){
        assertThrows(IllegalStateException.class, () -> player.getInventory().unEquipArmour());
    }

    @Test
    public void testUnEquippingJewelryWithNoJewelryEquipped(){
        assertThrows(IllegalStateException.class, () -> player.getInventory().unEquipJewelry());
    }

}
