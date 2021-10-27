/*
Testar:
Monster
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonsterTest {
    Monster actualGoblin;
    DamageMagic damageMagic;
    HealingMagic healingMagic;
    UtilityMagic utilityMagic;

    @BeforeEach
    void setUp(){
        damageMagic = new DamageMagic(15, "Dark", "DamageM", 15, 25);
        healingMagic = new HealingMagic(10, "HealingM", 10, 30);
        utilityMagic = new UtilityMagic(20, "UtilityM", 50);
        actualGoblin = new Monster(10,9,8,7,5,6,4,"GoblinOne", 1, "Goblin");
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
    void testDecisionMakerWithHealingSpellAndBelow50Percent(){
        HealingMagic expectedHealingMagic = healingMagic;
        int expectedDecisionNumber = 4;
        int actualDecisionNumber;
        actualGoblin.modifyCurrentHealth(-7);
        actualGoblin.spellBook.equipHealingSpell(healingMagic);

        actualDecisionNumber = actualGoblin.decisionMaker();
        assertEquals(expectedDecisionNumber, actualDecisionNumber);

        Magic actualMagicUsed = actualGoblin.useMagic();
        assertEquals(expectedHealingMagic, actualMagicUsed);

    }

    @Test
    void testDecisionMakerAbove50PercentWithHealingSpell(){
        int expectedDecisionNumber = 1;
        int actualDecisionNumber;
        actualGoblin.spellBook.equipHealingSpell(healingMagic);

        actualDecisionNumber = actualGoblin.decisionMaker();
        assertEquals(expectedDecisionNumber, actualDecisionNumber);
    }

    @Test
    void testDecisionMakerHasDamageSpell(){
        int expectedDecisionNumber = 4;
        int actualDecisionNumber;
        actualGoblin.spellBook.equipDamageSpell(damageMagic);

        actualDecisionNumber = actualGoblin.decisionMaker();
        assertEquals(expectedDecisionNumber, actualDecisionNumber);
    }

    @Test
    void testDecisionMakerHasUtilitySpell(){
        int expectedDecisionNumber = 4;
        int actualDecisionNumber;
        actualGoblin.spellBook.equipUtilitySpell(utilityMagic);

        actualDecisionNumber = actualGoblin.decisionMaker();
        assertEquals(expectedDecisionNumber, actualDecisionNumber);
    }

    @Test
    void testDecisionMakerWithNoSpells(){
        int expectedDecisionNumber = 1;
        int actualDecisionNumber;

        actualDecisionNumber = actualGoblin.decisionMaker();
        assertEquals(expectedDecisionNumber, actualDecisionNumber);
    }

    @Test
    void testUseMagicWithNoSpellsException(){
        assertThrows(IllegalStateException.class, () -> actualGoblin.useMagic());
    }

    @Test
    void testUseMagicAllDifferentSpellsEquippedAndBelow50Percent(){
        HealingMagic expectedHealing = healingMagic;
        Magic actualMagic;

        actualGoblin.modifyCurrentHealth(-7);
        actualGoblin.spellBook.equipDamageSpell(damageMagic);
        actualGoblin.spellBook.equipHealingSpell(healingMagic);
        actualGoblin.spellBook.equipUtilitySpell(utilityMagic);

        actualGoblin.decisionMaker();
        actualMagic = actualGoblin.useMagic();

        assertInstanceOf(HealingMagic.class , actualMagic);
        assertEquals(expectedHealing, actualMagic);
    }

    @Test
    void testUseMagicAllDifferentSpellsEquippedAndAbove50Percent(){
        DamageMagic expectedDamage = damageMagic;
        Magic actualMagic;

        actualGoblin.spellBook.equipDamageSpell(damageMagic);
        actualGoblin.spellBook.equipHealingSpell(healingMagic);
        actualGoblin.spellBook.equipUtilitySpell(utilityMagic);

        actualGoblin.decisionMaker();
        actualMagic = actualGoblin.useMagic();

        assertInstanceOf(DamageMagic.class , actualMagic);
        assertEquals(expectedDamage, actualMagic);
    }

    @Test
    void testUseMagicDamageAndUtilityEquipped(){
        DamageMagic expectedDamage = damageMagic;
        Magic actualMagic;

        actualGoblin.spellBook.equipDamageSpell(damageMagic);
        actualGoblin.spellBook.equipUtilitySpell(utilityMagic);

        actualGoblin.decisionMaker();
        actualMagic = actualGoblin.useMagic();

        assertInstanceOf(DamageMagic.class , actualMagic);
        assertEquals(expectedDamage, actualMagic);
    }

    @Test
    void testUseMagicOnlyUtilityEquipped(){
        UtilityMagic expectedUtility = utilityMagic;
        Magic actualMagic;

        actualGoblin.spellBook.equipUtilitySpell(utilityMagic);

        actualGoblin.decisionMaker();
        actualMagic = actualGoblin.useMagic();

        assertInstanceOf(UtilityMagic.class , actualMagic);
        assertEquals(expectedUtility, actualMagic);
    }
}