/*Testar följande klasser:
* -Equipment
* -Consumable
* -Weapon
* -Armour
* -Jewelry*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {

    PlayerCharacter player;
    Weapon weapon;
    Armour armour;
    Jewelry jewelry;
    Consumable healingPotion;
    Consumable manaPotion;

    @BeforeEach
    void setUp(){
        player = new PlayerCharacter(50, 50, 15, 10, 1, 5, 10, "Hero");
        weapon = new Weapon(10, 5, 10, "Ice");
        armour = new Armour(10, 20, "Kingsguard's Armour");
        jewelry = new Jewelry("The One Ring", 15);
        healingPotion = new Consumable("Healing Potion", "Health", 10);
        manaPotion = new Consumable("Mana Potion", "Mana", 15);
        player.getInventory().pickupItem(healingPotion);
        player.getInventory().pickupItem(manaPotion);
    }

    @Test
    public void testGetEquipmentName(){
        String expectedName = "Ice";
        assertEquals(expectedName, weapon.getName());
    }

    @Test
    public void testWeaponConstructorWithIncorrectMinDmgThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new Weapon(10, -2, 10, "Battlefury"));
    }

    @Test
    public void testWeaponConstructorWithIncorrectMaxDmgThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new Weapon(10, 10, 9, "Battlefury"));
    }

    @Test
    public void testWeaponGetAtkBonus(){
        int expectedAtkBonus = 10;
        assertEquals(expectedAtkBonus, weapon.getAtkBonus());
    }

    @Test
    public void testWeaponGetDmgMin(){
        int expectedDmgMin = 5;
        assertEquals(expectedDmgMin, weapon.getDmgMin());
    }

    @Test
    public void testWeaponGetDmgMax(){
        int expectedDmgMax = 10;
        assertEquals(expectedDmgMax, weapon.getDmgMax());
    }

    @Test
    public void testArmourGetSpdBonus(){
        int expectedSpdBonus = 10;
        assertEquals(expectedSpdBonus, armour.getSpdBonus());
    }

    @Test
    public void testArmourGetEvsBonus(){
        int expectedEvsBonus = 20;
        assertEquals(expectedEvsBonus, armour.getEvsBonus());
    }

    @Test
    public void testJewelryGetHealthBonus(){
        int expectedHealthBonus = 15;
        assertEquals(expectedHealthBonus, jewelry.getHealthBonus());
    }

    @Test
    public void testConsumeHealthPotion(){
        int expectedHealth = 15;
        player.modifyCurrentHealth(-10);
        healingPotion.useItem(player);
        assertEquals(expectedHealth, player.getCurrentHealth());
    }

    @Test
    public void testConsumeHealthPotionRemovesItem(){
        player.modifyCurrentHealth(-10);
        healingPotion.useItem(player);
        assertFalse(player.getInventory().getBackpack().contains(healingPotion));
    }

    @Test
    public void testConsumeManaPotion(){
        int expectedMana = 95;
        player.modifyCurrentMana(-20);
        manaPotion.useItem(player);
        assertEquals(expectedMana, player.getMana());
    }

    @Test
    public void testConsumeManaPotionRemovesItem(){
        player.modifyCurrentMana(-20);
        manaPotion.useItem(player);
        assertFalse(player.getInventory().getBackpack().contains(manaPotion));
    }

}
