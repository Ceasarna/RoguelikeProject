/*  Testar:
 * DamageMagic
 * HealingMagic
 * UtilityMagic */

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.*;

public class MagicSpellsTest {
    DamageMagic damageMagic;
    HealingMagic healingMagic;
    UtilityMagic utilityMagic;

    @BeforeEach
    void setUp(){
        damageMagic = new DamageMagic(10, "Dark", "Spelly", 10, 25);
        healingMagic = new HealingMagic(10, "name", 10, 20);
        utilityMagic = new UtilityMagic(15, "Meh", 35);
    }

    @Test
    public void testTypeOfDamageException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new DamageMagic(10, "NotCorrect", "Spelly", 5, 10));

        assertThat(thrown.getMessage(), is("SpellName must either be Light or Dark"));
    }

    @Test
    public void testMinDamageHigherThanMaxDamageException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new DamageMagic(10, "Light", "Spelly", 15, 10));

        assertThat(thrown.getMessage(), is("MinDmg cannot be higher than MaxDmg"));
    }

    @Test
    public void testSetNegativeMinDamageException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> damageMagic.setMinDmg(-5));

        assertThat(thrown.getMessage(), is("Value cannot be lower than zero"));
    }

    @Test
    public void testSetNegativeMaxDamageException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> damageMagic.setMaxDmg(-5));

        assertThat(thrown.getMessage(), is("Value cannot be lower than zero"));
    }

    @Test
    public void testSetNegativeMinHealingException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new HealingMagic(10, "name", -5, 25));

        assertThat(thrown.getMessage(), is("MinHeal cannot be below Zero"));
    }

    @Test
    public void testSetMinHealAboveMaxHeal(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> new HealingMagic(10, "name", 10, 5));

        assertThat(thrown.getMessage(), is("MinHeal cannot be higher than MaxHeal"));
    }

    @Test
    public void testRestoreHealth(){
        assertThat(healingMagic.restoreHealth(), is(15));
    }

    @Test
    public void testGetMinHeal(){
        assertThat(healingMagic.getMinHeal(), is(10.0));
    }

    @Test
    public void testGetMaxHeal() {
        assertThat(healingMagic.getMaxHeal(), is(20.0));
    }

    @Test
    public void testSetMinValueBelowZeroInSetterException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> healingMagic.setMinHeal(-5));
        assertThat(thrown.getMessage(), is("Value cannot be lower than zero"));
    }

    @Test
    public void testSetMaxValueBelowZeroInSetterException(){
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> healingMagic.setMaxHeal(-5));
        assertThat(thrown.getMessage(), is("Value cannot be lower than zero"));
    }

    @Test
    public void testSetUtilityValueBelowZeroException(){
        String errorMessage = "Value cannot be lower than zero";
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> utilityMagic.setUtilityValue(-5));
        assertThat(thrown.getMessage(), is(errorMessage));
    }

    @Test
    public void testGetUtilityValue(){
        assertThat(utilityMagic.getUtilityValue(), is(35));
    }

    @Test
    public void testDealDamage(){
        assertThat(damageMagic.dealDamage(), is(equalTo(17)));
    }

    @Test
    public void testGetMinDamage(){
        assertThat(damageMagic.getMinDmg(), is(10.0));
    }

    @Test
    public void testGetMaxDamage(){
        assertThat(damageMagic.getMaxDmg(), is(25.0));
    }

    @Test
    public void testGetType(){
        assertThat(damageMagic.getDamageType(), is("Dark"));
    }
}
