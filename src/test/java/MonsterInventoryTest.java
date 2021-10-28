/*  Testar:
MonsterInventory */

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


public class MonsterInventoryTest {

    Monster actualMonster;
    Armour armour;
    @BeforeEach
    void setUp(){
        armour = new Armour(5,10,"Big");
        actualMonster = new Monster(10, 9,7, 6, 5,20, 4, "Tom", 2, "Slime");
    }

    @Test
    public void testGetArmourSlot(){
        actualMonster.getInventory().equipArmour(armour);
        assertThat(actualMonster.getInventory().getArmourSlot(), is(equalTo(armour)));
    }

    @Test
    public void testUnEquippingArmour(){
        actualMonster.getInventory().equipArmour(armour);
        actualMonster.getInventory().unEquipArmour();
        assertThat(actualMonster.getCurrentEvsMod(), is(9));
        assertNull(actualMonster.getInventory().getArmourSlot());

    }

    @Test
    public void testGetOwner(){
        assertThat(actualMonster.getInventory().getOwner(), is(equalTo(actualMonster)));
    }

    @Test
    public void testEquipArmourIfArmourIsAlreadyEquipped(){
        Armour armour2 = new Armour(10, 20, "number2");
        actualMonster.getInventory().equipArmour(armour);
        actualMonster.getInventory().equipArmour(armour2);

        assertThat(actualMonster.getInventory().getArmourSlot(), is(armour2));
    }

    @Test
    public void testUnEquippingArmourWhenNoArmourEquippedException(){
        Throwable thrown = assertThrows(IllegalStateException.class, () -> actualMonster.getInventory().unEquipArmour());
        assertThat(thrown.getMessage(), is("No Armour is Equipped"));
    }

    @Test
    public void testPickUpItem(){
        Weapon weapon = new Weapon(2,4,5,"sword");
        actualMonster.getInventory().pickupItem(weapon);
        assertThat(actualMonster.getInventory().getBackpack().size(), is(1));
        assertThat(actualMonster.getInventory().getBackpack().get(0), is(weapon));
        assertThat(actualMonster.getGold(), is(16));
    }

}
