import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


class ModChangeTest {

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

}