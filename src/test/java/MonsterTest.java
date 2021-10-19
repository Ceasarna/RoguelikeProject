import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonsterTest {
    Goblin actualGoblin;

    @BeforeEach
    void setUp(){
        actualGoblin = new Goblin(10,9,8,7,5,6,4,"GoblinOne");
    }
    @Test
    void testMonsterAtkMod(){
        int expectedAtkMod = 10;
        assertEquals(expectedAtkMod, actualGoblin.getAtkMod());
    }
    @Test
    void testMonsterEvsMod(){
        int expectedEvsMod = 9;
        assertEquals(expectedEvsMod, actualGoblin.getEvsMod());
    }

    @Test
    void testMonsterMaxHealth(){
        int expectedMaxHealth = 8;
        assertEquals(expectedMaxHealth, actualGoblin.getMaxHealth());

    }
    @Test
    void testMonsterGold(){
        int expectedGold = 7;
        assertEquals(expectedGold, actualGoblin.getGold());
    }
    @Test
    void testMonsterBaseDmgMin(){
        int expectedBaseDmgMin = 5;
        assertEquals(expectedBaseDmgMin,actualGoblin.getBaseDmgMin());
    }
    @Test
    void testMonsterBaseDmgMax(){
        int expectedBaseDmgMax = 6;
        assertEquals(expectedBaseDmgMax,actualGoblin.getBaseDmgMax());
    }
    @Test
    void testMonsterSpdMod(){
        int expectedSpd = 4;
        assertEquals(expectedSpd,actualGoblin.getSpd());
    }
    @Test
    void testMonsterName(){
        String expectedName = "GoblinOne";
        assertEquals(expectedName,actualGoblin.getName());
    }

    @Test
    void testEquipDmgSpellForMonster(){
        DamageMagic expectedDmgMagic = new DamageMagic(5,"FireMagic",15,20);

        actualGoblin.spellBook.equipDamageSpell(expectedDmgMagic);

        assertEquals(expectedDmgMagic, actualGoblin.spellBook.getDamageSlot());
    }

    @Test
    void testEquipHealingSpellForMonster(){
        HealingMagic expectedHealingMagic = new HealingMagic(10, "HealingMagic", 40, 50);

        actualGoblin.spellBook.equipHealingSpell(expectedHealingMagic);

        assertEquals(expectedHealingMagic, actualGoblin.spellBook.getHealingSlot());
    }

    @Test
    void testEquipUtilitySpellForMonster(){
        UtilityMagic expectedUtilityMagic = new UtilityMagic(30,"UtilityMagic",50);

        actualGoblin.spellBook.equipUtilitySpell(expectedUtilityMagic);

        assertEquals(expectedUtilityMagic, actualGoblin.spellBook.getUtilitySlot());
    }

}
