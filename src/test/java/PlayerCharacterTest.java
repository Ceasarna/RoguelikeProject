/*Testar följande klasser:
* Character
* PlayerCharacter*/

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerCharacterTest {

    PlayerCharacter correctPlayer;

    @BeforeEach
    void setUp(){
        correctPlayer = new PlayerCharacter(50, 50, 15, 10, 1, 5, 10, "Hero");
    }

    @Test
    public void testAtkMod(){
        int expectedAtkMod = 50;
        assertEquals(expectedAtkMod, correctPlayer.getAtkMod());
    }

    @Test
    public void testCurrentAtkMod(){
        int expectedCurrentAtkMod = 50;
        assertEquals(expectedCurrentAtkMod, correctPlayer.getCurrentAtkMod());
    }

    @Test
    public void testEvsMod(){
        int expectedEvsMod = 50;
        assertEquals(expectedEvsMod, correctPlayer.getEvsMod());
    }

    @Test
    public void testCurrentEvsMod(){
        int expectedCurrentEvsMod = 50;
        assertEquals(expectedCurrentEvsMod, correctPlayer.getCurrentEvsMod());
    }

    @Test
    public void testMaxHealth(){
        int expectedMaxHealth = 15;
        assertEquals(expectedMaxHealth, correctPlayer.getMaxHealth());
    }

    @Test
    public void testCurrentHealth(){
        int expectedCurrentHealth = 15;
        assertEquals(expectedCurrentHealth, correctPlayer.getCurrentHealth());
    }

    @Test
    public void testGold(){
        int expectedGold = 10;
        assertEquals(expectedGold, correctPlayer.getGold());
    }

    @Test
    public void testBaseDmgMin(){
        int expectedBaseDmgMin = 1;
        assertEquals(expectedBaseDmgMin, correctPlayer.getBaseDmgMin());
    }

    @Test
    public void testBaseDmgMax(){
        int expectedBaseDmgMax = 5;
        assertEquals(expectedBaseDmgMax, correctPlayer.getBaseDmgMax());
    }

    @Test
    public void testSpd(){
        int expectedSpd = 10;
        assertEquals(expectedSpd, correctPlayer.getSpd());
    }

    @Test
    public void testCurrentSpd(){
        int expectedSpd = 10;
        assertEquals(expectedSpd, correctPlayer.getCurrentSpd());
    }

    @Test
    public void testName(){
        String expectedName = "Hero";
        assertEquals(expectedName, correctPlayer.getName());
    }

    @Test
    public void testStartingLvl(){
        int expectedLvl = 1;
        assertEquals(expectedLvl, correctPlayer.getLvl());
    }

    @Test
    public void testMana(){
        int expectedMana = 100;
        assertEquals(expectedMana, correctPlayer.getMana());
    }

    @Test
    public void testExp(){
        int expectedExp = 1000;
        assertEquals(expectedExp, correctPlayer.getExp());
    }

    @Test
    public void incorrectHealthThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new PlayerCharacter(50, 50, 0, 10, 1, 10, 5, "Hero"));
    }

    @Test
    public void incorrectGoldThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new PlayerCharacter(50, 50, 10, -1, 1, 10, 5, "Hero"));
    }

    @Test
    public void negativeBaseDmgMinThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new PlayerCharacter(50, 50, 15, 10, -1, 5, 10, "Hero"));
    }

    @Test
    public void higherMinDmgThanMaxDmgThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new PlayerCharacter(50, 50, 15, 10, 6, 5, 10, "Hero"));
    }

    @Test
    public void nullNameThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new PlayerCharacter(50, 50, 15, 10, 1, 5, 10, null));
    }

    @Test
    public void emptyNameThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new PlayerCharacter(50, 50, 15, 10, 1, 5, 10, ""));
    }

    @Test
    public void incorrectLvlThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new Character(50, 50, 15, 10, 1, 5, 10, "Hero", -1));
    }

    @Test
    public void setDiceRoller(){
        DiceRoller newDiceRoller = new DiceRoller();
        correctPlayer.setDiceRoller(newDiceRoller);
        assertEquals(newDiceRoller, correctPlayer.getDiceRoller());
    }

    @Test
    public void lowerCurrentHealth(){
        int expectedHealth = correctPlayer.getCurrentHealth() - 1;
        correctPlayer.modifyCurrentHealth(-1);
        assertEquals(expectedHealth, correctPlayer.getCurrentHealth());
    }

    @Test
    public void increaseCurrentHealth(){
        int expectedHealth = correctPlayer.getCurrentHealth() - 5;
        correctPlayer.modifyCurrentHealth(-10);
        correctPlayer.modifyCurrentHealth(5);
        assertEquals(expectedHealth, correctPlayer.getCurrentHealth());
    }

    @Test
    public void increasingHealthCapsAtMaxHealth(){
        int expectedHealth = 15;
        correctPlayer.modifyCurrentHealth(-5);
        correctPlayer.modifyCurrentHealth(10);
        assertEquals(expectedHealth, correctPlayer.getCurrentHealth());
    }

    @Test
    public void lowerMaxHealth(){
        int expectedHealth = 10;
        correctPlayer.modifyMaxHealth(-5);
        assertEquals(expectedHealth, correctPlayer.getMaxHealth());
    }

    @Test
    public void increaseMaxHealth(){
        int expectedHealth = 20;
        correctPlayer.modifyMaxHealth(5);
        assertEquals(expectedHealth, correctPlayer.getMaxHealth());
    }

    @Test
    public void increasingMaxHealthAlsoIncreasesCurrentHealth(){
        int expectedHealth = 20;
        correctPlayer.modifyMaxHealth(5);
        assertEquals(expectedHealth, correctPlayer.getCurrentHealth());
    }

    @Test
    public void loweringMaxHealthAlsoLowersCurrentHealth(){
        int expectedHealth = 10;
        correctPlayer.modifyMaxHealth(-5);
        assertEquals(expectedHealth, correctPlayer.getCurrentHealth());
    }

    @Test
    public void loweringMaxHealthCapsAtOne(){
        int expectedHealth = 1;
        correctPlayer.modifyMaxHealth(-20);
        assertEquals(expectedHealth, correctPlayer.getMaxHealth());
    }

    @Test
    public void loweringMaxHealthCapsAtOneAlsoSetsCurrentHealthToOne(){
        int expectedHealth = 1;
        correctPlayer.modifyMaxHealth(-20);
        assertEquals(expectedHealth, correctPlayer.getCurrentHealth());
    }

    @Test
    public void IncreaseCurrentAtkMod(){
        int expectedAtkMod = 60;
        correctPlayer.modifyCurrentAtkMod(10);
        assertEquals(expectedAtkMod, correctPlayer.getCurrentAtkMod());
    }

    @Test
    public void decreaseCurrentAtkMod(){
        int expectedAtkMod = 40;
        correctPlayer.modifyCurrentAtkMod(-10);
        assertEquals(expectedAtkMod, correctPlayer.getCurrentAtkMod());
    }

    @Test
    public void increaseAtkMod(){
        int expectedAtkMod = 60;
        correctPlayer.modifyAtkMod(10);
        assertEquals(expectedAtkMod, correctPlayer.getAtkMod());
    }

    @Test
    public void decreaseAtkMod(){
        int expectedAtkMod = 40;
        correctPlayer.modifyAtkMod(-10);
        assertEquals(expectedAtkMod, correctPlayer.getAtkMod());
    }

    @Test
    public void increaseAtkModAlsoIncreasesCurrentAtkMod(){
        int expectedAtkMod = 60;
        correctPlayer.modifyAtkMod(10);
        assertEquals(expectedAtkMod, correctPlayer.getAtkMod());
    }

    @Test
    public void decreaseAtkModAlsoDecreasesCurrentAtkMod(){
        int expectedAtkMod = 60;
        correctPlayer.modifyAtkMod(10);
        assertEquals(expectedAtkMod, correctPlayer.getAtkMod());
    }

    @Test
    public void increaseCurrentEvsMod(){
        int expectedEvsMod = 60;
        correctPlayer.modifyCurrentEvsMod(10);
        assertEquals(expectedEvsMod, correctPlayer.getCurrentEvsMod());
    }

    @Test
    public void decreaseCurrentEvsMod(){
        int expectedEvsMod = 40;
        correctPlayer.modifyCurrentEvsMod(-10);
        assertEquals(expectedEvsMod, correctPlayer.getCurrentEvsMod());
    }

    @Test
    public void increaseEvsMod(){
        int expectedEvsMod = 60;
        correctPlayer.modifyEvsMod(10);
        assertEquals(expectedEvsMod, correctPlayer.getEvsMod());
    }

    @Test
    public void decreaseEvsMod(){
        int expectedEvsMod = 40;
        correctPlayer.modifyEvsMod(-10);
        assertEquals(expectedEvsMod, correctPlayer.getEvsMod());
    }

    @Test
    public void increaseEvsModAlsoIncreasesCurrentEvsMod(){
        int expectedEvsMod = 60;
        correctPlayer.modifyCurrentEvsMod(10);
        assertEquals(expectedEvsMod, correctPlayer.getCurrentEvsMod());
    }

    @Test
    public void decreaseEvsModAlsoDecreasesCurrentEvsMod(){
        int expectedEvsMod = 40;
        correctPlayer.modifyCurrentEvsMod(-10);
        assertEquals(expectedEvsMod, correctPlayer.getCurrentEvsMod());
    }

    @Test
    public void increaseCurrentSpd(){
        int expectedSpd = 15;
        correctPlayer.modifyCurrentSpd(5);
        assertEquals(expectedSpd, correctPlayer.getCurrentSpd());
    }

    @Test
    public void decreaseCurrentSpd(){
        int expectedSpd = 5;
        correctPlayer.modifyCurrentSpd(-5);
        assertEquals(expectedSpd, correctPlayer.getCurrentSpd());
    }

    @Test
    public void increaseSpd(){
        int expectedSpd = 15;
        correctPlayer.modifySpd(5);
        assertEquals(expectedSpd, correctPlayer.getSpd());
    }

    @Test
    public void decreaseSpd(){
        int expectedSpd = 5;
        correctPlayer.modifySpd(-5);
        assertEquals(expectedSpd, correctPlayer.getSpd());
    }

    @Test
    public void increaseSpdAlsoIncreasesCurrentSpd(){
        int expectedSpd = 15;
        correctPlayer.modifySpd(5);
        assertEquals(expectedSpd, correctPlayer.getCurrentSpd());
    }

    @Test
    public void decreaseSpdAlsoDecreasesCurrentSpd(){
        int expectedSpd = 5;
        correctPlayer.modifySpd(-5);
        assertEquals(expectedSpd, correctPlayer.getCurrentSpd());
    }

    @Test
    public void increaseMana(){
        int expectedMana = 90;
        correctPlayer.modifyCurrentMana(-20);
        correctPlayer.modifyCurrentMana(10);
        assertEquals(expectedMana, correctPlayer.getMana());
    }

    @Test
    public void decreaseMana(){
        int expectedMana = 90;
        correctPlayer.modifyCurrentMana(-10);
        assertEquals(expectedMana, correctPlayer.getMana());
    }

    @Test
    public void increaseManaCapsAtMax(){
        int expectedMana = 100;
        correctPlayer.modifyCurrentMana(10);
        assertEquals(expectedMana, correctPlayer.getMana());
    }

    @Test
    public void decreaseManaCapsAtOne(){
        int expectedMana = 1;
        correctPlayer.modifyCurrentMana(-150);
        assertEquals(expectedMana, correctPlayer.getMana());
    }

    @Test
    public void increaseGold(){
        int expectedGold = 20;
        correctPlayer.addGold(10);
        assertEquals(expectedGold, correctPlayer.getGold());
    }

    @Test
    public void incorrectIncreaseGoldThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> correctPlayer.addGold(-1));
    }

    @Test
    public void decreaseGold(){
        int expectedGold = 5;
        correctPlayer.removeGold(5);
        assertEquals(expectedGold, correctPlayer.getGold());
    }

    @Test
    public void decreaseGoldWithNegativeInput(){
        int expectedGold = 5;
        correctPlayer.removeGold(-5);
        assertEquals(expectedGold, correctPlayer.getGold());
    }

    @Test
    public void decreaseTooMuchGoldThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> correctPlayer.removeGold(20));
    }

    @Test
    public void increaseExp(){
        int expectedExp = 1100;
        correctPlayer.addExp(100);
        assertEquals(expectedExp, correctPlayer.getExp());
    }

    @Test
    public void incorrectIncreaseExpThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> correctPlayer.addExp(0));
    }

    @Test
    public void increaseExpCausesLvlUp(){
        int expectedLvl = 2;
        correctPlayer.addExp(1000);
        assertEquals(expectedLvl, correctPlayer.getLvl());
    }

    @Test
    public void increaseExpCausesMultipleLvlUps(){
        int expectedLvl = 3;
        correctPlayer.addExp(2000);
        assertEquals(expectedLvl, correctPlayer.getLvl());
    }

    @Test
    public void lvlUpIncreasesHealth(){
        int expectedHealth = 20;
        correctPlayer.addExp(1000);
        assertEquals(expectedHealth, correctPlayer.getMaxHealth());
    }

}
