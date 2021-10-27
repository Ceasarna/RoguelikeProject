import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CombatTest{


private PlayerCharacter pc;
private Monster monster;
private DiceRoller diceRoller;
private Combat encounter;
private PlayerInput input;

    @Before
    public void setUp() {
        pc = new PlayerCharacter(50, 60, 50, 0, 2, 4, 50, "Hero");
        monster = new Monster(50, 60, 60, 5, 5, 7, 40, "Boblin", 2, "Goblin");
        encounter = new Combat(pc, monster);
        diceRoller = mock(DiceRoller.class);
        input = mock(PlayerInput.class);
        pc.setDiceRoller(diceRoller);
        monster.setDiceRoller(diceRoller);
        encounter.setDiceRoller(diceRoller);
        encounter.setPlayerInput(input);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void addPlayerCharacter() {
        assertEquals(pc, encounter.getPc());
    }

    @Test
    public void addMonster() {
        assertEquals(monster, encounter.getMonster());
    }

    @Test
    public void createInitiative() {
        when(diceRoller.roll1d100()).thenReturn(50);
        Character[] expected = new Character[2];
        expected[0] = pc;
        expected[1] = monster;
        Character[] initiative = encounter.createInitiative();
        assertArrayEquals(expected, initiative);
        verify(diceRoller, times(2)).roll1d100();
    }

    @Test
    public void pcHealthZero(){
        pc.modifyCurrentHealth(-50);
        assertFalse(encounter.isAlive(pc));
    }

    @Test
    public void pcHealthMax(){
        assertTrue(encounter.isAlive(pc));
    }

    @Test
    public void monsterHealthZero(){
        monster.modifyCurrentHealth(-60);
        assertFalse(encounter.isAlive(monster));
    }

    @Test
    public void monsterHealthMax(){
        assertTrue(encounter.isAlive(monster));
    }

    @Test
    public void pcTakeDamage(){
        encounter.takeDamage(monster, pc, -5);
        assertEquals(45, pc.getCurrentHealth());
    }

    @Test
    public void pcTakePositiveDamage(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        encounter.takeDamage(monster, pc, 5);});
        String expected = "Damage can't be positive";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void pcWins(){
        encounter.takeDamage(pc, monster, -60);
        assertTrue(encounter.victory);
        assertEquals(5, pc.getGold());
        assertEquals(1200, pc.getExp());
    }

    @Test
    public void monsterWins(){
        encounter.takeDamage(monster, pc, -55);
        assertTrue(encounter.victory);
    }

    @Test
    public void pcAttackHit(){
        when(diceRoller.roll1d100()).thenReturn(60);
        when(diceRoller.rollWithinRange(2, 4)).thenReturn(4);
        encounter.attack(pc, monster);
        assertEquals(56, monster.getCurrentHealth());
    }

    @Test
    public void pcAttackMiss(){
        when(diceRoller.roll1d100()).thenReturn(5);
        encounter.attack(pc, monster);
        assertEquals(60, monster.getCurrentHealth());
    }

    @Test
    public void monsterAttackHit(){
        when(diceRoller.roll1d100()).thenReturn(50);
        when(diceRoller.rollWithinRange(5, 7)).thenReturn(5);
        encounter.attack(monster, pc);
        assertEquals(45, pc.getCurrentHealth());
    }

    @Test
    public void monsterAttackMiss(){
        when(diceRoller.roll1d100()).thenReturn(5);
        encounter.attack(monster, pc);
        assertEquals(50, pc.getCurrentHealth());
    }

    @Test
    public void pcDodge(){
        encounter.dodge(pc);
        assertEquals(120, pc.getCurrentEvsMod());
    }

    @Test
    public void monsterDodge(){
        encounter.dodge(monster);
        assertEquals(120, monster.getCurrentEvsMod());
    }

    @Test
    public void pcAttackStart(){
        when(diceRoller.roll1d100()).thenReturn(50);
        when(diceRoller.rollWithinRange(2, 4)).thenReturn(4);
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        encounter.playerTurn();
        assertEquals(56, monster.getCurrentHealth());
    }

    @Test
    public void monsterAttackStart(){
        when(diceRoller.roll1d100()).thenReturn(50);
        when(diceRoller.rollWithinRange(5, 7)).thenReturn(6);
        encounter.monsterTurn();
        assertEquals(44, pc.getCurrentHealth());
    }

    @Test
    public void pcUseManaPotion(){
        Consumable e = new Consumable("Healing Potion", "Health", 10);
        Consumable e2 = new Consumable("Mana Potion", "Mana", 25);
        pc.modifyCurrentMana(-50);
        pc.getInventory().getBackpack().add(e);
        pc.getInventory().getBackpack().add(e2);
        encounter.useItem(pc, 2);
        assertEquals(75, pc.getMana());
        assertEquals(1, pc.getInventory().getBackpack().size());

    }

    @Test
    public void pcUseHealthPotion(){
        Consumable e = new Consumable("Healing Potion", "Health", 10);
        Consumable e2 = new Consumable("Mana Potion", "Mana", 25);
        pc.modifyCurrentHealth(-20);
        pc.getInventory().getBackpack().add(e);
        pc.getInventory().getBackpack().add(e2);
        encounter.useItem(pc, 1);
        assertEquals(40, pc.getCurrentHealth());
        assertEquals(1, pc.getInventory().getBackpack().size());
    }

    @Test
    public void pcHasNoItem(){
        String input = "3";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            encounter.turn(pc, monster, 3);});
        String expected = "You have no items.";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void looting(){
        Weapon e = new Weapon(5, 5, 7, "Sword");
        monster.getInventory().getBackpack().add(e);
        encounter.takeDamage(pc, monster, -70);
        assertEquals(5, pc.getGold());
        assertEquals(1, pc.getInventory().getBackpack().size());
    }

    @Test
    public void noLoot(){
        Monster monster2 = new Monster(50, 60, 60, 0, 5, 7, 40, "Roblin", 2, "Goblin");
        Combat encounter2 = new Combat(pc, monster2);
        encounter2.takeDamage(pc, monster2, -70);
        assertEquals(0, pc.getGold());
        assertEquals(0, pc.getInventory().getBackpack().size());
    }


}


