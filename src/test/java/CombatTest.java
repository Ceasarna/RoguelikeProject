import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


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

    @Before
    public void setUp() {
        pc = new PlayerCharacter(50, 60, 50, 0, 2, 4, 50, "Hero");
        monster = new Monster(50, 60, 60, 5, 5, 7, 40, "Boblin", 2, "Goblin");
        encounter = new Combat(pc, monster);
        diceRoller = mock(DiceRoller.class);
        PlayerInput input = mock(PlayerInput.class);
        pc.setDiceRoller(diceRoller);
        monster.setDiceRoller(diceRoller);
        encounter.setDiceRoller(diceRoller);
        encounter.setPlayerInput(input);
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
    public void createInitiativeSameInitiativePcSpd() {
        when(diceRoller.roll1d100()).thenReturn(50, 60);
        Character[] expected = new Character[2];
        expected[0] = pc;
        expected[1] = monster;
        Character[] initiative = encounter.createInitiative();
        assertArrayEquals(expected, initiative);
        verify(diceRoller, times(2)).roll1d100();
    }

    @Test
    public void createInitiativeSameInitiativeMonsterSpd() {
        monster.modifyCurrentSpd(20);
        when(diceRoller.roll1d100()).thenReturn(50, 40);
        Character[] expected = new Character[2];
        expected[0] = monster;
        expected[1] = pc;
        Character[] initiative = encounter.createInitiative();
        assertArrayEquals(expected, initiative);
        verify(diceRoller, times(2)).roll1d100();
    }

    @Test
    public void createInitiativeSameInitiativeSameSpd() {
        monster.modifyCurrentSpd(10);
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
        assertEquals(50, pc.getCurrentHealth());
    }

    @Test
    public void monsterHealthZero(){
        monster.modifyCurrentHealth(-60);
        assertFalse(encounter.isAlive(monster));
    }

    @Test
    public void monsterHealthMax(){
        assertEquals(60, monster.getCurrentHealth());
    }

    @Test
    public void pcTakeDamage(){
        encounter.takeDamage(monster, pc, -5);
        assertEquals(45, pc.getCurrentHealth());
    }

    @Test
    public void pcTakePositiveDamage(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> encounter.takeDamage(monster, pc, 5));
        String expected = "Damage can't be positive";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void monsterTakeDamage(){
        encounter.takeDamage(pc, monster, -5);
        assertEquals(55, monster.getCurrentHealth());
    }

    @Test
    public void monsterTakePositiveDamage(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> encounter.takeDamage(pc, monster, 5));
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
        assertTrue(encounter.defeat);
        assertFalse(encounter.victory);
    }

    @Test
    public void pcAttackHit(){
        when(diceRoller.roll1d100()).thenReturn(60);
        when(diceRoller.rollWithinRange(2, 4)).thenReturn(4);
        encounter.turn(pc, monster, 1);
        assertEquals(56, monster.getCurrentHealth());
    }

    @Test
    public void pcAttackMiss(){
        when(diceRoller.roll1d100()).thenReturn(5);
        encounter.turn(pc, monster, 1);
        assertEquals(60, monster.getCurrentHealth());
    }

    @Test
    public void monsterAttackHit(){
        when(diceRoller.roll1d100()).thenReturn(50);
        when(diceRoller.rollWithinRange(5, 7)).thenReturn(5);
        encounter.turn(monster, pc, 1);
        assertEquals(45, pc.getCurrentHealth());
    }

    @Test
    public void monsterAttackMiss(){
        when(diceRoller.roll1d100()).thenReturn(5);
        encounter.turn(monster, pc, 1);
        assertEquals(50, pc.getCurrentHealth());
    }

    @Test
    public void pcDodge(){
        encounter.turn(pc, monster, 2);
        assertEquals(120, pc.getCurrentEvsMod());
    }

    @Test
    public void monsterDodge(){
        encounter.turn(monster, pc, 2);
        assertEquals(120, monster.getCurrentEvsMod());
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
        when(encounter.playerInput.getInput()).thenReturn(1);
        Exception exception = assertThrows(NoSuchElementException.class, () -> encounter.turn(pc, monster, 3));
        String expected = "You have no items.";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void pcUseWrongItem(){
        when(encounter.playerInput.getInput()).thenReturn(1);
        Weapon sword = new Weapon(10,5, 7, "Sword");
        pc.getInventory().getBackpack().add(sword);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> encounter.useItem(pc, 1));
        String expected = "This item cannot be used at this time.";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void pcChoseItem(){
        Consumable c = new Consumable("Healing potion", "Health", 10);
        pc.getInventory().getBackpack().add(c);
        encounter.takeDamage(monster, pc, -15);
        when(encounter.playerInput.getInput()).thenReturn(1);
        encounter.turn(pc, monster, 3);
        assertEquals(45, pc.getCurrentHealth());
    }

    @Test
    public void pcChoseNoItem(){
        when(encounter.playerInput.getInput()).thenReturn(2);
        Consumable c = new Consumable("Healing potion", "Health", 10);
        pc.getInventory().getBackpack().add(c);
        encounter.takeDamage(monster, pc, -15);
        Exception exception = assertThrows(NoSuchElementException.class, () -> encounter.turn(pc, monster, 3));
        String expected = "Please chose a valid item.";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void pcHasNoSpells(){
        Exception exception = assertThrows(NoSuchElementException.class, () -> encounter.turn(pc, monster, 4));
        String expected = "You don't know any spells.";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void pcHasSpells(){
        when(encounter.playerInput.getInput()).thenReturn(1);
        when(encounter.diceRoller.rollWithinRange(10, 20)).thenReturn(10);
        when(encounter.diceRoller.roll1d100()).thenReturn(50);
        DamageMagic dSpell = new DamageMagic(10, "Light", "Fireball", 10, 20);
        pc.spellBook.pickUpSpell(dSpell);
        pc.spellBook.equipDamageSpell(dSpell);
        encounter.turn(pc, monster, 4);
        assertEquals(50, monster.getCurrentHealth());
        assertEquals(90, pc.getMana());
    }

    @Test
    public void pcPicksWrongSpellslot(){
        when(encounter.playerInput.getInput()).thenReturn(4);
        DamageMagic dSpell = new DamageMagic(10, "Light", "Fireball", 10, 20);
        pc.spellBook.pickUpSpell(dSpell);
        pc.spellBook.equipDamageSpell(dSpell);
        Exception exception = assertThrows(NoSuchElementException.class, ()-> encounter.turn(pc, monster, 4));
        String expected = "Please chose a valid spellslot.";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void pcUseDamageMagic(){
        when(encounter.diceRoller.rollWithinRange(10, 20)).thenReturn(10);
        when(encounter.diceRoller.roll1d100()).thenReturn(50);
        DamageMagic dSpell = new DamageMagic(10, "Light", "Fireball", 10, 20);
        pc.spellBook.pickUpSpell(dSpell);
        pc.spellBook.equipDamageSpell(dSpell);
        encounter.useMagic(pc, monster, 1);
        assertEquals(50, monster.getCurrentHealth());
        assertEquals(90, pc.getMana());
    }

    @Test
    public void pcMissDamageMagic(){
        when(encounter.diceRoller.roll1d100()).thenReturn(1);
        DamageMagic dSpell = new DamageMagic(10, "Light", "Fireball", 10, 20);
        pc.spellBook.pickUpSpell(dSpell);
        pc.spellBook.equipDamageSpell(dSpell);
        encounter.useMagic(pc, monster, 1);
        assertEquals(60, monster.getCurrentHealth());
        assertEquals(90, pc.getMana());
    }

    @Test
    public void monsterUseDamageMagic(){
        when(encounter.diceRoller.rollWithinRange(20, 40)).thenReturn(20);
        when(encounter.diceRoller.roll1d100()).thenReturn(50);
        DamageMagic dSpell = new DamageMagic(10, "Light", "Fireball", 10, 20);
        monster.spellBook.pickUpSpell(dSpell);
        monster.spellBook.equipDamageSpell(dSpell);
        encounter.useMagic(monster, pc, 1);
        assertEquals(30, pc.getCurrentHealth());
        assertEquals(90, monster.getMana());
    }
    @Test
    public void monsterMissDamageMagic(){
        when(encounter.diceRoller.roll1d100()).thenReturn(1);
        DamageMagic dSpell = new DamageMagic(10, "Light", "Fireball", 10, 20);
        monster.spellBook.pickUpSpell(dSpell);
        monster.spellBook.equipDamageSpell(dSpell);
        encounter.useMagic(monster, pc, 1);
        assertEquals(50, pc.getCurrentHealth());
        assertEquals(90, monster.getMana());
    }

    @Test
    public void pcUseHealingMagic(){
        when(encounter.diceRoller.rollWithinRange(10, 20)).thenReturn(10);
        HealingMagic hSpell = new HealingMagic(10, "Healing word", 10, 20);
        pc.spellBook.pickUpSpell(hSpell);
        pc.spellBook.equipHealingSpell(hSpell);
        encounter.takeDamage(monster, pc, -20);
        encounter.useMagic(pc, monster, 2);
        assertEquals(40, pc.getCurrentHealth());
        assertEquals(90, pc.getMana());
    }

    @Test
    public void monsterUseHealingMagic(){
        when(encounter.diceRoller.rollWithinRange(10, 20)).thenReturn(10);
        HealingMagic hSpell = new HealingMagic(10, "Healing word", 5, 10);
        monster.spellBook.pickUpSpell(hSpell);
        monster.spellBook.equipHealingSpell(hSpell);
        encounter.takeDamage(pc, monster, -20);
        encounter.useMagic(monster, pc, 2);
        assertEquals(50, monster.getCurrentHealth());
        assertEquals(90, monster.getMana());
    }

    @Test
    public void pcUseUtilityMagic(){
        UtilityMagic uSpell = new UtilityMagic(10, "Speedy", 5, "Spd");
        pc.spellBook.pickUpSpell(uSpell);
        pc.spellBook.equipUtilitySpell(uSpell);
        encounter.useMagic(pc, monster, 3);
        assertEquals(55, pc.getCurrentSpd());
        assertEquals(90, pc.getMana());
        assertEquals(1, encounter.modChanges.size());
    }

    @Test
    public void monsterUseUtilityMagic(){
        UtilityMagic uSpell = new UtilityMagic(10, "Speedy", 5, "Spd");
        monster.spellBook.pickUpSpell(uSpell);
        monster.spellBook.equipUtilitySpell(uSpell);
        encounter.useMagic(monster, pc, 3);
        assertEquals(50, monster.getCurrentSpd());
        assertEquals(90, monster.getMana());
        assertEquals(1, encounter.modChanges.size());
    }

    @Test
    public void pcUseNegativeUtilityMagic(){
        UtilityMagic uSpell = new UtilityMagic(10, "No-Speedy", -5, "Spd");
        pc.spellBook.pickUpSpell(uSpell);
        pc.spellBook.equipUtilitySpell(uSpell);
        encounter.useMagic(pc, monster, 3);
        assertEquals(35, monster.getCurrentSpd());
        assertEquals(90, pc.getMana());
        assertEquals(1, encounter.modChanges.size());
    }

    @Test
    public void monsterUseNegativeUtilityMagic(){
        UtilityMagic uSpell = new UtilityMagic(10, "No-Speedy", -5, "Spd");
        monster.spellBook.pickUpSpell(uSpell);
        monster.spellBook.equipUtilitySpell(uSpell);
        encounter.useMagic(monster, pc, 3);
        assertEquals(40, pc.getCurrentSpd());
        assertEquals(90, monster.getMana());
        assertEquals(1, encounter.modChanges.size());
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


