import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ModChangeTest {

    private PlayerCharacter pc;
    private Monster monster;
    private ModChange mcap;
    private ModChange mcan;
    private ModChange mcep;
    private ModChange mcen;
    private ModChange mcsp;
    private ModChange mcsn;

    @Before
    public void setUp(){
        pc = new PlayerCharacter(50, 50, 50, 0, 2, 4, 50, "Hero");
        monster = new Monster(50, 60, 50, 0, 2, 4, 50, "Boblin", 1, "Goblin");
        mcap = new ModChange(1, 3,10, pc, "Atk");
        mcan = new ModChange(1, 3, -10, pc, "Atk");
        mcep = new ModChange(1, 3, 10, pc, "Evs");
        mcen = new ModChange(1, 3, -10, pc, "Evs");
        mcsp = new ModChange(1, 3, 10, pc, "Spd");
        mcsn = new ModChange(1, 3, -10, pc, "Spd");
    }

    @Test
    public void getRoundEndTest(){ assertEquals(4, mcap.getRoundEnd()); }

    @Test
    public void getCharacterTest(){ assertEquals(pc, mcap.getCharacter());}

    @Test
    public void getModTest(){ assertEquals("Atk", mcap.getMod());}

    @Test
    public void getChangeTest(){assertEquals(10, mcap.getChange());}

    @Test
    public void falseChangeMod(){
        ModChange bad = new ModChange(1, 3, 10, pc, "Fire");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bad.changeMod();
        });
        String expected = "That is not a real mod";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    public void changeAtkUp(){
        mcap.changeMod();
        assertEquals(60, pc.getCurrentAtkMod());
    }

    @Test
    public void changeAtkDown(){
        mcan.changeMod();
        assertEquals(40, pc.getCurrentAtkMod());
    }

    @Test
    public void changeEvsUp(){
        mcep.changeMod();
        assertEquals(60, pc.getCurrentEvsMod());
    }

    @Test
    public void changeEvsDown(){
        mcen.changeMod();
        assertEquals(40, pc.getCurrentEvsMod());
    }

    @Test
    public void changeSpdUp(){
        mcsp.changeMod();
        assertEquals(60, pc.getCurrentSpd());
    }

    @Test
    public void changeSpdDown(){
        mcsn.changeMod();
        assertEquals(40, pc.getCurrentSpd());
    }

    @Test
    public void changeModAltAtk(){
        mcan.changeMod("Atk", 10);
        assertEquals(60, pc.getCurrentAtkMod());
    }

    @Test
    public void changeModAltEvs(){
        mcen.changeMod("Evs", 10);
        assertEquals(60, pc.getCurrentEvsMod());
    }

    @Test
    public void changeModAltSpd(){
        mcsn.changeMod("Spd", 10);
        assertEquals(60, pc.getCurrentSpd());
    }

}